package novel.crawler.spider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.internal.Throwables;

import novel.crawler.entity.Book;
import novel.crawler.entity.Content;
import novel.crawler.enums.Type;
import novel.crawler.interfaces.INovelSpider;
import novel.crawler.util.Request;
import novel.crawler.util.Tool;

/**
 * @author Administrator 抽象类
 */
public abstract class AbstractSpider implements INovelSpider {

	protected Map<String, Map<String, org.dom4j.Element>> map = new Tool().ruleMap;
	protected Map<String, org.dom4j.Element> webRule;
	protected String web;
	protected static final String CHAPTER_MATCH_RULE = ".*/*\\d+\\.[html]{3,4}";
	protected Element nextElement;
	//// 下一页 书列表
	protected String nextUrl;
	/**
	 * jsoup文本处理
	 */
	protected Document parseDoc;

	public Document getParseDoc() {
		return parseDoc;
	}

	/**
	 * 基础域名
	 */
	protected String baseUrl;

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	public void setParseDoc(Document parseDoc) {
		this.parseDoc = parseDoc;
	}

	/**
	 * book的url 后面替换章节url用
	 */
	protected String bookUrl;

	public String getBookUrl() {
		return bookUrl;
	}

	public void setBookUrl(String bookUrl) {
		this.bookUrl = bookUrl;
	}

	/**
	 * 解析html
	 * 
	 * @param html
	 * @param url
	 * @return 数据对象
	 * @throws java.text.ParseException
	 */
	public abstract Object analyzeHTMLByString(Type type, String url);

	public AbstractSpider(String web) {
		webRule = map.get(web);
		this.web = web;
	}

	/**
	 * 获取内容对象
	 * 
	 * @param crawlString
	 * @param web
	 * @return 内容对象
	 */
	@Override
	public Content getContent(String crawlString, String bookUrl) {
		Content content = new Content();
		content.setContent(getChapterContent(crawlString));
		content.setTitle(getChapterName());
		content.setNext(getNextUrl().isEmpty() ? getNextUrl() : bookUrl.concat(getNextUrl()));
		content.setPre(getPreUrl().isEmpty() ? getPreUrl() : bookUrl.concat(getPreUrl()));
		return content;
	}

	/*
	 * 爬取网页信息
	 */

	@Override
	public String pickData(String url, String charset) {
		System.out.println(url);
		HttpGet httpget = new HttpGet(url);
		httpget.setConfig(RequestConfig.custom().setConnectionRequestTimeout(2_000).setConnectTimeout(10_000)
				.setSocketTimeout(10_000).build());
		Request.setDefaultNovelSpiderHeader(httpget);
		CloseableHttpResponse response = null;
		try {
			CloseableHttpClient httpClient = HttpClientBuilder.create().build();
			response = httpClient.execute(httpget);
			StatusLine statusLine = response.getStatusLine();
			if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
				return EntityUtils.toString(response.getEntity(), charset);
			} else {
				throw new Exception("抓取失败，HTTP状态码：" + statusLine.getStatusCode());
			}
		} catch (Exception e) {
			// FIXME: handle exception
			throw new RuntimeException(e.toString());
//			System.out.println(e.toString()); 
		}
	}

	/**
	 * 获取章节名
	 * 
	 * @param selector
	 * @return 章节名
	 */
	@Override
	public String getChapterName() {
		org.dom4j.Element titleElement = webRule.get("content-this-title-element");
		String selector = titleElement.attributeValue("selector");
		int index = titleElement.attributeValue("index") == null ? 0
				: Integer.parseInt(titleElement.attributeValue("index"));
		Element title = parseDoc.select(selector).get(index);
		if (title == null) {
			return "";
		} else {
			return title.text();
		}

	}

	/**
	 * 获取章节内容
	 * 
	 * @param selector
	 * @return 章节内容
	 * @throws ParseException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getChapterContent(String crawlString) {
		org.dom4j.Element contentElement = webRule.get("content-this-element");
		List<org.dom4j.Element> parseElements = contentElement.elements("parse");
		if (parseElements != null && !parseElements.isEmpty()) {
			for (org.dom4j.Element parseElement : parseElements) {
				crawlString = crawlString.replaceAll(parseElement.getText(),
						parseElement.attributeValue("to") == null ? "" : parseElement.attributeValue("to"));
			}
		}
		parseDoc = Jsoup.parse(crawlString);
		Elements elements = parseDoc.select(contentElement.attributeValue("selector"));
		if (elements == null || elements.isEmpty())
			try {
				throw new Exception("抓取规则不正确！");
			} catch (Exception e) {
				// FIXME 自动生成的 catch 块
				e.printStackTrace();
			}
		int index = contentElement.attributeValue("index") == null ? 0
				: Integer.parseInt(contentElement.attributeValue("index"));
		crawlString = elements.get(index).text();
		if (parseElements != null && !parseElements.isEmpty()) {
			for (org.dom4j.Element parseElement : parseElements) {
				try {
					crawlString = Tool.replaceSpecifyString(crawlString, parseElement.attributeValue("to"));
				} catch (java.text.ParseException e) {
					// FIXME 自动生成的 catch 块
					e.printStackTrace();
				}
			}
		}
		return crawlString;
	}

	/**
	 * 获取下一章Url
	 * 
	 * @param selector
	 * @return 下一章Url
	 */
	@Override
	public String getNextUrl() {
		org.dom4j.Element element = webRule.get("content-this-next-element");
		String selector = element.attributeValue("selector");
		int index = element.attributeValue("index") == null ? 0 : Integer.parseInt(element.attributeValue("index"));
		Element nextElement = parseDoc.select(selector).get(index);
		if (nextElement == null || !nextElement.attr("href").matches(CHAPTER_MATCH_RULE)) {
			return "";
		} else {
			return nextElement.attr("href");
		}
	}

	/**
	 * 获取上一章Url
	 * 
	 * @param selector
	 * @return 上一章Url
	 */
	@Override
	public String getPreUrl() {
		org.dom4j.Element element = webRule.get("content-this-prev-element");
		String selector = element.attributeValue("selector");
		int index = element.attributeValue("index") == null ? 0 : Integer.parseInt(element.attributeValue("index"));
		Element prevElement = parseDoc.select(selector).get(index);
		if (prevElement == null || !prevElement.attr("href").matches(CHAPTER_MATCH_RULE)) {
			return "";
		} else {
			String href = prevElement.attr("href");
			return href;
		}
	}

	/**
	 * (BXWX、KSZ用) 根据url获取小说实体列表
	 * 
	 * @param url
	 * @return 返回小说实体列表
	 */
	public List<Book> getAllBooks(String url, Integer maxTryTime) {
		return new ArrayList<Book>();
	};

	/**
	 * (BXWX、KSZ用) 根据url获取小说实体列表
	 * 
	 * @param url
	 * @param charset
	 * @return 返回小说实体列表 "http://www.kanshuzhong.com/map/A/1/"
	 */
	public Elements getTrs(String url, String charset) {
		return getTrs(url, charset, INovelSpider.MAX_TRY_TIME);
	};

	/**
	 * (BXWX、KSZ用) 根据url获取小说实体列表
	 * 
	 * @param url
	 * @return 返回小说实体列表 "http://www.kanshuzhong.com/map/A/1/"
	 */
	public Elements getTrs(String url, String charset, Integer maxTryTime) {
		maxTryTime = maxTryTime == null ? INovelSpider.MAX_TRY_TIME : maxTryTime;
		Elements trs = null;
		for (int i = 0; i < maxTryTime; i++) {
			try {
				String html = pickData(url, charset);
				String novelSelector = webRule.get("novel-selector").getTextTrim();
				if (novelSelector == null) {
					throw new RuntimeException(url + "获取tr标签出错");
				}
				Document document = Jsoup.parse(html, baseUrl);
				trs = document.select(novelSelector);

				String nextSelector = webRule.get("novel-nextpage-selector").getTextTrim();
				if (nextSelector != null) {
					Elements elements = document.select(nextSelector);
					nextElement = elements == null ? null : elements.first();

					if (nextElement != null) {
						nextUrl = nextElement.absUrl("href");
					} else {
						nextUrl = "";
					}
				}

				return trs;
			} catch (Exception e) {
				// FIXME: handle exception
			}

		}
		throw new RuntimeException(url + "尝试了" + maxTryTime + "次还是失败了..");
	}

	@Override
	public Boolean hasNext() {
		// FIXME Auto-generated method stub
		return !nextUrl.isEmpty();
	}

	@Override
	public String next() {
		// FIXME Auto-generated method stub
		return nextUrl;
	}

	@Override
	public Iterator<List<Book>> iterator(String presentUrl, Integer maxTryTime) {
		// FIXME Auto-generated method stub
		nextUrl = presentUrl;
		return new NovelIterator();
	}

	/**
	 * @author mcshr 迭代器 抓取小说列表
	 */
	private class NovelIterator implements Iterator<List<Book>> {

		@Override
		public boolean hasNext() {
			return AbstractSpider.this.hasNext();
		}

		@Override
		public List<Book> next() {
			List<Book> books = getAllBooks(nextUrl, 10);
			return books;
		}

	}
}

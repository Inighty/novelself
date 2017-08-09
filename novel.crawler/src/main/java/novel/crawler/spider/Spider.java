package novel.crawler.spider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;
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

import novel.crawler.entity.Book;
import novel.crawler.entity.Chapter;
import novel.crawler.entity.Content;
import novel.crawler.enums.Type;
import novel.crawler.interfaces.INovelSpider;
import novel.crawler.util.Request;
import novel.crawler.util.Tool;

/**
 * @author Administrator 抽象类
 */
public class Spider implements INovelSpider {

	private Tool tool;
	Map<String, org.dom4j.Element> webRule;
	protected String web;
	private String charset;
	protected Type type;
	private static final String CHAPTER_MATCH_RULE = ".*/*\\d+\\.[html]{3,4}";
	//// 当前列表key （如首字母A B C..即Task的key）
	private String presentKey;
	//// 下一页 书列表
	private String nextUrl;
	//// 记录对应字母的前一个月份<字母，<年,月份>> 为了知道有没有跨年
	private Map<String, int[]> lastMonth = new HashMap<>();

	private Calendar cal = Calendar.getInstance();
	// 当前年
	private int year = cal.get(Calendar.YEAR);
	/**
	 * jsoup文本处理
	 */
	Document parseDoc;

	public Document getParseDoc() {
		return parseDoc;
	}

	/**
	 * 基础域名
	 */
	protected String baseUrl;
//
//	public String getBaseUrl() {
//		return baseUrl;
//	}
//
//	public void setBaseUrl(String baseUrl) {
//		this.baseUrl = baseUrl;
//	}
//
//	public void setParseDoc(Document parseDoc) {
//		this.parseDoc = parseDoc;
//	}

	/**
	 * book的url 后面替换章节url用
	 */
	private String bookUrl;

	public String getBookUrl() {
		return bookUrl;
	}

	public void setBookUrl(String bookUrl) {
		this.bookUrl = bookUrl;
	}

	public Spider(String web, Tool tool, Type type) {
		this.tool = tool;
		Init(web, type);
	}

	public Spider(String web, Type type) {
		tool = new Tool();
		Init(web, type);
	}

	private void Init(String web, Type type) {
		for (Map.Entry key : tool.ruleMap.entrySet()) {
			String keyStr = key.getKey().toString();
			if (web.contains(keyStr)) {
				this.baseUrl = keyStr;
				break;
			}
		}
		this.webRule = tool.ruleMap.get(baseUrl);
		this.charset = webRule.get("charset").getTextTrim();
		this.web = web;
		this.type = type;
	}

	/**
	 * @return 对象
	 */
	@Override
	public Object analyzeHTMLByString() {
		String html = "";
		for (int i = 0; i < tool.MaxTryTime; i++) {
			try {
				html = pickData(web, charset);
				if (!html.isEmpty()) {
					break;
				}
			} catch (Exception ex) {
				throw new RuntimeException(web + "尝试了" + tool.MaxTryTime + "次还是失败了..");
			}
		}
		//// 空直接返回
		if (html.isEmpty()) {
			return null;
		}
		parseDoc = Jsoup.parse(html, baseUrl);
		try {
			switch (type) {
				case booklist:
					return getAllBooks(html);
				case chapterlist:
					//// 这里可以把书前面没有的属性获取到并赋值 类型 更新时间

					//// 这里将书的地址赋给下次需要处理的url中
					bookUrl = web;
					return getChapters();
				case content:
					return getContent(html, web);
				case downloadUrl:
				default:
					return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public List<Chapter> getChapters() {
		org.dom4j.Element element = webRule.get("chapter-list-element");
		Elements aTags = parseDoc.select(element.attributeValue("selector"));
		// Elements chapters =
		// parseDoc.getElementById("list").getElementsByTag("dd");
		List<Chapter> chapterList = new ArrayList<>();
		if (aTags == null || aTags.isEmpty()) {
			return new ArrayList<>();
		}
		aTags.forEach(o -> {
			Chapter chapter = new Chapter();
			String chapterUrl = o.select("a").attr("href").trim();
			if (!chapterUrl.startsWith("/") && !chapterUrl.startsWith("http://")) {
				chapterUrl = "/" + chapterUrl;
			}
			if (bookUrl.endsWith("/")) {
				bookUrl = bookUrl.substring(0, bookUrl.length() - 1);
			} else if (bookUrl.endsWith("/index.html")) {
				bookUrl = bookUrl.replace("/index.html", "");
			}
			if (!chapterUrl.contains("http://")) {
				chapterUrl = bookUrl.concat(chapterUrl);
			}
			try {
				chapter.setUrl(Request.encryptBASE64(chapterUrl));
			} catch (Exception e) {
				// FIXME Auto-generated catch block
				e.printStackTrace();
			}
			chapter.setTitle(o.getElementsByTag("a").text());
			// System.out.println(chapter.toString());

			chapterList.add(chapter);
		});
//		if (web.equals("BXWX")) {
//			Collections.sort(chapterList, new Comparator<Chapter>() { // 笔下文学爬取的章节需要重新排序
//				@Override
//				public int compare(Chapter o1, Chapter o2) {
//					try {
//						return Request.decryptBASE64(o1.getUrl()).compareTo(Request.decryptBASE64(o2.getUrl()));
//					} catch (IOException e) {
//						// FIXME Auto-generated catch block
//						e.printStackTrace();
//					}
//					return 0;
//				}
//			});
//		}
		return chapterList;
	}

	/**
	 * 获取内容对象
	 *
	 * @param crawlString 获取字符串
	 * @param url         链接
	 * @return 内容对象
	 */
	@Override
	public Content getContent(String crawlString, String url) {
		Content content = new Content();
		content.setContent(getChapterContent(crawlString));
		content.setTitle(getChapterName());
		content.setNext(Tool.relativeUrl2FullUrl(url, getNextUrl()));
		content.setPre(Tool.relativeUrl2FullUrl(url, getPreUrl()));
		return content;
	}

	/*
	 * 爬取网页信息
	 */

	@Override
	public String pickData(String url, String charset) {
		HttpGet httpget = new HttpGet(url);
		httpget.setConfig(RequestConfig.custom().setConnectionRequestTimeout(10_000).setConnectTimeout(10_000)
				.setSocketTimeout(10_000).build());
		Request.setDefaultNovelSpiderHeader(httpget);
		CloseableHttpResponse response;
		CloseableHttpClient httpClient;
		try {
			httpClient = HttpClientBuilder.create().build();
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
			// System.out.println(e.toString());
		}
	}

	/**
	 * 获取章节名
	 *
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
	 * @return 章节内容
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String getChapterContent(String crawlString) {
		StringBuilder result = new StringBuilder();
		String temp = "";
		String nextNode = "";
		org.dom4j.Element contentElement = webRule.get("content-this-element");
		List<org.dom4j.Element> parseElements = contentElement.elements("parse");
		do {
			temp = crawlString;
			if (parseElements != null && !parseElements.isEmpty()) {
				for (org.dom4j.Element parseElement : parseElements) {
					crawlString = crawlString.replaceAll(parseElement.getText(),
							parseElement.attributeValue("to") == null ? "" : parseElement.attributeValue("to"));
				}
			}
			parseDoc = Jsoup.parse(crawlString);
			Elements elements = parseDoc.select(contentElement.attributeValue("selector"));
			if (crawlString.contains("下一节</a>")) {
				String splitStr = webRule.get("content-split-element").getTextTrim();
				Elements nextElements = parseDoc.select(splitStr);
				nextNode = nextElements.get(2).getElementsByTag("a").first().absUrl("href");
			}


			int index = contentElement.attributeValue("index") == null ? 0
					: Integer.parseInt(contentElement.attributeValue("index"));
			String content = elements.get(index).text();
			org.dom4j.Element needRemove = webRule.get("content-need-remove");
			if (needRemove != null) {
				for (String string : needRemove.getTextTrim().split(" ")) {
					elements.select(string).remove();
				}
				crawlString = content.substring(0, content.lastIndexOf("#{line-break}") - 1);
			} else {
				crawlString = content;
			}


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
			result.append(crawlString);

			if (!nextNode.isEmpty()) {
				crawlString = pickData(nextNode, charset);
			}
		}
		while (temp.contains("下一节</a>"));
		return result.toString();
	}

	/**
	 * 获取下一章Url
	 *
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
			if (baseUrl.contains("23wx") || baseUrl.contains("51xsw")) {
				return baseUrl + nextElement.attr("href");
			} else {
				return nextElement.attr("href");
			}
		}
	}

	/**
	 * 获取上一章Url
	 *
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
			if (baseUrl.contains("23wx")) {
				return baseUrl + prevElement.attr("href");
			} else {
				return prevElement.attr("href");
			}
		}
	}

	/**
	 * (BXWX、KSZ用) 根据url获取小说实体列表
	 *
	 * @param html
	 * @return 返回小说实体列表
	 */
	public List<Book> getAllBooks(String html) {
		List<Book> books = new ArrayList<>();
		// String msg = "";
		try {
			Elements trs = getTrs(html);
			for (int i = 1, size = trs.size() - 1; i < size; i++) {
				Element tr = trs.get(i);
				Elements tds = tr.children();
				Book book = new Book();
				// msg = tds.toString();
				//去除非中文
				book.setType(tds.first().text().replaceAll("[^\u4e00-\u9fa5]", ""));
				book.setName(tds.get(1).text());
				book.setUrl(tds.get(1).getElementsByTag("a").first().absUrl("href"));
				book.setNewChapter(tds.get(2).text());
				book.setNewChapterUrl(tds.get(2).getElementsByTag("a").first().absUrl("href"));
				book.setAuthor(tds.get(3).text());
				// book.setAuthorUrl(tds.get(2).getElementsByTag("a").first().absUrl("href"));

				book.setLastUpdateTime(ConvertDate(tds.get(4).text(), "MM-dd"));
				// book.setStatus(Tool.FormatStatus(tds.get(5).text()));
				book.setSource(webRule.get("source").getTextTrim());
				books.add(book);
				// Thread.sleep(1_000);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
			// FIXME: handle exception
		}
		return books;
	}

	;

	/**
	 * 获取书列表tr
	 *
	 * @param html
	 * @return
	 */
	public Elements getTrs(String html) {
//		maxTryTime = maxTryTime == null ? INovelSpider.MAX_TRY_TIME : maxTryTime;
		Elements trs;
//		for (int i = 0; i < maxTryTime; i++) {
//			try {
//				String html = pickData(url, charset);
		String novelSelector = webRule.get("novel-selector").getTextTrim();
		if (novelSelector == null) {
			throw new RuntimeException(html + "获取tr标签出错");
		}
		Document document = Jsoup.parse(html, baseUrl);
		trs = document.select(novelSelector);
		String nextSelector = webRule.get("novel-nextpage-selector").getTextTrim();
		Elements elements = document.select(nextSelector);
		Element nextElement = elements == null ? null : elements.first();
		//// 混混小说网没有下一页
		if (baseUrl.contains("hunhun")) {
			String[] page = nextElement.text().replaceAll("页", "").split("/");
			if (!page[0].equals(page[1])) {
				nextUrl = web.replaceAll("\\d+.html", (Integer.parseInt(page[0]) + 1) + ".html");
			} else {
				nextUrl = "";
			}
		} else {

			if (nextElement != null) {
				nextUrl = nextElement.absUrl("href");
			} else {
				nextUrl = "";
			}
		}

		return trs;
//			} catch (Exception e) {
//				// FIXME: handle exception
//			}
//		}
//		throw new RuntimeException(url + "尝试了" + maxTryTime + "次还是失败了..");
	}

	public Boolean hasNext() {
		// FIXME Auto-generated method stub
		return !nextUrl.isEmpty();
	}

	public String next() {
		// FIXME Auto-generated method stub
		return nextUrl;
	}

	public Iterator<List<Book>> iterator(String key, String presentUrl) {
		// FIXME Auto-generated method stub
		presentKey = key;
		nextUrl = presentUrl;
		return new NovelIterator();
	}

	/**
	 * @author mcshr 迭代器 抓取小说列表
	 */
	private class NovelIterator implements Iterator<List<Book>> {

		public boolean hasNext() {
			return Spider.this.hasNext();
		}

		public List<Book> next() {
			web = nextUrl;
			return (List<Book>) analyzeHTMLByString();
		}
	}

	public String getLastUpdateTime(String bookUrl) {
		String result = "";
		String html = pickData(bookUrl, charset);
		String regex = "[0-9]{4}[\\-|/][0-9]{1,2}[\\-|/][0-9]{1,2}\\s+[0-9]{1,2}:[0-9]{1,2}:[0-9]{1,2}";
		final Pattern pattern = Pattern.compile(regex);
		final Matcher matcher = pattern.matcher(html);

		if (matcher.find()) {
			result = matcher.group(0);
		}
		return result;
	}

	;

	/**
	 * 根据bookurl获取下载链接
	 *
	 * @param bookurl
	 * @return 返回txt下载链接
	 */
	public String getDownloadTxtUrl(String bookurl) {
		return null;
	}

	/**
	 * 字符串转Date
	 *
	 * @param time
	 * @param format
	 * @return Date
	 * @throws java.text.ParseException
	 * @throws ParseException
	 */
	public Date ConvertDate(String time, String format) throws java.text.ParseException {
		if (format.equals("MM-dd")) {
			format = "yyyy-MM-dd";
			int newMonth = Integer.parseInt(time.split("-")[0]);
			//// 存在上一个月数据
			if (lastMonth.containsKey(presentKey)) {
				int oldYear = lastMonth.get(presentKey)[0];
				int oldMonth = lastMonth.get(presentKey)[1];
				int[] newInt = {oldYear, newMonth};
				//// 如果新的月份比上一个大 那么就减1年
				if (newMonth > lastMonth.get(presentKey)[1]) {
					int newYear = oldYear - 1;
					newInt[0] = newYear;
					lastMonth.put(presentKey, newInt);
					time = newYear + "-" + time;
				} else {
					if (newMonth < oldMonth) {
						//// 更新月
						lastMonth.put(presentKey, newInt);
					}
					time = oldYear + "-" + time;
				}
			}
			//// 不存在上一个月数据
			else {
				int[] newInt = {year, newMonth};
				lastMonth.put(presentKey, newInt);
				time = year + "-" + time;
			}
		}

		if (format.equals("yy-MM-dd")) {
			format = "yyyy-MM-dd";
			time = "20" + time;
		}

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = sdf.parse(time);
		return date;
	}
}

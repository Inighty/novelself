package com.novelspider.spider;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.novelspider.entity.Content;
import com.novelspider.enums.Type;
import com.novelspider.interfaces.INovelSpider;
import com.novelspider.util.Rule;

/**
 * @author Administrator 抽象类
 */
public abstract class AbstractSpider implements INovelSpider {

	private Map<String, Map<String, org.dom4j.Element>> map = new Rule().ruleMap;
	private Map<String, org.dom4j.Element> webRule;
	private String web;
	private static final String CHAPTER_MATCH_RULE = ".*/*\\d+\\.[html]{3,4}";

	/**
	 * jsoup文本处理
	 */
	protected Document parseDoc;

	public Document getParseDoc() {
		return parseDoc;
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
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet(url);
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				// 打印响应状态
				if (entity != null) {
					return EntityUtils.toString(entity, charset);
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
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
					crawlString = Rule.replaceSpecifyString(crawlString, parseElement.attributeValue("to"));
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
}

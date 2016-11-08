package novel.crawler.spider;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import novel.crawler.entity.Book;
import novel.crawler.interfaces.INovelSpider;
import novel.crawler.util.Tool;

/**
 * 混混小说网
 * 
 * @author Administrator
 *
 */
public class HHSpider extends AbstractSpider implements INovelSpider {

	public String baseUrl = "http://www.hunhun520.com";
	public String charset = "gbk";
	// "http://zhannei.baidu.com/cse/search?s=16829369641378287696&q=";

	public HHSpider() {
		super("HH");
		super.baseUrl = baseUrl;
		super.charset = charset;
		// FIXME Auto-generated constructor stub
	}

	@Override
	public String getSearchBookUrl() {
		// FIXME Auto-generated method stub
		return null;
	}

	/**
	 * (BXWX、KSZ用) 根据url获取小说实体列表
	 * 
	 * @param url
	 * @return 返回小说实体列表 "http://www.kanshuzhong.com/map/A/1/"
	 * @throws ParseException
	 */
	@Override
	public List<Book> getAllBooks(String url, Integer maxTryTime) {
		List<Book> books = new ArrayList<Book>();
		// String msg = "";
		try {
			Elements trs = super.getTrs(url, charset, maxTryTime);
			for (int i = 1, size = trs.size() - 1; i < size; i++) {
				Element tr = trs.get(i);
				Elements tds = tr.getElementsByTag("span");
				Book book = new Book();
				// msg = tds.toString();
				book.setType(tds.first().text().replace("]", "").replace("[", ""));
				book.setName(tds.get(1).text());
				book.setUrl(tds.get(1).getElementsByTag("a").first().absUrl("href"));
				book.setNewChapter(tds.get(2).text());
				book.setNewChapterUrl(tds.get(2).getElementsByTag("a").first().absUrl("href"));
				book.setAuthor(tds.get(3).text());
				// book.setAuthorUrl(tds.get(2).getElementsByTag("a").first().absUrl("href"));

				book.setLastUpdateTime(ConvertDate(tds.get(4).text(), "MM-dd"));
				// book.setStatus(Tool.FormatStatus(tds.get(5).text()));
				book.setSource("混混小说");
				books.add(book);
				// Thread.sleep(1_000);
			}
		} catch (Exception e) {
			// FIXME: handle exception
		}
		return books;
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
		String result = "";
		//// 临时变量
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
				Elements nextElements = parseDoc.select("div.text a");
				nextNode = nextElements.get(2).getElementsByTag("a").first().absUrl("href");
			}
			elements.select("div.mb_29b").remove();
			elements.select("div.text").remove();
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

			result += crawlString;

			if (!nextNode.isEmpty()) {
				crawlString = pickData(nextNode, charset);
			}
		} while (temp.contains("下一节</a>"));
		return result;
	}

}

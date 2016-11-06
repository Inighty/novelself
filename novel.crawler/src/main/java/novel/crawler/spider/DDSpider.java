package novel.crawler.spider;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import novel.crawler.entity.Book;
import novel.crawler.interfaces.INovelSpider;
import novel.crawler.util.Tool;

public class DDSpider extends AbstractSpider implements INovelSpider {

	public String baseUrl = "http://www.23wx.com";
	public String charset = "gbk";
	// "http://zhannei.baidu.com/cse/search?s=16829369641378287696&q=";

	public DDSpider() {
		super("DD");
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
				Elements tds = tr.getElementsByTag("td");
				Book book = new Book();
				// msg = tds.toString();
//				book.setType(tds.first().text());
				book.setName(tds.first().text());
				book.setUrl(tds.get(1).getElementsByTag("a").first().absUrl("href"));
				book.setNewChapter(tds.get(1).text());
				book.setNewChapterUrl(tds.get(1).getElementsByTag("a").first().absUrl("href"));
				book.setAuthor(tds.get(2).text());
//				book.setAuthorUrl(tds.get(2).getElementsByTag("a").first().absUrl("href"));
				book.setLastUpdateTime(ConvertDate(tds.get(4).text(), "yy-MM-dd"));
				book.setStatus(Tool.FormatStatus(tds.get(5).text()));
				book.setSource("顶点小说");
				books.add(book);
//				Thread.sleep(1_000);
			}
		} catch (Exception e) {
			// FIXME Auto-generated catch block
			// System.out.println(msg);
//			e.printStackTrace();
		}
		return books;
	}

	
	
}

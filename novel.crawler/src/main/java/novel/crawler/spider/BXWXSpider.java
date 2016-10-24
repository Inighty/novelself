package novel.crawler.spider;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import novel.crawler.entity.Book;
import novel.crawler.enums.Type;
import novel.crawler.interfaces.INovelSpider;
import novel.crawler.util.Tool;

public class BXWXSpider extends AbstractSpider implements INovelSpider {

	public String baseUrl = "http://www.bxwx8.org";
	public String charset = "gbk";
	
	public BXWXSpider() {
		super("BXWX");
		super.baseUrl = baseUrl;
		// FIXME Auto-generated constructor stub
	}

	@Override
	public String getSearchBookUrl() {
		// FIXME Auto-generated method stub
		return null;
	}

	@Override
	public String getCharset() {
		// FIXME Auto-generated method stub
		return null;
	}

	@Override
	public Object analyzeHTMLByString(Type type, String url) {
		// FIXME Auto-generated method stub
		return null;
	}

	/**
	 * (BXWX、KSZ用) 根据url获取小说实体列表
	 * 
	 * @param url
	 * @return 返回小说实体列表
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
				book.setName(tds.get(0).text());
				book.setUrl(tds.get(0).getElementsByTag("a").first().absUrl("href"));
				book.setNewChapter(tds.get(1).text());
				book.setNewChapterUrl(tds.get(1).getElementsByTag("a").first().absUrl("href"));
				book.setAuthor(tds.get(2).text());
//				book.setAuthorUrl(tds.get(3).getElementsByTag("a").first().absUrl("href"));
				book.setLastUpdateTime(Tool.ConvertDate(tds.get(4).text(), "yy-MM-dd"));
				book.setStatus(Tool.FormatStatus(tds.get(5).text()));
				book.setSource("笔下文学");
				books.add(book);
			}
		} catch (Exception e) {
			// FIXME Auto-generated catch block
			// System.out.println(msg);
			e.printStackTrace();
		}
		return books;
	}


}

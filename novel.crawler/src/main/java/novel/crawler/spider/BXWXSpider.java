package novel.crawler.spider;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import novel.crawler.entity.Book;
import novel.crawler.interfaces.INovelSpider;
import novel.crawler.util.Tool;

public class BXWXSpider extends AbstractSpider implements INovelSpider {

	public String baseUrl = "http://www.bxwx8.org";
	public String charset = "gbk";

	public BXWXSpider() {
		super("BXWX");
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
				book.setName(tds.get(0).text());
				book.setUrl(tds.get(0).getElementsByTag("a").first().absUrl("href").replace("binfo", "b")
						.replace(".htm", "/index.html"));
				book.setNewChapter(tds.get(1).text());
				book.setNewChapterUrl(tds.get(1).getElementsByTag("a").first().absUrl("href"));
				String author = tds.get(2).text();
				//// 黑岩害死人 去除垃圾数据
				book.setAuthor(author.startsWith("黑岩") && !author.equals("黑岩") ? author.replace("黑岩", "") : author);
				// book.setAuthorUrl(tds.get(3).getElementsByTag("a").first().absUrl("href"));
				book.setLastUpdateTime(ConvertDate(tds.get(4).text(), "yy-MM-dd"));
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

	@Override
	public String getLastUpdateTime(String html) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 根据bookurl获取下载链接
	 * 
	 * @param bookurl
	 * @return 返回txt下载链接
	 */
	public String getDownloadTxtUrl(String bookUrl) {
		/// http://txt.bxwxtxt.com/packdown/fulltxt/24/24675.txt?44
		// http://www.bxwx8.org/b/155/155702/index.html bookurl
		String txtUrl = "http://txt.bxwxtxt.com/packdown/fulltxt"
				.concat(bookUrl.replace("http://www.bxwx8.org/b", "").replace("/index.html", ".txt"));

		return txtUrl;
	}

}

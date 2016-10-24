package novel.crawler.spider;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import novel.crawler.entity.Book;
import novel.crawler.entity.Chapter;
import novel.crawler.entity.Content;
import novel.crawler.enums.Type;
import novel.crawler.interfaces.INovelSpider;
import novel.crawler.util.Tool;

/**
 * @author Administrator 笔趣阁
 */
public class BQGSpider extends AbstractSpider implements INovelSpider {

	public BQGSpider() {
		super("BQG");
		super.baseUrl = baseUrl;
		// FIXME 自动生成的构造函数存根
	}

	// public String web = "BQG";
	public String baseUrl = "http://www.biquge.tw";
	public String charset = "utf-8";
	public String url = "http://zhannei.baidu.com/cse/search?s=16829369641378287696&q=";

	@Override
	public Object analyzeHTMLByString(Type type, String url) {

		try {
			String html = pickData(url, charset);
			//// 空直接返回
			if (html.isEmpty() || null == html) {
				return null;
			}
			parseDoc = Jsoup.parse(html, baseUrl);
			switch (type) {
			case booklist:
				Elements listElements = parseDoc.getElementsByClass("result-list");
				if (null != listElements) {
					Element firstBook = listElements.get(0);
					Element result = firstBook.getElementsByClass("result-game-item-detail").get(0);
					if (null != result) {
						Book book = new Book();
						book.setName(result.getElementsByTag("a").first().text());
						book.setUrl(result.select("a").attr("href").toString());
						//// 这里将书的地址赋给下次需要处理的url中
						bookUrl = book.getUrl();
						result = result.getElementsByClass("result-game-item-info").get(0);
						if (null != result) {
							//// 笔趣阁没有作者url
							book.setAuthor(result.getElementsByTag("span").get(1).text());
							book.setNewChapter(result.getElementsByAttribute("href").text());
							book.setNewChapterUrl(result.select("a").attr("href").toString());

							//// 先放着
							// book.setType();

							book.setLastUpdateTime(
									Tool.ConvertDate(result.getElementsByTag("span").get(5).text(), "yy-MM-dd"));
							return book;
						}
						// System.out.println(book.toString());
					}
				}
				break;
			// 先只取第一个吧
			// List<Book> list = new ArrayList<Book>();
			// listElements.forEach(s ->
			// s.getElementsByClass("result-game-item-detail").forEach(x -> {
			// Book book = new Book();
			// book.name = x.getElementsByTag("a").first().text();
			// book.url = x.select("a").attr("href").toString();
			// x.getElementsByClass("result-game-item-info").forEach(o -> {
			// book.author = o.getElementsByTag("span").get(1).text();
			// book.newChapter = o.getElementsByAttribute("href").text();
			// book.newChapterUrl = o.select("a").attr("href").toString();
			// book.lastUpdateTime = o.getElementsByTag("span").get(5).text();
			// System.out.println(book.toString());
			// list.add(book);
			// });
			// }));
			case chapterlist:
				Elements chapters = parseDoc.getElementById("list").getElementsByTag("dd");
				if (chapters != null) {
					List<Chapter> chapterList = new ArrayList<Chapter>();
					chapters.forEach(o -> {
						Chapter chapter = new Chapter();
						String chapterUrl = o.select("a").attr("href").trim();
						if (!chapterUrl.contains("http://")) {
							chapterUrl = baseUrl.concat(chapterUrl);
						}
						chapter.setUrl(chapterUrl);
						chapter.setTitle(o.getElementsByTag("a").text());
						System.out.println(chapter.toString());
						chapterList.add(chapter);
					});
					return chapterList;
				}
				break;
			case content:
				Content content = getContent(html, bookUrl);
				return content;
			default:
				break;
			}
		} catch (ParseException e) {
			// FIXME Auto-generated catch block
			e.printStackTrace();
		}
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSearchBookUrl() {
		return url;
	}

	@Override
	public String getCharset() {
		return charset;
	}

}

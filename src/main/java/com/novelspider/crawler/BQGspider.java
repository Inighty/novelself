package com.novelspider.crawler;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.novelspider.entity.Book;
import com.novelspider.enums.Type;

/**
 * @author Administrator 笔趣阁
 */
public class BQGspider extends Abstractspider {

	@Override
	public Object analyzeHTMLByString(Type type, String html) {
		switch (type) {
		case booklist:
			parseDoc = Jsoup.parse(html, "http://www.biquge.com/");
			Elements listElements = parseDoc.getElementsByClass("result-list");
			if (null != listElements) {
				Book book = new Book();
				Element firstBook = listElements.get(0);
				Element result = firstBook.getElementsByClass("result-game-item-detail").get(0);
				if (null != result) {
					book.name = result.getElementsByTag("a").first().text();
					book.url = result.select("a").attr("href").toString();
					////这里将书的地址赋给下次需要处理的url中
					nextUrl = book.url;
					result = result.getElementsByClass("result-game-item-info").get(0);
					if (null != result) {
						book.author = result.getElementsByTag("span").get(1).text();
						book.newChapter = result.getElementsByAttribute("href").text();
						book.newChapterUrl = result.select("a").attr("href").toString();
						book.lastUpdateTime = result.getElementsByTag("span").get(5).text();
					}
					System.out.println(book.toString());
				}
			}
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
			break;
		case chapterlist:
			
			break;
		case content:
			break;
		default:
			break;
		}
		System.out.println();

		// TODO Auto-generated method stub
		return null;
	}

}

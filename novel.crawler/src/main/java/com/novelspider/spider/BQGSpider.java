package com.novelspider.spider;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.novelspider.entity.Book;
import com.novelspider.entity.Chapter;
import com.novelspider.entity.Content;
import com.novelspider.enums.Type;

/**
 * @author Administrator 笔趣阁
 */
public class BQGSpider extends AbstractSpider {

	public BQGSpider() {
		super("BQG");
		// FIXME 自动生成的构造函数存根
	}

//	public String web = "BQG";
	public String baseUrl = "http://www.biquge.tw";
	public String charset = "utf-8";
	public String url = "http://zhannei.baidu.com/cse/search?s=16829369641378287696&q=";
	
	@Override
	public Object analyzeHTMLByString(Type type, String url) {
		String html = pickData(url, charset);
		//// 空直接返回
		if (html.isEmpty()) {
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
					book.name = result.getElementsByTag("a").first().text();
					book.url = result.select("a").attr("href").toString();
					 //// 这里将书的地址赋给下次需要处理的url中
					bookUrl = book.url;
					result = result.getElementsByClass("result-game-item-info").get(0);
					if (null != result) {
						book.author = result.getElementsByTag("span").get(1).text();
						book.newChapter = result.getElementsByAttribute("href").text();
						book.newChapterUrl = result.select("a").attr("href").toString();
						book.lastUpdateTime = result.getElementsByTag("span").get(5).text();
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
			Content content = getContent(html,bookUrl);
			return content;
		default:
			break;
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
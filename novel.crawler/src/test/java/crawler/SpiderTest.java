package crawler;


import novel.crawler.entity.Book;
import novel.crawler.entity.Chapter;
import novel.crawler.entity.Content;
import novel.crawler.enums.Type;
import novel.crawler.interfaces.INovelSpider;
import novel.crawler.spider.Spider;
import org.junit.Test;

import java.util.List;

public class SpiderTest {

	@SuppressWarnings("unchecked")
	@Test
	public void testContent() {
		INovelSpider iNovelSpider = new Spider("http://www.x23us.com/html/66/66899/27647340.html", Type.content);
		Content list = (Content) iNovelSpider.analyzeHTMLByString();
//		for (Book chapter : list) {
		System.out.println(list.getContent().toString());
//		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testChapter() {
		INovelSpider iNovelSpider = new Spider("http://www.x23us.com/html/66/66899/", Type.chapterlist);
		List<Chapter> list = (List<Chapter>) iNovelSpider.analyzeHTMLByString();
		for (Chapter chapter : list) {
			System.out.println(chapter.toString());
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testBooks() {
		INovelSpider iNovelSpider = new Spider("http://www.hunhun520.com/shuku/0_0_b_0_0_0_0_0_1.html", Type.booklist);
		List<Book> list = (List<Book>) iNovelSpider.analyzeHTMLByString();
		for (Book book : list) {
			System.out.println(book.toString());
		}
	}

}

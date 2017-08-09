package crawler;


import novel.crawler.entity.Book;
import novel.crawler.entity.Chapter;
import novel.crawler.enums.Type;
import novel.crawler.interfaces.INovelSpider;
import novel.crawler.spider.Spider;
import org.junit.Test;

import java.util.List;

public class SpiderTest {

	@SuppressWarnings("unchecked")
	@Test
	public void test() {
		INovelSpider iNovelSpider = new Spider("http://www.kanshuzhong.com/map/E/1/", Type.booklist);
		List<Book> list = (List<Book>) iNovelSpider.analyzeHTMLByString();
		for (Book chapter : list) {
			System.out.println(chapter.toString());
		}
	}
}

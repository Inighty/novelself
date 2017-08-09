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
	public void test() {
		INovelSpider iNovelSpider = new Spider("http://www.kanshuzhong.com/book/36456/25146620.html", Type.content);
		Content list = (Content) iNovelSpider.analyzeHTMLByString();
//		for (Book chapter : list) {
		System.out.println(list.getContent().toString());
//		}
	}
}

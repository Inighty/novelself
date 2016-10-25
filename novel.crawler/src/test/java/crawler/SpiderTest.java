package crawler;

import java.util.List;

import org.junit.Test;

import novel.crawler.entity.Book;
import novel.crawler.factory.SpiderFactory;
import novel.crawler.interfaces.INovelSpider;
import novel.crawler.spider.BXWXSpider;
import novel.crawler.spider.KSZSpider;

public class SpiderTest {

	@Test
	public void testBXWX(){
		BXWXSpider bxwxSpider = new BXWXSpider();
		List<Book> books = bxwxSpider.getAllBooks("http://www.bxwx8.org/binitialS/0/1.htm",10);
		for(Book book:books){
			System.out.println(book.toString());
		}
	}
	
	@Test
	public void testInterator(){
		INovelSpider iNovelSpider = SpiderFactory.SpiderGenerate("KSZ");
		iNovelSpider.getAllBooks("http://www.kanshuzhong.com/map/U/1/",10);
		System.out.println(iNovelSpider.hasNext());
//		System.out.println(iNovelSpider.next());
	}

	@Test
	public void test1(){
		KSZSpider kszSpider = new KSZSpider();
		List<Book> books = kszSpider.getAllBooks("http://www.kanshuzhong.com/map/A/1/",10);
		for(Book book:books){
			System.out.println(book.toString());
		}
	}
	
	@Test
	public void test() throws ClassNotFoundException {
//		String book = "诛仙";
//		String url = "http://zhannei.baidu.com/cse/search?s=287293036948159515&q=" + book;
//		String charset = "utf-8";
		// http://zhannei.baidu.com/cse/search?s=287293036948159515&q=%E8%AF%9B%E4%BB%99
		// BQGSpider bQGspider = new BQGSpider();
		// String html = bQGspider.pickData(url,charset);
		// Book bQGbook =
		// (Book)(bQGspider.analyzeHTML ByString(Type.booklist,html));

//		SpiderFactory spiderFactory = new SpiderFactory();
////		spiderFactory.SpiderGenerate(Web.BQG.toString());
//		 List<INovelSpider> list = spiderFactory.SpiderListGenerate();
//		 list.forEach(o->o.pickData(url, charset));
	}

}

package crawler;

import java.util.List;

import org.junit.Test;

import com.novelspider.factory.SpiderFactory;
import com.novelspider.interfaces.INovelSpider;

public class SpiderTest {

	@Test
	public void test() throws ClassNotFoundException {
		String book = "诛仙";
		String url = "http://zhannei.baidu.com/cse/search?s=287293036948159515&q=" + book;
		String charset = "utf-8";
		// http://zhannei.baidu.com/cse/search?s=287293036948159515&q=%E8%AF%9B%E4%BB%99
//		BQGSpider bQGspider = new BQGSpider();
//		String html = bQGspider.pickData(url,charset);
//		Book bQGbook =  (Book)(bQGspider.analyzeHTMLByString(Type.booklist,html));
		
		SpiderFactory spiderFactory = new SpiderFactory();
		List<INovelSpider> list = spiderFactory.SpiderListGenerate();
	}

}

package crawler;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.junit.Test;

import com.novelspider.enums.Web;
import com.novelspider.factory.SpiderFactory;
import com.novelspider.interfaces.INovelSpider;

public class SpiderTest {

	@Test
	public void test() throws ClassNotFoundException {
		String book = "诛仙";
		String url = "http://zhannei.baidu.com/cse/search?s=287293036948159515&q=" + book;
		String charset = "utf-8";
		// http://zhannei.baidu.com/cse/search?s=287293036948159515&q=%E8%AF%9B%E4%BB%99
		// BQGSpider bQGspider = new BQGSpider();
		// String html = bQGspider.pickData(url,charset);
		// Book bQGbook =
		// (Book)(bQGspider.analyzeHTMLByString(Type.booklist,html));

		SpiderFactory spiderFactory = new SpiderFactory();
//		spiderFactory.SpiderGenerate(Web.BQG.toString());
		 List<INovelSpider> list = spiderFactory.SpiderListGenerate();
		 list.forEach(o->o.pickData(url, charset));
	}

}

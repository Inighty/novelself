package crawler;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import com.novelspider.crawler.BQGspider;
import com.novelspider.entity.Book;
import com.novelspider.enums.Type;

public class spidertest {

	@Test
	public void test() throws ClassNotFoundException {
		String book = "诛仙";
		String url = "http://zhannei.baidu.com/cse/search?s=287293036948159515&q=" + book;
		String charset = "utf-8";
		// http://zhannei.baidu.com/cse/search?s=287293036948159515&q=%E8%AF%9B%E4%BB%99
		BQGspider bQGspider = new BQGspider();
		String html = bQGspider.pickData(url,charset);
		List<Book> booklist =  (ArrayList<Book>)(bQGspider.analyzeHTMLByString(Type.booklist,html));
	}

}

import novel.crawler.enums.Type;
import novel.crawler.spider.Spider;
import novel.crawler.util.Request;
import novel.web.service.NovelServiceImpl;
import org.junit.Test;

import java.io.IOException;

public class Testcase {
	@Test
	public void testChapterList() throws IOException {
		String url = "aHR0cHM6Ly93d3cueDIzdXMuY29tL2h0bWwvNTEvNTE1NTEv";
		url = Request.decryptBASE64(url);
		System.out.println(new Spider(url, Type.chapterlist).analyzeHTMLByString());
	}

	@Test
	public void testQuery(){
		new NovelServiceImpl().getsNovelByKeyword("44");
	}
}

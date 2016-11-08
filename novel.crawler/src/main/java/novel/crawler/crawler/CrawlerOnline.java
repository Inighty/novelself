package novel.crawler.crawler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import novel.crawler.entity.Book;
import novel.crawler.entity.Chapter;
import novel.crawler.entity.Content;
import novel.crawler.enums.Type;
import novel.crawler.factory.SpiderFactory;
import novel.crawler.spider.AbstractSpider;

/**
 * @author mcshr 在线爬虫类
 */
public class CrawlerOnline {
	public String bookStr = "诛仙";

	@SuppressWarnings("unchecked")
	public void Excute() {
		// spiderFactory.SpiderGenerate(Web.BQG.toString());
		List<AbstractSpider> list = new SpiderFactory().SpiderListGenerate();

		list.forEach(o -> {
			Book book = (Book) o.analyzeHTMLByString(Type.booklist, o.getSearchBookUrl() + bookStr);
			if (book != null) {
				Map<Chapter, Content> map = new HashMap<Chapter, Content>();
				List<Chapter> chapterList = (List<Chapter>) o.analyzeHTMLByString(Type.chapterlist, book.getUrl());
				chapterList.forEach(p -> {
					Content content = (Content) o.analyzeHTMLByString(Type.content, p.getUrl());
					System.out.println(content.toString());
					map.put(p, content);
				});
			}
		});
	}
}

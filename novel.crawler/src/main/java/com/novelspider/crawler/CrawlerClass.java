package com.novelspider.crawler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.novelspider.entity.Book;
import com.novelspider.entity.Chapter;
import com.novelspider.entity.Content;
import com.novelspider.enums.Type;
import com.novelspider.factory.SpiderFactory;
import com.novelspider.spider.AbstractSpider;

public class CrawlerClass {
	public String bookStr = "诛仙";

	public void Excute() {
		// spiderFactory.SpiderGenerate(Web.BQG.toString());
		List<AbstractSpider> list = new SpiderFactory().SpiderListGenerate();
		
		list.forEach(o -> {
			Book book = (Book) o.analyzeHTMLByString(Type.booklist, o.getSearchBookUrl() + bookStr);
			if (book != null) {
				Map<Chapter, Content> map = new HashMap<Chapter, Content>();
				List<Chapter> chapterList = (List<Chapter>) o.analyzeHTMLByString(Type.chapterlist, book.url);
				chapterList.forEach(p -> {
					Content content = (Content) o.analyzeHTMLByString(Type.content, p.getUrl());
					System.out.println(content.toString());
					map.put(p, content);
				});
			}
		});
	}
}

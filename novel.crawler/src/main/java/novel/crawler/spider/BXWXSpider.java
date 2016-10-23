package novel.crawler.spider;

import java.util.ArrayList;
import java.util.List;

import novel.crawler.entity.Book;
import novel.crawler.enums.Type;
import novel.crawler.interfaces.INovelSpider;

public class BXWXSpider extends AbstractSpider implements INovelSpider {

	public BXWXSpider() {
		super("BXWX");
		// FIXME Auto-generated constructor stub
	}

	@Override
	public String getSearchBookUrl() {
		// FIXME Auto-generated method stub
		return null;
	}

	@Override
	public String getCharset() {
		// FIXME Auto-generated method stub
		return null;
	}

	@Override
	public Object analyzeHTMLByString(Type type, String url) {
		// FIXME Auto-generated method stub
		return null;
	}

	/**
	 * (BXWX、KSZ用) 根据url获取小说实体列表
	 * 
	 * @param url
	 * @return 返回小说实体列表
	 */
	@Override
	public List<Book> getAllBooks(String url, Integer maxTryTime) {
		
		return new ArrayList<Book>();
	}


}

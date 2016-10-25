package novel.web.service;

import java.util.List;

import novel.crawler.entity.Book;;

public interface NovelService {
	/**
	 * 通过查询关键词，去数据库中查询结果，然后返回想要的内容
	 * @param keyword
	 * @return
	 */
	public List<Book> getsNovelByKeyword(String keyword);
	/**
	 * 查找对应平台下面的小说
	 * @param keyword
	 * @param source
	 * @return
	 */
	public List<Book> getsNovelByKeyword(String keyword, String source);
}

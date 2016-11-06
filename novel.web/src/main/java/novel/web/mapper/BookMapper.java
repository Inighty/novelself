package novel.web.mapper;

import java.util.List;
import java.util.Map;

import novel.crawler.entity.Book;

public interface BookMapper {
	public int deleteByPrimaryKey(Long id);

	public int insert(Book record);

	public int insertSelective(Book record);

	public Book selectByPrimaryKey(Long id);

	public int updateByPrimaryKeySelective(Book record);

	public int updateByPrimaryKey(Book record);

	public void batchInsert(List<Book> books);

	public List<Book> getsNovelByKeyword(String keyword);

	public List<Book> getsNovelByKeyword2(Map<String, String> map);

	public List<Book> getsNovelByAuthor(String keyword);
}
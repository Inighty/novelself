package novel.storage.mapper;

import java.util.List;

import novel.crawler.entity.Book;

public interface BookMapper {
	public int deleteByPrimaryKey(Long id);

	public int insert(Book record);

	public int insertSelective(Book record);

	public Book selectByPrimaryKey(Long id);

	public int updateByPrimaryKeySelective(Book record);

	public int updateByPrimaryKey(Book record);

	public void batchInsert(List<Book> books);
}
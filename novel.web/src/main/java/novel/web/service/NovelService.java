package novel.web.service;

import java.util.List;

import novel.crawler.entity.Book;;

public interface NovelService {
	/**
	 * ͨ����ѯ�ؼ��ʣ�ȥ���ݿ��в�ѯ�����Ȼ�󷵻���Ҫ������
	 * @param keyword
	 * @return
	 */
	public List<Book> getsNovelByKeyword(String keyword);
	/**
	 * ���Ҷ�Ӧƽ̨�����С˵
	 * @param keyword
	 * @param source
	 * @return
	 */
	public List<Book> getsNovelByKeyword(String keyword, String source);
}

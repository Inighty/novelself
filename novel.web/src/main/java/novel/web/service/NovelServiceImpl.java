package novel.web.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import novel.crawler.entity.Book;
import novel.web.mapper.BookMapper;

@Service
public class NovelServiceImpl implements NovelService {
	@Resource
	private BookMapper mapper;

	@Override
	public List<Book> getsNovelByKeyword(String keyword) {
		String likekeyword = "%" + keyword + "%";
		List<Book> list = mapper.getsNovelByKeyword(likekeyword);
		List<Book> listTemp = new ArrayList<Book>();
		for (Book book : list) {
			if (book.getName().equals(keyword)) {
				listTemp.add(book);
			}
		}
		list.removeAll(listTemp);
		listTemp.addAll(list);
		return listTemp;
	}

	@Override
	public List<Book> getsNovelByKeyword(String keyword, String source) {
		Map<String, String> map = new HashMap<>();
		map.put("keyword", "%" + keyword + "%");
		map.put("source", source + "");
		return mapper.getsNovelByKeyword2(map);
	}

	@Override
	public List<Book> getsNovelByAuthor(String keyword) {
		return mapper.getsNovelByAuthor(keyword);
	}

}

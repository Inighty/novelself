package novel.web.service;

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
//		keyword = "%" + keyword + "%";
		Map<String, String> map = new HashMap<>();
		map.put("keyword", keyword);
		map.put("likeKeyword", "%" + keyword + "%");
		return mapper.getsNovelByKeyword(map);
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

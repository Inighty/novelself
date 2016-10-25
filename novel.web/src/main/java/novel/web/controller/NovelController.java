package novel.web.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import novel.crawler.entity.Chapter;
import novel.crawler.entity.Content;
import novel.crawler.enums.Type;
import novel.crawler.factory.SpiderFactory;
import novel.web.entity.JsonResponse;

@Controller
public class NovelController {

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/chapters.do", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getChapter(String url) {
		List<Chapter> chapterList = (List<Chapter>) SpiderFactory.SpiderGenerate(url)
				.analyzeHTMLByString(Type.chapterlist, url);
		return JsonResponse.success(chapterList);
	}

	@RequestMapping(value = "/content.do", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getContent(String url) {
		Content content = (Content) SpiderFactory.SpiderGenerate(url).analyzeHTMLByString(Type.content, url);
		return JsonResponse.success(content);
	}
}

package novel.web.controller;

import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import novel.crawler.entity.Chapter;
import novel.crawler.entity.Content;
import novel.crawler.enums.Type;
import novel.crawler.factory.SpiderFactory;
import novel.web.entity.JsonResponse;
import novel.web.service.NovelService;

@Controller
public class NovelController {

	@Resource
	private NovelService service;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ins/chapters.do", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getChapter(String url) {
		List<Chapter> chapterList = (List<Chapter>) SpiderFactory.SpiderGenerate(url)
				.analyzeHTMLByString(Type.chapterlist, url);
		return JsonResponse.success(chapterList);
	}

	@RequestMapping(value = "/ins/content.do", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getContent(String url) {
		Content content = (Content) SpiderFactory.SpiderGenerate(url).analyzeHTMLByString(Type.content, url);
		return JsonResponse.success(content);
	}

	@RequestMapping(value = "/search.do", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse getsNovelByKeyword(String keyword) throws UnsupportedEncodingException {
		// System.out.println(keyword);
		// keyword = new String(keyword.getBytes("ISO-8859-1"), "utf-8");
		// System.out.println(keyword);
		return JsonResponse.success(service.getsNovelByKeyword(keyword));
	}

	@RequestMapping(value = "/search2.do", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse getsNovelByKeyword(String keyword, String source) throws UnsupportedEncodingException {
		keyword = new String(keyword.getBytes("ISO-8859-1"), "utf-8");
		source = new String(source.getBytes("ISO-8859-1"), "utf-8");
		return JsonResponse.success(service.getsNovelByKeyword(keyword, source));
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/chapters.do", method = RequestMethod.GET)
	public ModelAndView showChapterList(String url) {
		ModelAndView view = new ModelAndView();
		view.setViewName("chapters");
		view.getModel().put("chapters",
				(List<Chapter>) SpiderFactory.SpiderGenerate(url).analyzeHTMLByString(Type.chapterlist, url));
		view.getModel().put("baseUrl", url);
		return view;
	}

	@RequestMapping(value = "/content.do", method = RequestMethod.GET)
	public ModelAndView showChapterDetail(String url, String baseUrl) {
		ModelAndView view = new ModelAndView();
		view.setViewName("content");
		try {
			Content content = (Content) SpiderFactory.SpiderGenerate(url).analyzeHTMLByString(Type.content, url);
			content.setContent(content.getContent().replaceAll("\n", "<br>"));
			view.getModel().put("content", content);
			view.getModel().put("isSuccess", true);
		} catch (Exception e) {
			e.printStackTrace();
			view.getModel().put("isSuccess", false);
		}
		view.getModel().put("baseUrl", baseUrl);
		return view;
	}
}

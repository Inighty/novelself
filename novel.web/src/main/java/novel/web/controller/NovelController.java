package novel.web.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import novel.crawler.spider.Spider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import novel.crawler.entity.Chapter;
import novel.crawler.entity.Content;
import novel.crawler.enums.Type;
import novel.crawler.util.Request;
import novel.web.entity.JsonResponse;
import novel.web.service.NovelService;

@Controller
public class NovelController {

	@Resource
	private NovelService service;

	@Autowired
	private HttpServletRequest request;

	@RequestMapping(value = "/ins/chapters.do", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getChapter(String url) {
		List<Chapter> chapterList = (List<Chapter>) new Spider(url, Type.chapterlist).analyzeHTMLByString();
		return JsonResponse.success(chapterList);
	}

	@RequestMapping(value = "/ins/content.do", method = RequestMethod.GET)
	@ResponseBody
	public JsonResponse getContent(String url) {
		Content content = (Content) new Spider(url, Type.content).analyzeHTMLByString();
		return JsonResponse.success(content);
	}

	@RequestMapping(value = "/search.do", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse getsNovelByKeyword(String keyword, String flag) throws UnsupportedEncodingException {
		// System.out.println(keyword);
		// keyword = new String(keyword.getBytes("ISO-8859-1"), "utf-8");
		// System.out.println(keyword);
		if (flag.equals("")) {
			return JsonResponse.success(service.getsNovelByKeyword(keyword));
		} else {
			return JsonResponse.success(service.getsNovelByAuthor(keyword));
		}
	}

	@RequestMapping(value = "/download.do", method = RequestMethod.POST)
	@ResponseBody
	public JsonResponse getNovelTxtUrl(String bookUrl) throws IOException {
		bookUrl = Request.decryptBASE64(bookUrl);
		// System.out.println(keyword);
		// keyword = new String(keyword.getBytes("ISO-8859-1"), "utf-8");
		// System.out.println(keyword);
		// return
		// SpiderFactory.SpiderGenerate(bookUrl).getDownloadTxtUrl(bookUrl);
		return JsonResponse.success(new Spider(bookUrl, Type.downloadUrl).getDownloadTxtUrl(bookUrl));
	}
//
//	@RequestMapping(value = "/search2.do", method = RequestMethod.POST)
//	@ResponseBody
//	public JsonResponse getsNovelByKeyword(String keyword, String source) throws UnsupportedEncodingException {
//		keyword = new String(keyword.getBytes("ISO-8859-1"), "utf-8");
//		source = new String(source.getBytes("ISO-8859-1"), "utf-8");
//		return JsonResponse.success(service.getsNovelByKeyword(keyword, source));
//	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/chapters.do", method = RequestMethod.GET)
	public ModelAndView showChapterList(String url, String book) throws IOException {
		// System.out.println("����ǰ:"+url);
		url = Request.decryptBASE64(url);
		// System.out.println("�����:"+url);
		ModelAndView view = new ModelAndView();
		view.setViewName("chapters");
		try {
			// view.addObject("isMobile",
			// Request.checkAgentIsMobile(getUserAgent()));
			view.getModel().put("book", new String(book.getBytes("ISO-8859-1"), "utf-8"));
			view.getModel().put("bookUrl", Request.encryptBASE64(url));
			view.getModel().put("isMobile", Request.checkAgentIsMobile(getUserAgent()));
			view.getModel().put("chapters", new Spider(url, Type.chapterlist).analyzeHTMLByString());
			view.getModel().put("baseUrl", Request.encryptBASE64(url));
			view.getModel().put("isSuccess", true);
		} catch (Exception e) {
			e.printStackTrace();
			view.getModel().put("isSuccess", false);
		}
		return view;
	}

	@RequestMapping(value = "/content.do", method = RequestMethod.GET)
	public ModelAndView showChapterDetail(String url, String baseUrl, String book) throws IOException {

		String noBase64url = Request.decryptBASE64(url);
		String noBase64baseUrl = Request.decryptBASE64(baseUrl);
		if (noBase64baseUrl.contains(noBase64url)) {
			return showChapterList(baseUrl, book);
		}

		// String noBase64baseUrl = Request.decryptBASE64(baseUrl);
		ModelAndView view = new ModelAndView();
		view.setViewName("content");
		try {
			Content content = (Content) new Spider(noBase64url, Type.content).analyzeHTMLByString();
			content.setContent(content.getContent().replaceAll("\n", "<br>"));
			view.getModel().put("book", new String(book.getBytes("ISO-8859-1"), "utf-8"));
			view.getModel().put("content", content);
			view.getModel().put("isSuccess", true);

		} catch (Exception e) {
			e.printStackTrace();
			view.getModel().put("isSuccess", false);
		}
		view.getModel().put("baseUrl", baseUrl);
		return view;
	}

	// get user agent
	private String getUserAgent() {
		return request.getHeader("user-agent");
	}

}

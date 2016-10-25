package novel.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloNovelSpiderControl {
	@RequestMapping(value = "/hello.do", method = RequestMethod.GET)
	@ResponseBody

	public String sayHello() {
		return "我就知道会这样，真坑";
	}
}

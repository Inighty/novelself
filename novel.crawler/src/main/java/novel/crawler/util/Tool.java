package novel.crawler.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class Tool {

	public final Map<String, Map<String, Element>> ruleMap = new HashMap<>();

	public int MaxTryTime;

	/**
	 * 构造函数
	 */
	@SuppressWarnings("unchecked")
	public Tool() {
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			InputStream stream = classLoader.getResourceAsStream("resources/rule.xml");
			if (stream == null) {
				stream = classLoader.getResourceAsStream("rule.xml");
			}
			String result = IOUtils.toString(stream, "utf8");
			// String result =
			// IOUtils.toString(classLoader.getResourceAsStream("rule.xml"),
			// "utf8");
			Document document = DocumentHelper.parseText(result);
			Element root = document.getRootElement();
			MaxTryTime = Integer.parseInt(root.element("MaxTryTime").getTextTrim());
			List<Element> site = root.elements("Site");
			Map<String, Element> temp;
			for (Element novelSite : site) {
				List<Element> childElements = novelSite.elements();
				temp = new HashMap<>();
				for (Element ele : childElements) {
					temp.put(ele.getName(), ele);
				}
				ruleMap.put(novelSite.elementTextTrim("domain"), temp);
			}
		} catch (IOException | DocumentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 替换特殊字符串
	 *
	 * @return string
	 */
	public static String replaceSpecifyString(String content, String specifyString) throws ParseException {
		if (specifyString != null) {
			if (specifyString.startsWith("#{")) {
				switch (specifyString) {
					case "#{space}":
						return content.replaceAll("#\\{space}", "&nbsp;");
					case "#{line-break}":
						return content.replaceAll("#\\{line-break}", "\n");
					default:
						throw new ParseException(content + "不是合法的表达式！", 0);
				}
			} else {
				return content.replaceAll(specifyString, "");
			}
		}
		return "";
	}


	/**
	 * @param status 状态
	 * @return int result
	 */
	public static int FormatStatus(String status) {
		int statusInt = 1;
		if (status.contains("连载")) {
			statusInt = 1;
		} else if (status.contains("完结") || status.contains("完成")) {
			statusInt = 2;
		}
		return statusInt;
	}

	/**
	 * 将页面中解析到的前一章，下一章的章节地址解析为绝对路径 <br>
	 * 如果absUrl本身就是绝对路径，则原样返回。
	 * 
	 * @param url
	 *            当前章节的完整url地址:http://www.biquge.tw//0_5/1373.html
	 * @param absUrl
	 *            1374.html
	 * @return /0_5/1374.html
	 */
	public static String relativeUrl2FullUrl(String url, String absUrl) {
		if (absUrl.startsWith("http://")) {
			return Request.encryptBASE64(absUrl);
		}
		if (absUrl.startsWith("/")) {
			absUrl = absUrl.substring(1);
		}
		int index = url.lastIndexOf("/");
		if (index < 0) {
			throw new RuntimeException("翻页url解析出错！");
		}
		String after = url.substring(index + 1);
		String newUrl = url.replace(after, absUrl);
		return Request.encryptBASE64(newUrl);
	}
}

package novel.crawler.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

	/**
	 * 构造函数
	 */
	@SuppressWarnings("unchecked")
	public Tool() {
		try {
			ClassLoader classLoader = getClass().getClassLoader();
//			String result = IOUtils.toString(classLoader.getResourceAsStream("resources/rule.xml"), "utf8");
			String result = IOUtils.toString(classLoader.getResourceAsStream("rule.xml"), "utf8");
			Document document = DocumentHelper.parseText(result);
			Element root = document.getRootElement();
			List<Element> site = root.elements("Site");
			Map<String, Element> temp = null;
			for (Element novelSite : site) {
				List<Element> childElements = novelSite.elements();
				temp = new HashMap<>();
				for (Element ele : childElements) {
					temp.put(ele.getName(), ele);
				}
				ruleMap.put(novelSite.elementTextTrim("name"), temp);
			}
		} catch (IOException | DocumentException e) {
			// FIXME 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	/**
	 * 替换特殊字符串
	 * 
	 * @return
	 * @throws ParseRuleException
	 */
	public static String replaceSpecifyString(String content, String specifyString) throws ParseException {
		if (specifyString != null && specifyString.startsWith("#{")) {
			switch (specifyString) {
			case "#{space}":
				return content.replaceAll("#\\{space\\}", "&nbsp;");
			case "#{line-break}":
				return content.replaceAll("#\\{line-break\\}", "\n");
			default:
				throw new ParseException(content + "不是合法的表达式！", 0);
			}
		} else {
			return content.replaceAll(specifyString, "");
		}
	}

	/**
	 * 字符串转Date
	 * 
	 * @param time
	 * @param format
	 * @return Date
	 * @throws ParseException
	 */
	public static Date ConvertDate(String time, String format) throws ParseException {
		if (format.equals("MM-dd")) {
			format = "yyyy-MM-dd";
			time = "2016-" + time;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date date = sdf.parse(time);
		return date;
	}

	/**
	 * 统一小说状态 连载 完结
	 * 
	 * @param Status
	 * @return
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
	 * @param relativeUrl
	 *            1374.html
	 * @return /0_5/1374.html
	 */
	public static String relativeUrl2FullUrl(String url, String absUrl) {
		if (absUrl.startsWith("http://")) {
			return absUrl;
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

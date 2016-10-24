package novel.crawler.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Tool {

	public final Map<String, Map<String, Element>> ruleMap = new HashMap<>();

	/**
	 * 构造函数
	 */
	@SuppressWarnings("unchecked")
	public Tool() {

		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(this.getClass().getResourceAsStream("/rule.xml"));
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
		} catch (DocumentException e) {
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
				return content.replaceAll("#\\{space\\}", " ");
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

}

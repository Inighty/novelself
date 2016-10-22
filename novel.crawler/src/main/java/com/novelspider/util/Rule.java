package com.novelspider.util;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class Rule {
	
	public final Map<String, Map<String, Element>> ruleMap = new HashMap<>();
	
	/**
	 * 构造函数
	 */
	public Rule() {
		
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
	 * @return
	 * @throws ParseRuleException 
	 */
	public static String replaceSpecifyString(String content, String specifyString) throws ParseException {
		if (specifyString != null && specifyString.startsWith("#{")) {
			switch (specifyString) {
			case "#{space}" : return content.replaceAll("#\\{space\\}", " ");
			case "#{line-break}" : return content.replaceAll("#\\{line-break\\}", "\n");
			default : throw new ParseException(content + "不是合法的表达式！", 0);
			}
		} else {
			return content.replaceAll(specifyString, "");
		}
	}
	
}

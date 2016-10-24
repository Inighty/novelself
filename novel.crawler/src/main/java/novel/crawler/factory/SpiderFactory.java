package novel.crawler.factory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import novel.crawler.enums.Web;
import novel.crawler.spider.AbstractSpider;

/**
 * @author Administrator 爬虫工厂实现接口
 */
public class SpiderFactory {

	public static AbstractSpider SpiderGenerate(String web) {
		// TODO Auto-generated method stub
		AbstractSpider spiderObj = null;
		Class<?> spider = null;
		try {
			// System.out.println(web);
			spider = Class.forName("novel.crawler.spider." + Web.getEnumByUrl(web).toString() + "Spider");
			if (spider != null) {
				spiderObj = (AbstractSpider) spider.newInstance();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return spiderObj;
	}

	public List<AbstractSpider> SpiderListGenerate() {
		// TODO Auto-generated method stub
		List<AbstractSpider> spiderList = new ArrayList<AbstractSpider>();
		Properties prop = new Properties();
		try {
			prop.load(this.getClass().getResourceAsStream("/application.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (prop != null) {
			List<String> rank = Arrays.asList(prop.getProperty("rank").split(","));
			rank.forEach(o -> spiderList.add(SpiderGenerate(o)));
		}
		return spiderList;
	}
}

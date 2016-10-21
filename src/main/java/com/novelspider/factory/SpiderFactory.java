package com.novelspider.factory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import com.novelspider.interfaces.INovelSpider;
import com.novelspider.interfaces.ISpiderFactory;

/**
 * @author Administrator 爬虫工厂实现接口
 */
public class SpiderFactory implements ISpiderFactory {

	@Override
	public INovelSpider SpiderGenerate(String web) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<INovelSpider> SpiderListGenerate() {
		// TODO Auto-generated method stub
		InputStream in = SpiderFactory.class.getClassLoader().getResourceAsStream("/application.properties");
		Properties prop = new Properties();
		try {
			prop.load(in);
			String Web = prop.getProperty("rank").trim();
			System.out.println(Web);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}

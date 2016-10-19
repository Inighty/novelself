package com.novelspider.interfaces;

/**
 * @author Administrator
 * 爬取页面接口
 */
public interface INovelspider {
	
	
	/**
	 * 爬取页面返回string
	 * @param url
	 * @return 字符串
	 */
	public String pickData(String url);
	
	
}

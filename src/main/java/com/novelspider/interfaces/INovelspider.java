package com.novelspider.interfaces;

import com.novelspider.enums.Type;

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
	public String pickData(String url,String charset);
	
	/**
	 * 解析html
	 * 
	 * @param html
	 * @return 数据对象
	 */
	public abstract Object analyzeHTMLByString(Type type,String html);
	
	
	
	/**
	 *  获取要处理的url
	 */
	public String getNextUrl();
	
	
	/**
	 * @return 设置要处理的url
	 */
	public void setNextUrl(String nextUrl);
}

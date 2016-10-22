package com.novelspider.interfaces;

import java.text.ParseException;

import com.novelspider.entity.Content;
import com.novelspider.enums.Type;

/**
 * @author Administrator 爬取页面接口
 */
public interface INovelSpider {

	/**
	 * 爬取页面返回string
	 * 
	 * @param url
	 * @return 字符串
	 */
	public String pickData(String url, String charset);

	/**
	 * 解析html
	 * 
	 * @param type
	 * @param url
	 * @return 数据对象
	 * @throws ParseException
	 */
	public Object analyzeHTMLByString(Type type, String url);

	/**
	 * 获取搜索书的url
	 */
	public String getSearchBookUrl();

	/**
	 * 获取网页编码
	 */
	public String getCharset();



	/**
	 * 获取下一章Url
	 * 
	 * @param selector
	 * @return 下一章Url
	 */
	public String getNextUrl();

	/**
	 * 获取上一章Url
	 * 
	 * @param selector
	 * @return 上一章Url
	 */
	public String getPreUrl();

	/**
	 * 获取章节内容
	 * 
	 * @param selector
	 * @return 章节内容
	 * @throws ParseException
	 */
	public String getChapterContent(String crawlString);

	/**
	 * 获取章节名
	 * 
	 * @param selector
	 * @return 章节名
	 */
	public String getChapterName();

	/**
	 * 获取内容对象
	 * 
	 * @param crawlString
	 * @param 书的主地址
	 * @return 内容对象
	 */
	public Content getContent(String crawlString,String bookUrl);
}

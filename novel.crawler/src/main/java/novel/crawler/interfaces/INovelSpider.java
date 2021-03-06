package novel.crawler.interfaces;

import java.text.ParseException;
import java.util.Iterator;
import java.util.List;

import novel.crawler.entity.Book;
import novel.crawler.entity.Content;
import novel.crawler.enums.Type;

/**
 * @author Administrator 爬取页面接口
 */
/**
 * @author Administrator
 *
 */
public interface INovelSpider {

	static final int MAX_TRY_TIME = 3;

	/**
	 * 爬取页面返回string
	 * 
	 * @param url
	 * @return 字符串
	 * @throws Exception
	 */
	public String pickData(String url, String charset) throws Exception;

	/**
	 * 解析html
	 * 
	 * @return 数据对象
	 * @throws ParseException
	 */
	public Object analyzeHTMLByString();
//
//	/**
//	 * 获取搜索书的url
//	 */
//	public String getSearchBookUrl();

	/**
	 * 获取下一章Url
	 * 
	 * @return 下一章Url
	 */
	public String getNextUrl();

	/**
	 * 获取上一章Url
	 * 
	 * @return 上一章Url
	 */
	public String getPreUrl();

	/**
	 * 获取章节内容
	 * 
	 * @return 章节内容
	 */
	public String getChapterContent(String crawlString);

	/**
	 * 获取章节名
	 * 
	 * @return 章节名
	 */
	public String getChapterName();

	/**
	 * 获取内容对象
	 *
	 * @param crawlString 获取字符串
	 * @param bookUrl 书的主地址
	 * @return 内容对象
	 */
	public Content getContent(String crawlString, String bookUrl);

	///// 下面接口 后台爬虫用

	/**
	 * 根据url获取小说实体列表
	 * 
	 * @param url
	 * @return 返回小说实体列表
	 */
	public List<Book> getAllBooks(String url);

	/**
	 * 判断是否还有下一页
	 * 
	 * @return boolean
	 */
	public Boolean hasNext();

//	public String next();

	public Iterator<List<Book>> iterator(String key, String presentUrl);

	/**
	 * 专门去拿精确的时间
	 * 
	 * @param html
	 * @return 时间
	 */
	public String getLastUpdateTime(String html);

	// /**
	// * (BXWX、KSZ用) 根据url获取小说列表中 tr标签
	// *
	// * @param url
	// * @return 小说列表中 tr标签 "http://www.kanshuzhong.com/map/A/1/"
	// */
	//
	// public Elements getTrs(String url,String charset);

	/**
	 * 根据bookurl获取下载链接
	 *
	 * @return 返回txt下载链接
	 */
	public String getDownloadTxtUrl();
}

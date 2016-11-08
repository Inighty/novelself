package novel.crawler.spider;

import novel.crawler.interfaces.INovelSpider;

/**
 * @author Administrator 笔趣阁
 */
public class BQGSpider extends AbstractSpider implements INovelSpider {

	public BQGSpider() {
		super("BQG");
		super.baseUrl = baseUrl;
		super.charset = charset;
		// FIXME 自动生成的构造函数存根
	}

	// public String web = "BQG";
	public String baseUrl = "http://www.biquge.tw";
	public String charset = "utf-8";
	public String url = "http://zhannei.baidu.com/cse/search?s=16829369641378287696&q=";

	@Override
	public String getSearchBookUrl() {
		return url;
	}

	@Override
	public String getLastUpdateTime(String html) {
		// TODO Auto-generated method stub
		return null;
	}

}

package novel.crawler.interfaces;

import java.util.List;

import novel.crawler.spider.AbstractSpider;

/**
 * @author Administrator
 * 爬虫工厂接口
 */
public interface ISpiderFactory {
	/**
	 * @param web
	 * @return 爬虫对象
	 */
	public AbstractSpider SpiderGenerate(String web);
	
	
	/**
	 * @return 所有爬虫
	 */
	public List<AbstractSpider> SpiderListGenerate();
}

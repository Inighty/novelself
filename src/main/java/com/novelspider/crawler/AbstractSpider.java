package com.novelspider.crawler;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.nodes.Document;

import com.novelspider.enums.Type;
import com.novelspider.interfaces.INovelSpider;

/**
 * @author Administrator 抽象类
 */
public abstract class AbstractSpider implements INovelSpider {
	
	
	/**
	 *  jsoup文本处理
	 */
	protected Document parseDoc;
	
	
	public Document getParseDoc() {
		return parseDoc;
	}


	public void setParseDoc(Document parseDoc) {
		this.parseDoc = parseDoc;
	}


	/**
	 *  需要处理的url地址
	 */
	protected String nextUrl;
	
	@Override
	public String getNextUrl() {
		return nextUrl;
	}

	@Override
	public void setNextUrl(String nextUrl) {
		this.nextUrl = nextUrl;
	}


	/*
	 * 爬取网页信息
	 */
	public String pickData(String url, String charset) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		try {
			HttpGet httpget = new HttpGet(url);
			CloseableHttpResponse response = httpclient.execute(httpget);
			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				// 打印响应状态
				if (entity != null) {
					return EntityUtils.toString(entity, charset);
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}


	/**
	 * 解析html
	 * 
	 * @param html
	 * @return 数据对象
	 */
	public abstract Object analyzeHTMLByString(Type type,String html);
}

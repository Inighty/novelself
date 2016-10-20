package com.novelspider.common;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class httphelper {
	public void getHtml(String url){	
		try {
			Document doc = Jsoup.connect("http://www.oschina.net/")   
					 .data("query", "Java")   // 请求参数  
					 .userAgent("I ’ m jsoup") // 设置 User-Agent   
					 .cookie("auth", "token") // 设置 cookie   
					 .timeout(3000)           // 设置连接超时时间  
					 .post();
		} catch (IOException e) {
			// FIXME 自动生成的 catch 块
			e.printStackTrace();
		}                 // 使用 POST 方法访问 URL
	}
}

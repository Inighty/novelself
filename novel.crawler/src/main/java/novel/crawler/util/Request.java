package novel.crawler.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpMessage;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

@SuppressWarnings("restriction")
public class Request {
	//// 爬虫的请求头
	private static final Map<String, String> DEFAULT_NOVEL_SPIDER_HEADER = new HashMap<>();

	public Request() {
	}

	static {
		DEFAULT_NOVEL_SPIDER_HEADER.put("Accept", "*/*");
		DEFAULT_NOVEL_SPIDER_HEADER.put("Accept-Language", "zh-CN,zh;q=0.8");
		DEFAULT_NOVEL_SPIDER_HEADER.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.71 Safari/537.36");
	}
	
	/**
	 * 设置默认请求头
	 * @param message
	 */
	public static void setDefaultNovelSpiderHeader(HttpMessage message) {
		Set<String> keySet = DEFAULT_NOVEL_SPIDER_HEADER.keySet();
		for (String str : keySet) {
			message.setHeader(str, DEFAULT_NOVEL_SPIDER_HEADER.get(str));
		}
	}
	
	

	/**
	 * BASE64解密
	 * 
	 * @param key
	 * @return
	 * @throws IOException 
	 * @throws Exception
	 */
	public static String decryptBASE64(String key) throws IOException{
		return new String(new BASE64Decoder().decodeBuffer(key));
	}

	/**
	 * BASE64加密
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(String key){
		return (new BASE64Encoder()).encodeBuffer(key.getBytes());
	}
	
}

package novel.crawler.enums;

import java.io.Serializable;

public enum Web implements Serializable {
	BQG("BQG", "www.biquge.tw"), BXWX("BXWX", "www.bxwx8.org"), KSZ("KSZ", "www.kanshuzhong.com");

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String domain;

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	private Web(String name, String domain) {
		this.name = name;
		this.domain = domain;
	}

	public static Web getEnumByUrl(String url) {
		if (url == null)
			throw new IllegalArgumentException("url 不能为null");

		for (Web web : values()) {
			if ((!url.contains("http://")) && url.contains(web.getName())) {
				return web;
			}
			if (url.contains(web.getDomain())) {
				return web;
			}
		}
		throw new RuntimeException("不支持的网站：" + url);
	}

}

package novel.crawler.enums;

import java.io.Serializable;

public enum Web implements Serializable {
	BQG("biquge"), BXWX("bxwx8"), KSZ("kanshuzhong");

	private String domain;

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	private Web(String domain) {
		this.domain = domain;
	}

	public static Web getEnumByUrl(String url) {
		if (url == null)
			throw new IllegalArgumentException("url 不能为null");
		for (Web web : values()) {
			if (url.contains(web.getDomain())) {
				return web;
			}
		}
		throw new RuntimeException("不支持的网站：" + url);
	}

}

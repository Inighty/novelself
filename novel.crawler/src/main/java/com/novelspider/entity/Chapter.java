package com.novelspider.entity;

public class Chapter {
	/// 连接
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/// 章节名
	private String title;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "Chapter [url=" + url + ", title=" + title + "]";
	}
}

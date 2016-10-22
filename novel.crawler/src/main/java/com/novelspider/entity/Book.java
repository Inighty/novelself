package com.novelspider.entity;

import com.novelspider.interfaces.IEntity;

/**
 * @author Administrator 小说model
 */
public class Book implements IEntity {

	public String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//// 书的地址
	public String url = "";

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	// 书名
	public String author;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	//// 作者地址
	public String authorUrl = "";

	public String getAuthorUrl() {
		return authorUrl;
	}

	public void setAuthorUrl(String authorUrl) {
		this.authorUrl = authorUrl;
	}

	/**
	 * 状态 默认为连载中 0：连载中 1：完结
	 */
	public int status = 0;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String newChapter;

	public String getNewChapter() {
		return newChapter;
	}

	public void setNewChapter(String newChapter) {
		this.newChapter = newChapter;
	}

	public String newChapterUrl;

	public String getNewChapterUrl() {
		return newChapterUrl;
	}

	public void setNewChapterUrl(String newChapterUrl) {
		this.newChapterUrl = newChapterUrl;
	}

	public String lastUpdateTime = "";

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	@Override
	public String toString() {
		return "Book [name=" + name + ", url=" + url + ", author=" + author + ", authorUrl=" + authorUrl + ", status="
				+ status + ", newChapter=" + newChapter + ", newChapterUrl=" + newChapterUrl + ", lastUpdateTime="
				+ lastUpdateTime + "]";
	}
}

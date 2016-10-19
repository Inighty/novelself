package com.novelspider.entity;

/**
 * @author Administrator 小说model
 */
public class Book {

	public String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// 书名
	public String author;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * 状态
	 */
	public int status;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Book [name=" + name + ", author=" + author + ", status=" + status + "]";
	}
}

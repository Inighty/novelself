package novel.crawler.entity;

import java.util.Date;

import novel.crawler.interfaces.IEntity;

/**
 * @author Administrator 小说model
 */
public class Book implements IEntity {

	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	//// 书的地址
	private String url = "";

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	// 作者
	private String author;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	//// 作者地址
	private String authorUrl = "";

	public String getAuthorUrl() {
		return authorUrl;
	}

	public void setAuthorUrl(String authorUrl) {
		this.authorUrl = authorUrl;
	}

	private String type = "";

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 状态 默认为连载  (1:连载  2:完结)
	 */
	private int status =1;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	private String newChapter;

	public String getNewChapter() {
		return newChapter;
	}

	public void setNewChapter(String newChapter) {
		this.newChapter = newChapter;
	}

	private String newChapterUrl;

	public String getNewChapterUrl() {
		return newChapterUrl;
	}

	public void setNewChapterUrl(String newChapterUrl) {
		this.newChapterUrl = newChapterUrl;
	}

	
	private Date lastUpdateTime;

	public Date getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	private String source = "";

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * 添加时间
	 */
	private Date addTime;
	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	/**
	 * 更新数据库时间
	 */
	private Date updateTime;
	
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", name=" + name + ", url=" + url + ", author=" + author + ", authorUrl=" + authorUrl
				+ ", type=" + type + ", status=" + status + ", newChapter=" + newChapter + ", newChapterUrl="
				+ newChapterUrl + ", lastUpdateTime=" + lastUpdateTime + ", source=" + source + ", addTime=" + addTime
				+ ", updateTime=" + updateTime + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
}

package novel.crawler.entity;

/**
 * @author mcshr 章节内容实体
 */
public class Content {
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 前一章Url
	 */
	private String pre;
	/**
	 * 后一章url
	 */
	private String next;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPre() {
		return pre;
	}

	public void setPre(String pre) {
		this.pre = pre;
	}

	public String getNext() {
		return next;
	}

	public void setNext(String next) {
		this.next = next;
	}

	@Override
	public String toString() {
		return "Content [title=" + title + ", content=" + content.substring(0, 20) + ", pre=" + pre + ", next=" + next
				+ "]";
	}
}

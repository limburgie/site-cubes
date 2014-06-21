package be.webfactor.sitecubes.domain;

import javax.persistence.*;

@Entity
public class ContentLocation extends BaseEntity {

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "content_item_id")
	private ContentItem item;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "page_id")
	private Page page;

	@Column(name = "column_id")
	private String columnId;

	@Column(name = "position")
	private int position;

	public ContentItem getItem() {
		return item;
	}

	public void setItem(ContentItem item) {
		this.item = item;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}

	public String getColumnId() {
		return columnId;
	}

	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

}

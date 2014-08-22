package be.webfactor.sitecubes.domain;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(name = "UQ_PAGE_COL_POS", columnNames = {"page_id", "column_id", "position"})})
public class ContentLocation extends BaseEntity {

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "content_item_id", nullable = false)
	private ContentItem item;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "page_id", nullable = false)
	private Page page;

	@Column(name = "column_id", nullable = true)
	private String columnId;

	@Column(name = "position", nullable = false)
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

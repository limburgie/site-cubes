package be.webfactor.sitecubes.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(name = "UQ_SITE_TITLE", columnNames = {"site_id", "title"})
})
public class ContentItem extends BaseEntity {

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "content") @Lob
	private String content;

	@Column(name = "create_date")
	private Date createDate;

	@Column(name = "modified_date")
	private Date modifiedDate;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "site_id", nullable = false)
	private Site site;

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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}

}

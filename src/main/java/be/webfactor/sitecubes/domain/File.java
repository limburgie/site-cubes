package be.webfactor.sitecubes.domain;

import javax.persistence.*;
import java.util.Date;

@Entity
public class File extends BaseEntity {

	@Column(name = "file_name", nullable = false)
	private String fileName;

	@Column(name = "data") @Lob
	private byte[] data;

	@Column(name = "modified_date")
	private Date modifiedDate;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "site_id", nullable = false)
	private Site site;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
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

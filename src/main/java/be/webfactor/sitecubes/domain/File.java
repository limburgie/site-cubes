package be.webfactor.sitecubes.domain;

import org.apache.commons.io.FileUtils;

import javax.persistence.*;
import java.util.Date;

@Entity
public class File extends BaseEntity {

	@Column(name = "file_name", nullable = false, unique = true)
	private String fileName;

	@Column(name = "content_type")
	private String contentType;

	@Column(name = "data") @Lob
	private byte[] data;

	@Column(name = "modified_date")
	private Date modifiedDate;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "site_id", nullable = false)
	private Site site;

	public long getFileSize() {
		return data == null ? 0 : data.length;
	}

	public String getReadableFileSize() {
		return FileUtils.byteCountToDisplaySize(getFileSize());
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
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

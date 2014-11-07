package be.webfactor.sitecubes.faces.bean.admin;

import be.webfactor.sitecubes.domain.File;
import be.webfactor.sitecubes.domain.Site;
import be.webfactor.sitecubes.faces.bean.SiteContextBean;
import be.webfactor.sitecubes.faces.helper.FacesUtil;
import be.webfactor.sitecubes.service.FileService;
import org.apache.commons.lang3.SerializationUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Named @Scope("view")
public class FileBean implements Serializable {

	@Inject private FileService fileService;
	@Inject private SiteContextBean siteContextBean;
	@Inject private FacesUtil facesUtil;

	private Site site;
	private List<File> files;
	private File file;
	private boolean upload;

	@PostConstruct
	public void initData() {
		upload = false;
		file = null;
		site = siteContextBean.getSite();
		files = fileService.getFiles(site);
	}

	public void initUpload() {
		upload = true;
	}

	public void onRowSelect(SelectEvent event) {
		file = SerializationUtils.clone((File) event.getObject());
	}

	public boolean isUpload() {
		return upload;
	}

	public void uploadNewFile(FileUploadEvent event) {
		UploadedFile uploadedFile = event.getFile();
		File file = new File();
		file.setFileName(uploadedFile.getFileName());
		file.setSite(site);
		saveUploadedFileData(file, uploadedFile);
	}

	public void uploadExistingFile(FileUploadEvent event) {
		UploadedFile uploadedFile = event.getFile();
		file = saveUploadedFileData(file, uploadedFile);
	}

	private File saveUploadedFileData(File file, UploadedFile uploadedFile) {
		file.setData(uploadedFile.getContents());
		file.setContentType(uploadedFile.getContentType());
		file.setModifiedDate(new Date());
		return fileService.save(file);
	}

	public void save() {
		fileService.save(file);
		facesUtil.info("file-saved-successfully");
	}

	public void delete() {
		fileService.delete(file);
		initData();
		facesUtil.info("file-deleted-successfully");
	}

	public void cancel() {
		initData();
	}

	public List<File> getFiles() {
		return files;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

}

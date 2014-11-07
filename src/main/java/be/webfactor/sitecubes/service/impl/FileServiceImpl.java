package be.webfactor.sitecubes.service.impl;

import be.webfactor.sitecubes.domain.File;
import be.webfactor.sitecubes.domain.Site;
import be.webfactor.sitecubes.repository.FileRepository;
import be.webfactor.sitecubes.service.FileService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named @Transactional(readOnly = true)
public class FileServiceImpl implements FileService {

	@Inject private FileRepository repository;

	public List<File> getFiles(Site site) {
		return repository.getSiteFiles(site);
	}

	@Transactional @Secured("ROLE_ADMIN")
	public File save(File file) {
		return repository.save(file);
	}

	@Transactional @Secured("ROLE_ADMIN")
	public void delete(File file) {
		repository.delete(file);
	}

}

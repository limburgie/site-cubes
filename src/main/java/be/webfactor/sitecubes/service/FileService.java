package be.webfactor.sitecubes.service;

import be.webfactor.sitecubes.domain.File;
import be.webfactor.sitecubes.domain.Site;

import java.util.List;

public interface FileService {

	List<File> getFiles(Site site);

	File save(File file);

	void delete(File file);

}

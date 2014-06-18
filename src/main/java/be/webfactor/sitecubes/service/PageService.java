package be.webfactor.sitecubes.service;

import be.webfactor.sitecubes.domain.Page;

import java.util.List;

public interface PageService {

	List<Page> getPages();

	Page save(Page page);

	void delete(Page page);

}

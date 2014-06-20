package be.webfactor.sitecubes.service;

import be.webfactor.sitecubes.domain.ContentLocation;
import be.webfactor.sitecubes.domain.Page;

import java.util.List;

public interface ContentLocationService {

	List<ContentLocation> getLocationsOnPage(Page page);

}

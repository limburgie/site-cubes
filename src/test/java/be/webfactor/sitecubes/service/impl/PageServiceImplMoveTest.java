package be.webfactor.sitecubes.service.impl;

import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.service.PageService;
import org.junit.After;
import org.junit.Before;

import javax.inject.Inject;

public class PageServiceImplMoveTest extends ServiceTestCase {

	@Inject private PageService pageService;

	@Before
	public void setup() {

	}

	@After
	public void cleanup() {
		for (Page page : pageService.getRootPages()) {
			pageService.delete(page);
		}
	}

}

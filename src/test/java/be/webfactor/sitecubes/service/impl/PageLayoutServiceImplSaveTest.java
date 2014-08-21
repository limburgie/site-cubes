package be.webfactor.sitecubes.service.impl;

import be.webfactor.sitecubes.domain.PageLayout;
import be.webfactor.sitecubes.service.PageLayoutService;
import be.webfactor.sitecubes.service.exception.InvalidPageLayoutStructureException;
import be.webfactor.sitecubes.service.test.ServiceTestCase;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

public class PageLayoutServiceImplSaveTest extends ServiceTestCase {

	@Inject private PageLayoutService pageLayoutService;

	private PageLayout pageLayout;

	@Before
	public void setup() {
		pageLayout = new PageLayout();
		pageLayout.setName("Some name");
	}

	@Test(expected = InvalidPageLayoutStructureException.class)
	public void ifTemplateIsInvalidThenThrowInvalidPageLayoutStructureException() {
		pageLayout.setStructure("#if($test) asdasd");

		pageLayoutService.save(pageLayout);
	}

}

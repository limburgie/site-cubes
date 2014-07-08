package be.webfactor.sitecubes.service.impl;

import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.service.test.PageServiceTestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PageServiceImplGetPagesTest extends PageServiceTestCase {

	@Before
	public void setup() {
		createPage("Home", "home");
		createPage("News", "news");
		createPage("Contact", "contact", "home");
	}

	@Test
	public void getPagesForParentNullReturnsRootPages() {
		List<Page> pages = pageService.getPages(null);

		assertEquals(2, pages.size());
		assertTrue(pages.contains(getPage("home")));
		assertTrue(pages.contains(getPage("news")));
	}

	@Test
	public void getPagesForExistingParentReturnsChildren() {
		List<Page> pages = pageService.getPages(getPage("home"));

		assertEquals(1, pages.size());
		assertTrue(pages.contains(getPage("contact")));
	}

}

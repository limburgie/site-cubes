package be.webfactor.sitecubes.service.impl;

import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.service.PageService;
import be.webfactor.sitecubes.service.exception.DuplicateFriendlyUrlException;
import be.webfactor.sitecubes.service.exception.InvalidFriendlyUrlException;
import be.webfactor.sitecubes.service.exception.InvalidPageNameException;
import be.webfactor.sitecubes.service.test.PageServiceTestCase;
import org.junit.Test;

import javax.inject.Inject;

import static org.junit.Assert.*;

public class PageServiceImplSaveTest extends PageServiceTestCase {

	@Inject private PageService pageService;

	@Test
	public void pageCanBeCreated() {
		createPage("Home", "home");
		assertEquals(1, pageService.getRootPages().size());
	}

	@Test
	public void pageCanBeUpdated() {
		createPage("Home", "home");
		Page persisted = getPage("home");
		persisted.setName("Test");
		pageService.save(persisted);

		assertEquals("Test", getPage("home").getName());
	}

	@Test
	public void firstCreatedPageHasZeroPosition() {
		createPage("Home", "home");

		assertEquals(0, getPage("home").getPosition());
	}

	@Test
	public void pageGetsLastPositionWithinParent() {
		createPage("Home", "home");
		createPage("News", "news", "home");

		createPage("Contact", "contact", "home");

		assertEquals(1, getPosition("contact"));
	}

	@Test(expected = DuplicateFriendlyUrlException.class)
	public void pageWithExistingFriendlyUrlThrowsDuplicateFriendlyUrlException() {
		createPage("Home", "home");
		createPage("Blabla", "home");
	}

	@Test(expected = InvalidPageNameException.class)
	public void pageWithEmptyNameThrowsInvalidPageNameException() {
		createPage(null, "home");
	}

	@Test(expected = InvalidFriendlyUrlException.class)
	public void pageWithEmptyFriendlyUrlThrowsInvalidFriendlyUrlException() {
		createPage("Home", null);
	}

	@Test(expected = InvalidFriendlyUrlException.class)
	public void pageWithInvalidFriendlyUrlThrowsInvalidFriendlyUrlException() {
		createPage("Home", "/%asd");
	}

}

package be.webfactor.sitecubes.service.impl;

import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.service.PageService;
import be.webfactor.sitecubes.service.exception.DuplicateFriendlyUrlException;
import be.webfactor.sitecubes.service.exception.InvalidFriendlyUrlException;
import be.webfactor.sitecubes.service.exception.InvalidPageNameException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;

public class PageServiceImplSaveTest extends ServiceTestCase {

	@Inject private PageService pageService;

	private Page home;
	private Page news;

	@Before
	public void setup() {
		home = new Page();
		home.setName("Home");
		home.setFriendlyUrl("home");

		pageService.save(home);
	}

	@Test
	public void pageCanBeCreated() {
		assertEquals(1, pageService.getRootPages().size());
	}

	@Test
	public void pageCanBeUpdated() {
		Page persisted = pageService.getRootPages().get(0);
		persisted.setName("Test");
		pageService.save(persisted);

		assertEquals("Test", pageService.getRootPages().get(0).getName());
	}

	@Test(expected = DuplicateFriendlyUrlException.class)
	public void pageWithExistingFriendlyUrlThrowsDuplicateFriendlyUrlException() {
		Page newPage = new Page();
		newPage.setName("Blabla");
		newPage.setFriendlyUrl("home");

		pageService.save(newPage);
	}

	@Test(expected = InvalidPageNameException.class)
	public void pageWithEmptyNameThrowsInvalidPageNameException() {
		Page page = new Page();
		page.setFriendlyUrl("home");

		pageService.save(page);
	}

	@Test(expected = InvalidFriendlyUrlException.class)
	public void pageWithEmptyFriendlyUrlThrowsInvalidFriendlyUrlException() {
		Page page = new Page();
		page.setName("Home");

		pageService.save(page);
	}

	@Test(expected = InvalidFriendlyUrlException.class)
	public void pageWithInvalidFriendlyUrlThrowsInvalidFriendlyUrlException() {
		Page page = new Page();
		page.setName("Home");
		page.setFriendlyUrl("/%asd");

		pageService.save(page);
	}

	@After
	public void tearDown() {
		for (Page page : pageService.getRootPages()) {
			pageService.delete(page);
		}
	}

}

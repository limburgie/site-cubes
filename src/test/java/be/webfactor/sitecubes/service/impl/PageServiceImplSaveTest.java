package be.webfactor.sitecubes.service.impl;

import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.service.PageService;
import be.webfactor.sitecubes.service.exception.DuplicateFriendlyUrlException;
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
		assertEquals(1, pageService.getPages().size());
	}

	@Test
	public void pageCanBeUpdated() {
		Page persisted = pageService.getPages().get(0);
		persisted.setName("Test");
		pageService.save(persisted);

		assertEquals("Test", pageService.getPages().get(0).getName());
	}

	@Test(expected = DuplicateFriendlyUrlException.class)
	public void pageWithExistingFriendlyUrlThrowsDuplicateFriendlyUrlException() {
		Page newPage = new Page();
		newPage.setName("Blabla");
		newPage.setFriendlyUrl("home");

		pageService.save(newPage);
	}

	@After
	public void tearDown() {
		pageService.delete(pageService.getPages().get(0));
	}

}

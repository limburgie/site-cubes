package be.webfactor.sitecubes.service.impl;

import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.service.PageService;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;
import java.util.List;

import static org.junit.Assert.*;

public class PageServiceImplDeleteTest extends ServiceTestCase {

	@Inject private PageService pageService;

	private Page home;
	private Page news;
	private Page contact;

	@Before
	public void setup() {
		home = new Page();
		home.setName("Home");
		home.setFriendlyUrl("home");

		news = new Page();
		news.setName("News");
		news.setFriendlyUrl("news");

		contact = new Page();
		contact.setName("Contact");
		contact.setFriendlyUrl("contact");

		home.addPage(contact);
	}

	@Test
	public void deletePageWithoutChildrenIsSuccessful() {
		pageService.save(news);

		pageService.delete(news);

		List<Page> pages = pageService.getPages();
		assertTrue(pages.isEmpty());
	}

	@Test
	public void deleteParentPageAlsoDeletesSubpages() {
		pageService.save(home);

		pageService.delete(home);

		List<Page> pages = pageService.getPages();
		assertTrue(pages.isEmpty());
	}

	@Test
	public void deleteChildPageIsSuccessful() {
		pageService.save(home);

		pageService.delete(contact);

		List<Page> pages = pageService.getPages();
		assertEquals(1, pages.size());
		assertEquals("Home", pages.get(0).getName());
		assertTrue(pages.get(0).getChildren().isEmpty());

		pageService.delete(pages.get(0));
	}

}

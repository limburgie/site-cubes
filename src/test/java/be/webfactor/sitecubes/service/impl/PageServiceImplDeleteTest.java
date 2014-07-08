package be.webfactor.sitecubes.service.impl;

import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.service.test.PageServiceTestCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PageServiceImplDeleteTest extends PageServiceTestCase {

	@Before
	public void setup() {
		super.setup();
		createPage("Home", "home");
		createPage("News", "news");
		createPage("Contact", "contact", "home");
	}

	@Test
	public void deletePageWithoutChildrenIsSuccessful() {
		delete("news");

		assertNull(getPage("news"));
	}

	@Test
	public void deleteParentPageAlsoDeletesSubpages() {
		delete("home");

		Page home = getPage("home");
		Page contact = getPage("contact");

		assertNull(home);
		assertNull(contact);
	}

	@Test
	public void deleteChildPageIsSuccessful() {
		delete("contact");

		assertNull(getPage("contact"));
		assertTrue(getPage("home").getChildren().isEmpty());
	}

	@Test
	public void deletePageMovesOtherPagesWithSameParentUp() {
		delete("home");

		assertEquals(0, getPosition("news"));
	}

	private void delete(String friendlyUrl) {
		pageService.delete(getPage(friendlyUrl));
	}

}

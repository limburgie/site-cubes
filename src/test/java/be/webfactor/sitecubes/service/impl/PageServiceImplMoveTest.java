package be.webfactor.sitecubes.service.impl;

import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.service.PageService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;

import static org.junit.Assert.assertEquals;

public class PageServiceImplMoveTest extends ServiceTestCase {

	@Inject private PageService pageService;

	private Page root1;
	private Page root2;
	private Page child1;
	private Page child2;
	private Page child22;

	@Before
	public void setup() {
		createPage("Root 1", "root-1");
		createPage("Child 11", "child-11", "root-1");
		createPage("Child 12", "child-12", "root-1");
		createPage("Root 2", "root-2");
		createPage("Child 21", "child-21", "root-2");
		createPage("Child 22", "child-22", "root-2");
	}

	@Test
	public void targetPageNowContainsChild() {
		move("child-21", "root-1", 0);

		Page firstRoot = pageService.getPageByFriendlyUrl("root-1");

		assertEquals(3, firstRoot.getChildren().size());
		assertEquals("child-21", firstRoot.getChildren().get(0).getFriendlyUrl());
	}

	@Test
	public void positionIsSameAsGivenPosition() {
		move("child-21", "root-1", 1);

		Page child21 = pageService.getPageByFriendlyUrl("child-21");
		assertEquals(1, child21.getPosition());
	}

	@Test
	public void positionOfUnderlyingChildrenIsIncremented() {
		move("child-21", "root-1", 0);

		Page child11 = pageService.getPageByFriendlyUrl("child-11");
		assertEquals(1, child11.getPosition());
		Page child12 = pageService.getPageByFriendlyUrl("child-12");
		assertEquals(2, child12.getPosition());
	}

	@Test
	public void otherChildrenInOldRootPageAreMovedUp() {
		move("child-21", "root-1", 0);

		Page child22 = pageService.getPageByFriendlyUrl("child-22");
		assertEquals(0, child22.getPosition());
	}

	@After
	public void cleanup() {
		for (Page page : pageService.getRootPages()) {
			pageService.delete(page);
		}
	}

	private void move(String fromFriendlyUrl, String toParentFriendlyUrl, int position) {
		Page from = pageService.getPageByFriendlyUrl(fromFriendlyUrl);
		Page to = pageService.getPageByFriendlyUrl(toParentFriendlyUrl);
		pageService.move(from, to, position);
	}

	private void createPage(String name, String friendlyUrl) {
		Page page = doCreatePage(name, friendlyUrl);
		pageService.save(page);
	}

	private void createPage(String name, String friendlyUrl, String parentFriendlyUrl) {
		Page page = doCreatePage(name, friendlyUrl);
		page.setParent(pageService.getPageByFriendlyUrl(parentFriendlyUrl));
		pageService.save(page);
	}

	private Page doCreatePage(String name, String friendlyUrl) {
		Page page = new Page();
		page.setName(name);
		page.setFriendlyUrl(friendlyUrl);
		return page;
	}

}

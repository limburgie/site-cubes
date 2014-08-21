package be.webfactor.sitecubes.service.impl;

import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.service.test.PageServiceTestCase;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PageServiceImplMoveTest extends PageServiceTestCase {

	@Before
	public void setup() {
		super.setup();

		createPage("Root 1", "root-1");
		createPage("Child 11", "child-11", "root-1");
		createPage("Child 12", "child-12", "root-1");
		createPage("Child 13", "child-13", "root-1");
		createPage("Root 2", "root-2");
		createPage("Child 21", "child-21", "root-2");
		createPage("Child 22", "child-22", "root-2");
		createPage("Root 3", "root-3");
		createPage("Root 4", "root-4");
	}

	@Test
	public void targetPageNowContainsChild() {
		move("child-21", "root-1", 0);

		Page firstRoot = getPage("root-1");

		assertEquals(4, firstRoot.getChildren().size());
		assertEquals("child-21", firstRoot.getChildren().get(0).getFriendlyUrl());
	}

	@Test
	public void positionIsSameAsGivenPosition() {
		move("child-21", "root-1", 1);

		assertEquals(1, getPosition("child-21"));
	}

	@Test
	public void positionOfUnderlyingChildrenIsIncremented() {
		move("child-21", "root-1", 0);

		assertEquals(1, getPosition("child-11"));
		assertEquals(2, getPosition("child-12"));
	}

	@Test
	public void otherChildrenInOldRootPageAreMovedUp() {
		move("child-21", "root-1", 0);

		assertEquals(0, getPosition("child-22"));
	}

	@Test
	public void movingChildToRootMovesAllRootsDown() {
		move("child-21", Page.ROOT.getFriendlyUrl(), 0);

		assertEquals(0, getPosition("child-21"));
		assertEquals(1, getPosition("root-1"));
		assertEquals(2, getPosition("root-2"));
	}

	@Test
	public void movingRootUnderExistingRootMovesAllUnderlyingRootsUp() {
		move("root-2", "root-1", 0);

		assertEquals(1, getPosition("root-3"));
		assertEquals(2, getPosition("root-4"));
	}

	@Test
	public void movingLastRootInMiddleOfExistingRootsChildren() {
		move("root-4", "root-2", 1);

		assertEquals(2, getPosition("child-22"));
	}

	@Test
	public void switchChildrenWithinRootCausesNoProblems() {
		move("child-11", "root-1", 1);

		assertEquals(0, getPosition("child-12"));
		assertEquals(1, getPosition("child-11"));
		assertEquals(2, getPosition("child-13"));
	}

	@Test
	public void quickMoveBetweenTwoPagesGivesNoError() {
		move("root-2", Page.ROOT.getFriendlyUrl(), 2);
		move("root-3", Page.ROOT.getFriendlyUrl(), 2);
	}

	private void move(String fromFriendlyUrl, String toParentFriendlyUrl, int position) {
		Page from = pageService.getPageByFriendlyUrl(fromFriendlyUrl);
		Page to = null;
		if (toParentFriendlyUrl != null) {
			to = pageService.getPageByFriendlyUrl(toParentFriendlyUrl);
		}
		pageService.move(from, to, position);
	}

}

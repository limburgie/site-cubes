package be.webfactor.sitecubes.faces.helper;

import be.webfactor.sitecubes.domain.Page;
import org.junit.Before;
import org.junit.Test;
import org.primefaces.model.TreeNode;

import static org.junit.Assert.*;

public class PageTreeBuilderBuildTreeTest {

	private Page root;
	private Page parent1;
	private Page parent2;
	private Page child;

	private PageTreeBuilder pageTreeBuilder;

	@Before
	public void setup() {
		root = new Page();
		root.setId(9L);
		root.setName("Root");
		root.setFriendlyUrl("/");

		parent1 = new Page();
		parent1.setId(1L);
		parent1.setName("Home");
		parent1.setFriendlyUrl("/home");
		parent1.setParent(root);
		root.getChildren().add(parent1);

		parent2 = new Page();
		parent2.setId(2L);
		parent2.setName("News");
		parent2.setFriendlyUrl("/news");
		parent2.setParent(root);
		root.getChildren().add(parent2);

		child = new Page();
		child.setId(3L);
		child.setName("Contact");
		child.setFriendlyUrl("/contact");
		child.setParent(parent1);
		parent1.getChildren().add(child);

		pageTreeBuilder = new PageTreeBuilder();
	}

	@Test
	public void rootIsEmptyAndHasParentNull() {
		TreeNode rootNode = pageTreeBuilder.buildTree(root, null);

		Page rootPage = (Page) rootNode.getData();
		assertEquals("Root", rootPage.getName());
		assertEquals("/", rootPage.getFriendlyUrl());
		assertNull(root.getParent());
	}

	@Test
	public void rootHasTwoNodesWhichHaveRootAsParent() {
		TreeNode rootNode = pageTreeBuilder.buildTree(root, null);

		assertEquals(2, rootNode.getChildCount());

		TreeNode node1 = rootNode.getChildren().get(0);
		assertEquals(rootNode, node1.getParent());
		assertEquals(parent1, node1.getData());

		TreeNode node2 = rootNode.getChildren().get(1);
		assertEquals(rootNode, node2.getParent());
		assertEquals(parent2, node2.getData());
	}

	@Test
	public void firstNodeHasOneChild() {
		TreeNode rootNode = pageTreeBuilder.buildTree(root, null);
		TreeNode parentNode = rootNode.getChildren().get(0);

		assertEquals(1, parentNode.getChildCount());

		TreeNode childNode = parentNode.getChildren().get(0);
		assertEquals(parentNode, childNode.getParent());
		assertEquals(child, childNode.getData());
	}

	@Test
	public void allNodesAreExpanded() {
		TreeNode rootNode = pageTreeBuilder.buildTree(root, null);

		assertTrue(rootNode.isExpanded());
		assertTrue(rootNode.getChildren().get(0).isExpanded());
		assertTrue(rootNode.getChildren().get(1).isExpanded());
		assertTrue(rootNode.getChildren().get(0).getChildren().get(0).isExpanded());
	}

}

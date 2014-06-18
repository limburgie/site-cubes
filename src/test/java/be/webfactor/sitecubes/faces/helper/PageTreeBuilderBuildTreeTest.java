package be.webfactor.sitecubes.faces.helper;

import be.webfactor.sitecubes.domain.Page;
import org.junit.Before;
import org.junit.Test;
import org.primefaces.model.TreeNode;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class PageTreeBuilderBuildTreeTest {

	private Page parent1;
	private Page parent2;
	private Page child;
	private List<Page> pages;

	private PageTreeBuilder pageTreeBuilder;

	@Before
	public void setup() {
		parent1 = new Page();
		parent1.setId(1L);
		parent1.setName("Home");
		parent1.setFriendlyUrl("/home");

		parent2 = new Page();
		parent2.setId(2L);
		parent2.setName("News");
		parent2.setFriendlyUrl("/news");

		child = new Page();
		child.setId(3L);
		child.setName("Contact");
		child.setFriendlyUrl("/contact");
		child.setParent(parent1);
		parent1.getChildren().add(child);

		pages = Arrays.asList(parent1, parent2);

		pageTreeBuilder = new PageTreeBuilder();
	}

	@Test
	public void rootIsEmptyAndHasParentNull() {
		TreeNode root = pageTreeBuilder.buildTree(pages);

		assertEquals("Root", root.getData());
		assertNull(root.getParent());
	}

	@Test
	public void rootHasTwoNodesWhichHaveRootAsParent() {
		TreeNode root = pageTreeBuilder.buildTree(pages);

		assertEquals(2, root.getChildCount());

		TreeNode node1 = root.getChildren().get(0);
		assertEquals(root, node1.getParent());
		assertEquals(parent1, node1.getData());

		TreeNode node2 = root.getChildren().get(1);
		assertEquals(root, node2.getParent());
		assertEquals(parent2, node2.getData());
	}

	@Test
	public void firstNodeHasOneChild() {
		TreeNode root = pageTreeBuilder.buildTree(pages);
		TreeNode parentNode = root.getChildren().get(0);

		assertEquals(1, parentNode.getChildCount());

		TreeNode childNode = parentNode.getChildren().get(0);
		assertEquals(parentNode, childNode.getParent());
		assertEquals(child, childNode.getData());
	}

	@Test
	public void allNodesAreExpanded() {
		TreeNode root = pageTreeBuilder.buildTree(pages);

		assertTrue(root.isExpanded());
		assertTrue(root.getChildren().get(0).isExpanded());
		assertTrue(root.getChildren().get(1).isExpanded());
		assertTrue(root.getChildren().get(0).getChildren().get(0).isExpanded());
	}

}

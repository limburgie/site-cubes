package be.webfactor.sitecubes.faces.helper;

import be.webfactor.sitecubes.domain.Page;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import javax.inject.Named;
import java.util.List;

@Named
public class PageTreeBuilder {

	public TreeNode buildTree(List<Page> pages) {
		TreeNode root = new DefaultTreeNode("Root", null);
		buildSubtree(pages, root);
		return root;
	}

	private void buildSubtree(List<Page> pages, TreeNode parent) {
		parent.setExpanded(true);
		for (Page page : pages) {
			TreeNode child = new DefaultTreeNode(page, parent);
			buildSubtree(page.getChildren(), child);
		}
	}

}

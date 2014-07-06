package be.webfactor.sitecubes.faces.helper;

import be.webfactor.sitecubes.domain.Page;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import javax.inject.Named;
import java.util.List;

@Named
public class PageTreeBuilder {

	public static final String ROOT_NAME = "Root";

	public TreeNode buildTree(List<Page> pages, Page active) {
		TreeNode root = new DefaultTreeNode(ROOT_NAME, null);
		buildSubtree(pages, root, active);
		return root;
	}

	private void buildSubtree(List<Page> pages, TreeNode parent, Page active) {
		parent.setExpanded(true);
		for (Page page : pages) {
			TreeNode child = new DefaultTreeNode(page, parent);
			if (page.equals(active)) {
				child.setSelected(true);
			}
			buildSubtree(page.getChildren(), child, active);
		}
	}

}

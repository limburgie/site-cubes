package be.webfactor.sitecubes.faces.helper;

import be.webfactor.sitecubes.domain.Page;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import javax.inject.Named;
import java.util.List;

@Named
public class PageTreeBuilder {

	public TreeNode buildTree(Page root, Page active) {
		TreeNode rootNode = new DefaultTreeNode(root, null);
		buildSubtree(root.getChildren(), rootNode, active);
		return rootNode;
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

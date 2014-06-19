package be.webfactor.sitecubes.faces.bean;

import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.faces.helper.FacesUtil;
import be.webfactor.sitecubes.faces.helper.PageTreeBuilder;
import be.webfactor.sitecubes.service.FriendlyUrlHandler;
import be.webfactor.sitecubes.service.PageService;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@Scope("view")
public class PagesBean implements Serializable {

	@Inject private PageService pageService;
	@Inject private PageTreeBuilder pageTreeBuilder;
	@Inject private FacesUtil facesUtil;
	@Inject private FriendlyUrlHandler friendlyUrlHandler;

	private TreeNode root;
	private Page page;

	@PostConstruct
	public void initTree() {
		List<Page> rootPages = pageService.getPages();
		root = pageTreeBuilder.buildTree(rootPages, page);
	}

	public void initRootPage() {
		page = new Page();
	}

	public void initChildPage() {
		Page parent = page;
		page = new Page();
		page.setParent(parent);
	}

	public void onNodeSelect(NodeSelectEvent event) {
		long selectedPageId = ((Page) event.getTreeNode().getData()).getId();
		page = pageService.getPageById(selectedPageId);
	}

	public void save() {
		pageService.save(page);
		initTree();
		facesUtil.info("page-saved-successfully");
	}

	public void delete() {
		pageService.delete(page);
		page = null;
		initTree();
		facesUtil.info("page-deleted-successfully");
	}

	public void cancel() {
		page = null;
		initTree();
	}

	public void updateFriendlyUrl() {
		if (page.getId() == null) {
			page.setFriendlyUrl(friendlyUrlHandler.normalize(page.getName()));
		}
	}

	public TreeNode getRoot() {
		return root;
	}

	public Page getPage() {
		return page;
	}

}

package be.webfactor.sitecubes.faces.bean.admin;

import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.domain.PageLayout;
import be.webfactor.sitecubes.faces.bean.SiteContextBean;
import be.webfactor.sitecubes.faces.helper.FacesUtil;
import be.webfactor.sitecubes.faces.helper.PageTreeBuilder;
import be.webfactor.sitecubes.service.FriendlyUrlHandler;
import be.webfactor.sitecubes.service.PageLayoutService;
import be.webfactor.sitecubes.service.PageService;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.TreeDragDropEvent;
import org.primefaces.model.TreeNode;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@Scope("view")
public class PagesBean implements Serializable {

	@Inject private PageService pageService;
	@Inject private PageLayoutService pageLayoutService;
	@Inject private PageTreeBuilder pageTreeBuilder;
	@Inject private FacesUtil facesUtil;
	@Inject private FriendlyUrlHandler friendlyUrlHandler;
	@Inject private SiteContextBean siteContextBean;

	private PageLayout defaultLayout;
	private TreeNode rootNode;
	private Page page;
	private Page rootPage;

	@PostConstruct
	public void init() {
		initTree();
		initLayouts();
	}

	private void initLayouts() {
		defaultLayout = pageLayoutService.getDefaultLayout();
	}

	public void initTree() {
		rootPage = pageService.getRoot(siteContextBean.getActiveSite());
		rootNode = pageTreeBuilder.buildTree(rootPage, page);
	}

	public void initRootPage() {
		createPage();
		page.setParent(rootPage);
	}

	public void initChildPage() {
		Page parent = page;
		createPage();
		page.setParent(parent);
	}

	private void createPage() {
		page = new Page();
		page.setLayout(defaultLayout);
	}

	public void onNodeSelect(NodeSelectEvent event) {
		long selectedPageId = ((Page) event.getTreeNode().getData()).getId();
		page = pageService.getPageById(selectedPageId);
	}

	public void onDragDrop(TreeDragDropEvent event) {
		Page movedPage = (Page) event.getDragNode().getData();
		Page targetParent = (Page) event.getDropNode().getData();
		int position = event.getDropIndex();
		pageService.move(movedPage, targetParent, position);
		initTree();
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
		return rootNode;
	}

	public Page getPage() {
		return page;
	}

}

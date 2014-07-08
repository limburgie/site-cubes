package be.webfactor.sitecubes.service.test;

import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.service.PageService;
import org.junit.After;

import javax.inject.Inject;

public abstract class PageServiceTestCase extends ServiceTestCase {

	@Inject protected PageService pageService;

	protected void createPage(String name, String friendlyUrl) {
		Page page = doCreatePage(name, friendlyUrl);
		pageService.save(page);
	}

	protected void createPage(String name, String friendlyUrl, String parentFriendlyUrl) {
		Page page = doCreatePage(name, friendlyUrl);
		page.setParent(pageService.getPageByFriendlyUrl(parentFriendlyUrl));
		pageService.save(page);
	}

	protected int getPosition(String friendlyUrl) {
		return getPage(friendlyUrl).getPosition();
	}

	protected Page getPage(String friendlyUrl){
		return pageService.getPageByFriendlyUrl(friendlyUrl);
	}

	private Page doCreatePage(String name, String friendlyUrl) {
		Page page = new Page();
		page.setName(name);
		page.setFriendlyUrl(friendlyUrl);
		return page;
	}

	@After
	public void cleanup() {
		for (Page page : pageService.getRootPages()) {
			pageService.delete(page);
		}
		Page root = pageService.getRoot();
		if (root != null) {
			pageService.delete(root);
		}
	}

}

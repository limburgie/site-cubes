package be.webfactor.sitecubes.service.test;

import be.webfactor.sitecubes.domain.ContentItem;
import be.webfactor.sitecubes.domain.ContentLocation;
import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.repository.PageRepository;
import be.webfactor.sitecubes.service.ContentItemService;
import be.webfactor.sitecubes.service.ContentLocationService;
import be.webfactor.sitecubes.service.PageService;
import org.junit.After;
import org.junit.Before;

import javax.inject.Inject;

public abstract class ContentLocationServiceTestCase extends ServiceTestCase {

	@Inject protected ContentLocationService contentLocationService;

	@Inject private PageService pageService;
	@Inject private ContentItemService contentItemService;
	@Inject private PageRepository pageRepository;

	private Page page;
	private ContentItem item;

	@Before
	public void setup() {
		pageRepository.save(Page.ROOT);

		page = createPage("Home", "home");
		item = createItem("Test");
	}

	protected ContentLocation createLocation(String columnId, int position) {
		ContentLocation location = new ContentLocation();
		location.setPage(page);
		location.setItem(item);
		location.setColumnId(columnId);
		location.setPosition(position);

		return contentLocationService.save(location);
	}

	protected void deleteLocation(String columnId, int position) {
		contentLocationService.delete(getLocation(columnId, position));
	}

	protected ContentLocation getLocation(String columnId, int position) {
		for(ContentLocation location : contentLocationService.getLocationsOnPage(page)) {
			if(location.getColumnId().equals(columnId) && location.getPosition() == position) {
				return location;
			}
		}
		return null;
	}

	private ContentItem createItem(String title) {
		ContentItem item = new ContentItem();
		item.setTitle(title);
		return contentItemService.save(item);
	}

	private Page createPage(String name, String friendlyUrl) {
		Page page = doCreatePage(name, friendlyUrl);
		page.setParent(pageService.getRoot());
		return pageService.save(page);
	}

	private Page doCreatePage(String name, String friendlyUrl) {
		Page page = new Page();
		page.setName(name);
		page.setFriendlyUrl(friendlyUrl);
		return page;
	}

	@After
	public void cleanup() {
		for (ContentItem item : contentItemService.getItems()) {
			contentItemService.delete(item);
		}
		pageService.delete(pageService.getRoot());
	}

}

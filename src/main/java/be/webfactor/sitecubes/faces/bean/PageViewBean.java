package be.webfactor.sitecubes.faces.bean;

import be.webfactor.sitecubes.domain.ContentItem;
import be.webfactor.sitecubes.domain.ContentLocation;
import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.faces.helper.FacesUtil;
import be.webfactor.sitecubes.faces.renderer.CustomDashboardModel;
import be.webfactor.sitecubes.service.ContentItemService;
import be.webfactor.sitecubes.service.ContentLocationService;
import be.webfactor.sitecubes.service.PageService;
import org.primefaces.component.commandlink.CommandLink;
import org.primefaces.component.dashboard.Dashboard;
import org.primefaces.component.panel.Panel;
import org.primefaces.event.DashboardReorderEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DefaultDashboardColumn;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.component.html.HtmlPanelGroup;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Named
@Scope("view")
public class PageViewBean implements Serializable {

	private static final Pattern VAR_PATTERN = Pattern.compile("\\{(.*?)\\}");
	private static final String PANEL_PREFIX = "panel";

	@Inject private PageService pageService;
	@Inject private ContentLocationService contentLocationService;
	@Inject private ContentItemService contentItemService;
	@Inject private FacesUtil facesUtil;

	private Page page;
	private List<ContentLocation> locations;
	private List<String> columnIds;

	private Dashboard dashboardComponent;

	@PostConstruct
	public void init() {
		initPage();
		initDashboard();
		retrieveLocations();
		populateDashboard();
	}

	private void initPage() {
		String friendlyUrl = facesUtil.getParam("u");
		page = pageService.getPageByFriendlyUrl(friendlyUrl);
	}

	private void initDashboard() {
		columnIds = new ArrayList<String>();
		Matcher matcher = VAR_PATTERN.matcher(page.getLayout().getStructure());
		while (matcher.find()) {
			columnIds.add(matcher.group(1));
		}

		dashboardComponent = facesUtil.createPrimeComponent(Dashboard.class);
	}

	private void populateDashboard() {
		addWidgetsToDashboard();

		for (ContentLocation location : locations) {
			Panel panel = createPanelForLocation(location);
			dashboardComponent.getChildren().add(panel);
		}
	}

	private void retrieveLocations() {
		locations = contentLocationService.getLocationsOnPage(page);
	}

	private void addWidgetsToDashboard() {
		retrieveLocations();
		CustomDashboardModel dashboardModel = new CustomDashboardModel(page.getLayout().getStructure());
		for (String columnId : columnIds) {
			DashboardColumn column = new DefaultDashboardColumn();
			for (ContentLocation location : locations) {
				if (columnId.equals(location.getColumnId())) {
					column.addWidget(PANEL_PREFIX + location.getId());
				}
			}
			dashboardModel.addColumn(columnId, column);
		}
		dashboardComponent.setModel(dashboardModel);
	}

	private Panel createPanelForLocation(ContentLocation location) {
		Panel panel = facesUtil.createPrimeComponent(Panel.class);
		panel.setId(PANEL_PREFIX + location.getId());
		panel.setHeader(location.getItem().getTitle());
		panel.setStyleClass("portlet");
		panel.setWidgetVar(PANEL_PREFIX + location.getId());

		HtmlPanelGroup actionsGroup = new HtmlPanelGroup();
		CommandLink closeLink = facesUtil.createPrimeComponent(CommandLink.class);
		closeLink.setStyleClass("ui-panel-titlebar-icon panel-close-icon");
		closeLink.setActionExpression(facesUtil.createMethodExpression("#{pageViewBean.remove("+location.getId()+")}", null));
		closeLink.setUpdate(":dashboard");
		HtmlOutputText closeIcon = new HtmlOutputText();
		closeIcon.setStyleClass("fa fa-times");
		closeLink.getChildren().add(closeIcon);
		actionsGroup.getChildren().add(closeLink);
		panel.getFacets().put("actions", actionsGroup);

		HtmlOutputText panelContent = new HtmlOutputText();
		panelContent.setEscape(false);
		panelContent.setValue(location.getItem().getContent());
		panel.getChildren().add(panelContent);
		return panel;
	}

	public void remove(long locationId) {
		ContentLocation location = contentLocationService.getLocation(locationId);
		contentLocationService.delete(location);
		addWidgetsToDashboard();
	}

	public void reorder(DashboardReorderEvent event) {
		String widgetId = event.getWidgetId();
		long locationId = Long.valueOf(widgetId.replace(PANEL_PREFIX, ""));
		int position = event.getItemIndex();
		String toColumnId = columnIds.get(event.getColumnIndex());

		contentLocationService.moveLocation(locationId, toColumnId, position);
	}

	public void addContent() {
		// Todo: let user select content item
		List<ContentItem> items = contentItemService.getItems();
		if (items.isEmpty()) {
			return;
		}
		ContentLocation location = contentLocationService.addItemInFirstColumn(page, items.get(0));
		Panel panel = createPanelForLocation(location);
		dashboardComponent.getChildren().add(panel);
		addWidgetsToDashboard();
	}

	public Dashboard getDashboardComponent() {
		return dashboardComponent;
	}

	public void setDashboardComponent(Dashboard dashboardComponent) {
		this.dashboardComponent = dashboardComponent;
	}

	public Page getPage() {
		return page;
	}

}

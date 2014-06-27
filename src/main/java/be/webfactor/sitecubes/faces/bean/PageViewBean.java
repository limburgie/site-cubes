package be.webfactor.sitecubes.faces.bean;

import be.webfactor.sitecubes.domain.ContentLocation;
import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.faces.helper.FacesUtil;
import be.webfactor.sitecubes.faces.renderer.CustomDashboardModel;
import be.webfactor.sitecubes.service.ContentLocationService;
import be.webfactor.sitecubes.service.PageService;
import org.primefaces.component.dashboard.Dashboard;
import org.primefaces.component.panel.Panel;
import org.primefaces.event.DashboardReorderEvent;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DefaultDashboardColumn;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.component.html.HtmlOutputText;
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
	private static final String PANEL_PREFIX = "panel-";

	@Inject private PageService pageService;
	@Inject private ContentLocationService contentLocationService;
	@Inject private FacesUtil facesUtil;

	private Page page;
	private List<ContentLocation> locations;
	private List<String> columnIds;

	private Dashboard dashboardComponent;

	@PostConstruct
	public void init() {
		initPage();
		initDashboard();
	}

	private void initPage() {
		String friendlyUrl = facesUtil.getParam("u");
		page = pageService.getPageByFriendlyUrl(friendlyUrl);
		locations = contentLocationService.getLocationsOnPage(page);

		columnIds = new ArrayList<String>();
		Matcher matcher = VAR_PATTERN.matcher(page.getLayout().getStructure());
		while (matcher.find()) {
			columnIds.add(matcher.group(1));
		}
	}

	private void initDashboard() {
		dashboardComponent = (Dashboard) facesUtil.createComponent("org.primefaces.component.Dashboard", "org.primefaces.component.DashboardRenderer");
		String tpl = page.getLayout().getStructure();
		CustomDashboardModel dashboardModel = new CustomDashboardModel(tpl);

		for (String columnId : columnIds) {
			DashboardColumn column = new DefaultDashboardColumn();
			for (ContentLocation location : locations) {
				if (columnId.equals(location.getColumnId())) {
					column.addWidget(PANEL_PREFIX + location.getId());
				}
			}
			dashboardModel.addColumn(columnId, column);
		}

		for (ContentLocation location : locations) {
			Panel panel = (Panel) facesUtil.createComponent("org.primefaces.component.Panel", "org.primefaces.component.PanelRenderer");
			panel.setId(PANEL_PREFIX + location.getId());
			panel.setHeader(location.getItem().getTitle());

			HtmlOutputText panelContent = new HtmlOutputText();
			panelContent.setValue(location.getItem().getContent());
			panel.getChildren().add(panelContent);

			dashboardComponent.getChildren().add(panel);
		}

		dashboardComponent.setModel(dashboardModel);
	}

	public void reorder(DashboardReorderEvent event) {
		String widgetId = event.getWidgetId();
		long locationId = Long.valueOf(widgetId.replace(PANEL_PREFIX, ""));
		int position = event.getItemIndex();
		String toColumnId = columnIds.get(event.getColumnIndex());

		contentLocationService.moveLocation(locationId, toColumnId, position);
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

package be.webfactor.sitecubes.faces.bean;

import be.webfactor.sitecubes.domain.ContentLocation;
import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.faces.helper.FacesUtil;
import be.webfactor.sitecubes.faces.renderer.CustomDashboardColumn;
import be.webfactor.sitecubes.service.ContentLocationService;
import be.webfactor.sitecubes.service.PageService;
import org.primefaces.component.dashboard.Dashboard;
import org.primefaces.component.panel.Panel;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardModel;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.component.html.HtmlOutputText;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@Scope("view")
public class PageViewBean implements Serializable {

	public static final String PANEL_PREFIX = "panel-";
	@Inject private PageService pageService;
	@Inject private ContentLocationService contentLocationService;
	@Inject private FacesUtil facesUtil;

	private Page page;
	private DashboardModel dashboard;
	private List<ContentLocation> locations;

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
	}

	private void initDashboard() {
		dashboardComponent = (Dashboard) facesUtil.createComponent("org.primefaces.component.Dashboard", "org.primefaces.component.DashboardRenderer");
		dashboard = new DefaultDashboardModel();

		for (int i = 1; i <= 3; i++) {
			String columnId = String.valueOf(i);

			CustomDashboardColumn column = new CustomDashboardColumn();
			column.setRowIndex(1);
			column.setBootstrapClass("col-md-4");
			for (ContentLocation location : locations) {
				if (columnId.equals(location.getColumnId())) {
					column.addWidget(PANEL_PREFIX + location.getId());
				}
			}
			dashboard.addColumn(column);
		}

		for (int i = 4; i<= 5; i++) {
			String columnId = String.valueOf(i);

			CustomDashboardColumn column = new CustomDashboardColumn();
			column.setRowIndex(2);
			column.setBootstrapClass("col-md-6");
			for (ContentLocation location : locations) {
				if (columnId.equals(location.getColumnId())) {
					column.addWidget(PANEL_PREFIX + location.getId());
				}
			}
			dashboard.addColumn(column);
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

		dashboardComponent.setModel(dashboard);
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

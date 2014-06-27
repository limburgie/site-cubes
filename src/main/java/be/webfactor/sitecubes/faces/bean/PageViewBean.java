package be.webfactor.sitecubes.faces.bean;

import be.webfactor.sitecubes.domain.ContentLocation;
import be.webfactor.sitecubes.domain.Page;
import be.webfactor.sitecubes.faces.helper.FacesUtil;
import be.webfactor.sitecubes.faces.renderer.CustomDashboardModel;
import be.webfactor.sitecubes.service.ContentLocationService;
import be.webfactor.sitecubes.service.PageService;
import org.primefaces.component.dashboard.Dashboard;
import org.primefaces.component.panel.Panel;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DefaultDashboardColumn;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.faces.component.html.HtmlOutputText;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@Named
@Scope("view")
public class PageViewBean implements Serializable {

	public static final String PANEL_PREFIX = "panel-";
	@Inject private PageService pageService;
	@Inject private ContentLocationService contentLocationService;
	@Inject private FacesUtil facesUtil;

	private Page page;
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
		CustomDashboardModel dashboardModel = new CustomDashboardModel(page.getLayout().getStructure());

		List<String> columnIds = Arrays.asList("$1", "$2", "$3");

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

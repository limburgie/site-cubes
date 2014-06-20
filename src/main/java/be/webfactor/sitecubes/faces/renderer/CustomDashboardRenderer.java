package be.webfactor.sitecubes.faces.renderer;

import org.primefaces.component.dashboard.Dashboard;
import org.primefaces.component.dashboard.DashboardRenderer;
import org.primefaces.component.panel.Panel;
import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DashboardModel;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.IOException;
import java.util.*;

public class CustomDashboardRenderer extends DashboardRenderer {

	@Override
	protected void encodeMarkup(FacesContext fc, Dashboard dashboard) throws IOException {
		ResponseWriter writer = fc.getResponseWriter();
		String clientId = dashboard.getClientId(fc);

		writer.startElement("div", dashboard);
		writer.writeAttribute("id", clientId, "id");
		String styleClass = dashboard.getStyleClass() != null ? Dashboard.CONTAINER_CLASS + " " + dashboard.getStyleClass() : Dashboard.CONTAINER_CLASS;
		writer.writeAttribute("class", styleClass, "styleClass");
		if (dashboard.getStyle() != null)
			writer.writeAttribute("style", dashboard.getStyle(), "style");

		DashboardModel model = dashboard.getModel();
		if (model != null) {
			List<DashboardColumn> columns = model.getColumns();
			for (int rowIndex: getRowIds(columns)) {
				writer.startElement("div", null);
				writer.writeAttribute("class", "row", null);

				for (DashboardColumn column : columns) {
					CustomDashboardColumn cdc = (CustomDashboardColumn) column;
					if (cdc.getRowIndex() == rowIndex) {
						writer.startElement("div", null);
						writer.writeAttribute("class", cdc.getBootstrapClass() + " " + Dashboard.COLUMN_CLASS, null);

						for (String widgetId : column.getWidgets()) {
							Panel widget = findWidget(widgetId, dashboard);

							if (widget != null)
								renderChild(fc, widget);
						}

						writer.endElement("div");
					}
				}
				writer.endElement("div");
			}
		}

		writer.endElement("div");
	}

	private List<Integer> getRowIds(List<DashboardColumn> columns) {
		Set<Integer> rowIdSet = new HashSet<Integer>();
		for (DashboardColumn column: columns) {
			CustomDashboardColumn cdc = (CustomDashboardColumn) column;
			rowIdSet.add(cdc.getRowIndex());
		}
		List<Integer> result = new ArrayList<Integer>(rowIdSet);
		Collections.sort(result);
		return result;
	}

}

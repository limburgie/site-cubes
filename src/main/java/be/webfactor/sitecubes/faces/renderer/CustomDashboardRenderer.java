package be.webfactor.sitecubes.faces.renderer;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.primefaces.component.dashboard.Dashboard;
import org.primefaces.component.dashboard.DashboardRenderer;
import org.primefaces.component.panel.Panel;
import org.primefaces.model.DashboardColumn;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

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

		CustomDashboardModel model = (CustomDashboardModel) dashboard.getModel();
		if (model != null) {
			try {
				Document doc = getDocument(model.getTemplate());
				Element root = doc.getRootElement();
				writeElement(writer, root, fc, dashboard);
			} catch (DocumentException e) {
				e.printStackTrace();
			}

			/*
			List<DashboardColumn> columns = model.getColumns();
			for (int rowIndex : getRowIds(columns)) {
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
			*/
		}

		writer.endElement("div");
	}

	private void writeElement(ResponseWriter writer, Element element, FacesContext fc, Dashboard dashboard) throws IOException {
		String tagName = element.getName();
		writer.startElement(tagName, null);
		List attributes = element.attributes();
		for (int i = 0; i < attributes.size(); i++) {
			Attribute attribute = (Attribute) attributes.get(i);
			String attributeName = attribute.getName();
			String attributeValue = attribute.getValue();
			writer.writeAttribute(attributeName, attributeValue, null);
		}
		String value = element.getText();
		if (StringUtils.isNotBlank(value)) {
			if(value.startsWith("$")) {
				writeDashboardColumn(writer, value, fc, dashboard);
			} else {
				writer.write(value);
			}
		}
		List elements = element.elements();
		for (int i = 0; i < elements.size(); i++) {
			Element child = (Element) elements.get(i);
			writeElement(writer, child, fc, dashboard);
		}
		writer.endElement(tagName);
	}

	private void writeDashboardColumn(ResponseWriter writer, String columnId, FacesContext fc, Dashboard dashboard) throws IOException {
		DashboardColumn column = ((CustomDashboardModel) dashboard.getModel()).getColumn(columnId);
		writer.startElement("div", null);
		writer.writeAttribute("class", Dashboard.COLUMN_CLASS, null);
		for (String widgetId : column.getWidgets()) {
			Panel widget = findWidget(widgetId, dashboard);

			if (widget != null) {
				renderChild(fc, widget);
			}
		}
		writer.endElement("div");
	}

	private Document getDocument(String template) throws DocumentException {
		InputStream stream = new ByteArrayInputStream(template.getBytes(StandardCharsets.UTF_8));
		SAXReader reader = new SAXReader();
		return reader.read(stream);
	}

}

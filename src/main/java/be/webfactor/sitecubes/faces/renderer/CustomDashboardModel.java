package be.webfactor.sitecubes.faces.renderer;

import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

import java.util.*;

public class CustomDashboardModel extends DefaultDashboardModel {

	private Map<String, DashboardColumn> columns = new LinkedHashMap<String, DashboardColumn>();
	private String template;

	public CustomDashboardModel(String template) {
		this.template = template;
	}

	public String getTemplate() {
		return template;
	}

	public void addColumn(String columnId, DashboardColumn column) {
		columns.put(columnId, column);
	}

	public DashboardColumn getColumn(String columnId) {
		return columns.get(columnId);
	}

	public int getColumnCount() {
		return columns.size();
	}

	public void addColumn(DashboardColumn column) {
		throw new UnsupportedOperationException();
	}

	public DashboardColumn getColumn(int index) {
		return getColumns().get(index);
	}

	public List<DashboardColumn> getColumns() {
		return new ArrayList<DashboardColumn>(columns.values());
	}

	public void transferWidget(DashboardColumn fromColumn, DashboardColumn toColumn, String widgetId, int index) {
		super.transferWidget(fromColumn, toColumn, widgetId, index);
	}
}

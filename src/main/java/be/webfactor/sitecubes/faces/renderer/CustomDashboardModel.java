package be.webfactor.sitecubes.faces.renderer;

import org.primefaces.model.DashboardColumn;
import org.primefaces.model.DefaultDashboardModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomDashboardModel extends DefaultDashboardModel {

	private Map<String, DashboardColumn> columns = new HashMap<String, DashboardColumn>();
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
		throw new UnsupportedOperationException();
	}

	public List<DashboardColumn> getColumns() {
		return new ArrayList<DashboardColumn>(columns.values());
	}

}

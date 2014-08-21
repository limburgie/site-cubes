package be.webfactor.sitecubes.faces.renderer;

import org.primefaces.model.DefaultDashboardColumn;

public class CustomDashboardColumn extends DefaultDashboardColumn {

	private String columnId;

	public CustomDashboardColumn(String columnId) {
		this.columnId = columnId;
	}

	public String getTemplate() {
		return columnId;
	}

}

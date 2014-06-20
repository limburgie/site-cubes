package be.webfactor.sitecubes.faces.renderer;

import org.primefaces.model.DefaultDashboardColumn;

public class CustomDashboardColumn extends DefaultDashboardColumn {

	private int rowIndex = 1;
	private String bootstrapClass = "";

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public String getBootstrapClass() {
		return bootstrapClass;
	}

	public void setBootstrapClass(String bootstrapClass) {
		this.bootstrapClass = bootstrapClass;
	}

}

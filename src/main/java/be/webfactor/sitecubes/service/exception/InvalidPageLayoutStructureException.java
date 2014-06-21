package be.webfactor.sitecubes.service.exception;

import be.webfactor.sitecubes.service.exception.type.FieldValidationException;

public class InvalidPageLayoutStructureException extends FieldValidationException {

	public InvalidPageLayoutStructureException() {
		super("page-layout-structure-cannot-be-empty", "layout-structure-field");
	}

}

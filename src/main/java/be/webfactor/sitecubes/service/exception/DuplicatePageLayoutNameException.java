package be.webfactor.sitecubes.service.exception;

import be.webfactor.sitecubes.service.exception.type.FieldValidationException;

public class DuplicatePageLayoutNameException extends FieldValidationException {

	public DuplicatePageLayoutNameException() {
		super("page-layout-with-that-name-already-exists", "layout-name-field");
	}

}

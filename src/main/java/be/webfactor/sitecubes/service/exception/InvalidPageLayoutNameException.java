package be.webfactor.sitecubes.service.exception;

import be.webfactor.sitecubes.service.exception.type.FieldValidationException;

public class InvalidPageLayoutNameException extends FieldValidationException {

	public InvalidPageLayoutNameException() {
		super("page-layout-name-should-not-be-empty", "layout-name-field");
	}

}

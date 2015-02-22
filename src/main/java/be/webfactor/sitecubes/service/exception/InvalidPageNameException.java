package be.webfactor.sitecubes.service.exception;

import be.webfactor.sitecubes.service.exception.type.FieldValidationException;

public class InvalidPageNameException extends FieldValidationException {

	public InvalidPageNameException() {
		super("page-name-should-not-be-empty", "page-name-field");
	}

}

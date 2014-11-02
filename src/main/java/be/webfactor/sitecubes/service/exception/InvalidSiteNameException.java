package be.webfactor.sitecubes.service.exception;

import be.webfactor.sitecubes.service.exception.type.FieldValidationException;

public class InvalidSiteNameException extends FieldValidationException {

	public InvalidSiteNameException() {
		super("site-name-should-not-be-empty", "site-name-field");
	}

}

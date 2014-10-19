package be.webfactor.sitecubes.service.exception;

import be.webfactor.sitecubes.service.exception.type.FieldValidationException;

public class DuplicateSiteNameException extends FieldValidationException {

	public DuplicateSiteNameException() {
		super("site-with-that-name-already-exists", "site-name-field");
	}

}

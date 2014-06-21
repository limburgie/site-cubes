package be.webfactor.sitecubes.service.exception;

import be.webfactor.sitecubes.service.exception.type.FieldValidationException;

public class DuplicateFriendlyUrlException extends FieldValidationException {

	public DuplicateFriendlyUrlException() {
		super("page-with-that-friendly-url-already-exists", "page-friendly-url-field");
	}

}

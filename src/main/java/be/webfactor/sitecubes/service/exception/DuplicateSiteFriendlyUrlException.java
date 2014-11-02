package be.webfactor.sitecubes.service.exception;

import be.webfactor.sitecubes.service.exception.type.FieldValidationException;

public class DuplicateSiteFriendlyUrlException extends FieldValidationException {

	public DuplicateSiteFriendlyUrlException() {
		super("site-with-that-friendly-url-already-exists", "site-friendly-url-field");
	}

}

package be.webfactor.sitecubes.service.exception;

import be.webfactor.sitecubes.service.exception.type.FieldValidationException;

public class InvalidSiteFriendlyUrlException extends FieldValidationException {

	public InvalidSiteFriendlyUrlException() {
		super("site-friendly-url-invalid", "site-friendly-url-field");
	}

}

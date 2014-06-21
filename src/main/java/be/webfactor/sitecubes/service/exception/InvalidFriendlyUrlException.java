package be.webfactor.sitecubes.service.exception;

import be.webfactor.sitecubes.service.exception.type.FieldValidationException;

public class InvalidFriendlyUrlException extends FieldValidationException {

	public InvalidFriendlyUrlException() {
		super("friendly-url-invalid", "page-friendly-url-field");
	}

}

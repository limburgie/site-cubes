package be.webfactor.sitecubes.service.exception;

import be.webfactor.sitecubes.service.exception.type.FieldValidationException;

public class InvalidPageFriendlyUrlException extends FieldValidationException {

	public InvalidPageFriendlyUrlException() {
		super("page-friendly-url-invalid", "page-friendly-url-field");
	}

}

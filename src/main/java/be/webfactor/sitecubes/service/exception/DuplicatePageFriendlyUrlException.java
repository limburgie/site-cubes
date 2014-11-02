package be.webfactor.sitecubes.service.exception;

import be.webfactor.sitecubes.service.exception.type.FieldValidationException;

public class DuplicatePageFriendlyUrlException extends FieldValidationException {

	public DuplicatePageFriendlyUrlException() {
		super("page-with-that-friendly-url-already-exists", "page-friendly-url-field");
	}

}

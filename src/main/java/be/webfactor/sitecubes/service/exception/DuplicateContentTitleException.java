package be.webfactor.sitecubes.service.exception;

import be.webfactor.sitecubes.service.exception.type.FieldValidationException;

public class DuplicateContentTitleException extends FieldValidationException {

	public DuplicateContentTitleException() {
		super("content-item-with-that-title-already-exists", "content-title-field");
	}

}

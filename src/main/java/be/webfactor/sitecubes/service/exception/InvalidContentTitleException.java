package be.webfactor.sitecubes.service.exception;

import be.webfactor.sitecubes.service.exception.type.FieldValidationException;

public class InvalidContentTitleException extends FieldValidationException {

	public InvalidContentTitleException() {
		super("content-title-should-not-be-empty", "content-title-field");
	}

}

package be.webfactor.sitecubes.service.exception;

import be.webfactor.sitecubes.service.exception.type.FieldValidationException;

public class InvalidUserLastNameException extends FieldValidationException {

	public InvalidUserLastNameException() {
		super("last-name-should-not-be-empty", "user-last-name-field");
	}

}

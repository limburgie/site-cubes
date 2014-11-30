package be.webfactor.sitecubes.service.exception;

import be.webfactor.sitecubes.service.exception.type.FieldValidationException;

public class InvalidUserFirstNameException extends FieldValidationException {

	public InvalidUserFirstNameException() {
		super("first-name-should-not-be-empty", "user-first-name-field");
	}

}

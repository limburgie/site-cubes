package be.webfactor.sitecubes.service.exception;

import be.webfactor.sitecubes.service.exception.type.FieldValidationException;

public class InvalidUsernameException extends FieldValidationException {

	public InvalidUsernameException() {
		super("username-should-not-be-empty", "user-username-field");
	}

}

package be.webfactor.sitecubes.service.exception;

import be.webfactor.sitecubes.service.exception.type.FieldValidationException;

public class InvalidPasswordException extends FieldValidationException {

	public InvalidPasswordException() {
		super("password-should-not-be-empty", "user-password-field");
	}

}

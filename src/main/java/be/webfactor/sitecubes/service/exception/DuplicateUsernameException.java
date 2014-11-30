package be.webfactor.sitecubes.service.exception;

import be.webfactor.sitecubes.service.exception.type.FieldValidationException;

public class DuplicateUsernameException extends FieldValidationException {

	public DuplicateUsernameException() {
		super("user-with-that-username-already-exists", "user-username-field");
	}

}

package be.webfactor.sitecubes.service.exception;

import be.webfactor.sitecubes.service.exception.type.FieldValidationException;

public class InvalidThemeNameException extends FieldValidationException {

	public InvalidThemeNameException() {
		super("theme-name-should-not-be-empty", "theme-name-field");
	}

}

package be.webfactor.sitecubes.service.exception;

public class InvalidPageNameException extends FieldValidationException {

	public InvalidPageNameException() {
		super("page-name-should-not-be-empty", "page-name-field");
	}

}

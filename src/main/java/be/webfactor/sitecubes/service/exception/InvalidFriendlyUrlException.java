package be.webfactor.sitecubes.service.exception;

public class InvalidFriendlyUrlException extends FieldValidationException {

	public InvalidFriendlyUrlException() {
		super("friendly-url-invalid", "page-friendly-url-field");
	}

}

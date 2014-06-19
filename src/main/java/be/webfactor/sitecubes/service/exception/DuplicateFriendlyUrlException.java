package be.webfactor.sitecubes.service.exception;

public class DuplicateFriendlyUrlException extends FieldValidationException {

	public DuplicateFriendlyUrlException() {
		super("that-friendly-url-is-already-taken", "page-friendly-url-field");
	}

}

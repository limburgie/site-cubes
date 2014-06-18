package be.webfactor.sitecubes.service.exception;

public class DuplicateFriendlyUrlException extends MessagedException {

	public DuplicateFriendlyUrlException() {
		super("that-friendly-url-is-already-taken");
	}

}

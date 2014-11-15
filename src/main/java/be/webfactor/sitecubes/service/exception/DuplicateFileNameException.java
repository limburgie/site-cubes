package be.webfactor.sitecubes.service.exception;

import be.webfactor.sitecubes.service.exception.type.MessagedException;

public class DuplicateFileNameException extends MessagedException {

	public DuplicateFileNameException() {
		super("file-with-name-x-already-exists");
	}

}

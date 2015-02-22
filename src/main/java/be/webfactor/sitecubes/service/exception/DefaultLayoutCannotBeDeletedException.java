package be.webfactor.sitecubes.service.exception;

import be.webfactor.sitecubes.service.exception.type.MessagedException;

public class DefaultLayoutCannotBeDeletedException extends MessagedException {

	public DefaultLayoutCannotBeDeletedException() {
		super("default-layout-cannot-be-deleted");
	}

}

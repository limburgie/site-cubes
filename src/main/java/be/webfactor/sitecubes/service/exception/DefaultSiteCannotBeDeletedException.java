package be.webfactor.sitecubes.service.exception;

import be.webfactor.sitecubes.service.exception.type.MessagedException;

public class DefaultSiteCannotBeDeletedException extends MessagedException {

	public DefaultSiteCannotBeDeletedException() {
		super("default-site-cannot-be-deleted");
	}

}

package be.webfactor.sitecubes.service.exception;

import be.webfactor.sitecubes.service.exception.type.MessagedException;

public class DefaultThemeCannotBeDeletedException extends MessagedException {

	public DefaultThemeCannotBeDeletedException() {
		super("default-theme-cannot-be-deleted");
	}

}

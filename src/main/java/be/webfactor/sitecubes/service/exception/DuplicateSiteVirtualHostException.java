package be.webfactor.sitecubes.service.exception;

import be.webfactor.sitecubes.service.exception.type.FieldValidationException;

public class DuplicateSiteVirtualHostException extends FieldValidationException {

	public DuplicateSiteVirtualHostException() {
		super("site-with-that-virtual-host-already-exists", "site-virtual-host-field");
	}

}

package be.webfactor.sitecubes.service.exception;

import be.webfactor.sitecubes.service.exception.type.MessagedException;

public class DuplicateRoleAssignmentException extends MessagedException {

	public DuplicateRoleAssignmentException() {
		super("this-role-is-already-assigned-to-this-user-in-this-context");
	}

}

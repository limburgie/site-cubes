package be.webfactor.sitecubes.service.exception;

public abstract class MessagedException extends RuntimeException {

	private String resourceKey;

	public MessagedException(String resourceKey) {
		this.resourceKey = resourceKey;
	}

	public String getResourceKey() {
		return resourceKey;
	}

}

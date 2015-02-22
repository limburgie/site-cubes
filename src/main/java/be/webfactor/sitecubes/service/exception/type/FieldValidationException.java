package be.webfactor.sitecubes.service.exception.type;

public abstract class FieldValidationException extends MessagedException {

	private String fieldWrapperClass;

	public FieldValidationException(String resourceKey, String fieldWrapperClass) {
		super(resourceKey);
		this.fieldWrapperClass = fieldWrapperClass;
	}

	public String getFieldWrapperClass() {
		return fieldWrapperClass;
	}
}

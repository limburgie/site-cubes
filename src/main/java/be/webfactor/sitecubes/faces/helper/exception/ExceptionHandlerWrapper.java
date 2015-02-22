package be.webfactor.sitecubes.faces.helper.exception;

import be.webfactor.sitecubes.util.BeanLocator;

import javax.faces.FacesException;
import javax.faces.context.ExceptionHandler;

/**
 * Exception Handler wrapper class that passes unhandled Exceptions to the FacesExceptionHandler.
 */
public class ExceptionHandlerWrapper extends javax.faces.context.ExceptionHandlerWrapper {

	private final ExceptionHandler exceptionHandler;
	private FacesExceptionHandler facesExceptionHandler;

	public ExceptionHandlerWrapper(ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
		this.facesExceptionHandler = BeanLocator.getBean(FacesExceptionHandler.class);
	}

	@Override
	public void handle() throws FacesException {
		facesExceptionHandler.handleExceptionEvents(getUnhandledExceptionQueuedEvents());
	}

	@Override
	public ExceptionHandler getWrapped() {
		return exceptionHandler;
	}

}

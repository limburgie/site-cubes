package be.webfactor.sitecubes.faces.helper.exception;

import be.webfactor.sitecubes.faces.helper.FacesUtil;
import be.webfactor.sitecubes.service.exception.type.FieldValidationException;
import be.webfactor.sitecubes.service.exception.type.MessagedException;
import org.springframework.security.access.AccessDeniedException;

import javax.faces.FacesException;
import javax.faces.event.ExceptionQueuedEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Iterator;

/**
 * ExceptionHandler class used to properly handle uncaught Exceptions in the context of JSF portlets. See
 * LiferayExceptionHandlerFactory for more information.
 */
@Named
public class FacesExceptionHandler {

	@Inject private FacesUtil facesUtil;

	public void handleExceptionEvents(Iterable<ExceptionQueuedEvent> events) {
		Iterator<ExceptionQueuedEvent> exceptionEvents = events.iterator();
		while (exceptionEvents.hasNext()) {
			Throwable eventException = exceptionEvents.next().getContext().getException();
			Throwable actualException = getNonJsfException(eventException);
			handleException(actualException);
			exceptionEvents.remove();
		}
	}

	private void handleException(Throwable actual) {
		if (actual instanceof FieldValidationException) {
			FieldValidationException fve = (FieldValidationException) actual;
			facesUtil.addErrorStyling(fve.getFieldWrapperClass());
		}
		else if (actual instanceof MessagedException) {
			MessagedException e = (MessagedException) actual;
			facesUtil.error(e.getResourceKey(), e);
		} else if (actual instanceof AccessDeniedException) {
			facesUtil.permissionError(actual);
		} else {
			facesUtil.unexpectedError(actual);
		}
	}

	private Throwable getNonJsfException(Throwable t) {
		return ExceptionUtil.getCauseExcludingTypes(t, FacesException.class);
	}

}

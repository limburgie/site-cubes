package be.webfactor.sitecubes.faces.helper;

import be.webfactor.sitecubes.service.exception.MessagedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.util.Locale;
import java.util.ResourceBundle;

@Named
public class FacesUtil {

	private static final Log LOGGER = LogFactory.getLog(FacesUtil.class);

	public void info(String key) {
		msg(FacesMessage.SEVERITY_INFO, key);
	}

	public void error(String key) {
		msg(FacesMessage.SEVERITY_ERROR, key);
	}

	private void msg(FacesMessage.Severity severity, String key) {
		fc().addMessage(null, new FacesMessage(severity, translate(key), null));
	}

	private String translate(String key) {
		String baseName = fc().getApplication().getMessageBundle();
		ResourceBundle rb = ResourceBundle.getBundle(baseName);
		return rb.getString(key);
	}

	private Locale getLocale() {
		return fc().getViewRoot().getLocale();
	}

	private FacesContext fc() {
		return FacesContext.getCurrentInstance();
	}

	public void unexpectedError(Throwable t) {
		error("unexpected-error");
		LOGGER.error(t);
	}

	public void recoverableError(MessagedException e) {
		error(e.getResourceKey());
	}

}

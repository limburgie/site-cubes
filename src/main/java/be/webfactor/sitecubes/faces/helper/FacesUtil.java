package be.webfactor.sitecubes.faces.helper;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.context.RequestContext;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
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

	public void error(String key, Throwable t) {
		error(key);
		LOGGER.error(t);
	}

	public String getParam(String key) {
		return ((HttpServletRequest) fc().getExternalContext().getRequest()).getParameter(key);
	}

	private void msg(FacesMessage.Severity severity, String key) {
		fc().addMessage(null, new FacesMessage(severity, translate(key), null));
	}

	private String translate(String key) {
		String baseName = fc().getApplication().getMessageBundle();
		ResourceBundle rb = ResourceBundle.getBundle(baseName);
		return rb.getString(key);
	}

	private FacesContext fc() {
		return FacesContext.getCurrentInstance();
	}

	public void addErrorStyling(String className) {
		js("jQuery(\"." + className + "\").addClass(\"has-error\")");
	}

	public void js(String script) {
		RequestContext.getCurrentInstance().execute(script);
	}

	public void unexpectedError(Throwable t) {
		error("unexpected-error");
		LOGGER.error(t);
	}

}

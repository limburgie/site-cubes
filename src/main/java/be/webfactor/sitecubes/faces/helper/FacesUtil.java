package be.webfactor.sitecubes.faces.helper;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.context.RequestContext;

import javax.el.MethodExpression;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ResourceBundle;

@Named
public class FacesUtil {

	private static final Log LOGGER = LogFactory.getLog(FacesUtil.class);
	private static final String PRIME_COMPONENT_PREFIX = "org.primefaces.component.";
	private static final String PRIME_RENDERER_SUFFIX = "Renderer";

	/*
	 * General utilities
	 */

	private FacesContext fc() {
		return FacesContext.getCurrentInstance();
	}

	private ExternalContext ec() {
		return fc().getExternalContext();
	}

	/*
	 * Messaging utilities
	 */

	public void info(String key, Object... params) {
		msg(FacesMessage.SEVERITY_INFO, key, params);
	}

	public void error(String key, Object... params) {
		msg(FacesMessage.SEVERITY_ERROR, key, params);
	}

	public void error(String key, Throwable t) {
		error(key);
		LOGGER.error(t);
	}

	public void unexpectedError(Throwable t) {
		error("unexpected-error");
		LOGGER.error("An unexpected error occurred", t);
	}

	public void permissionError(Throwable t) {
		error("access-denied-error");
		LOGGER.error("A user performed an action he was not allowed to", t);
	}

	private void msg(FacesMessage.Severity severity, String key, Object... params) {
		String message = MessageFormat.format(translate(key), params);
		fc().addMessage(null, new FacesMessage(severity, message, message));
	}

	private String translate(String key) {
		String baseName = fc().getApplication().getMessageBundle();
		return ResourceBundle.getBundle(baseName).getString(key);
	}

	/*
	 * Request related stuff
	 */

	private HttpServletRequest getRequest() {
		return ((HttpServletRequest) ec().getRequest());
	}

	public String getRequestParam(String key) {
		return getRequest().getParameter(key);
	}

	public boolean isAdminView() {
		return fc().getViewRoot().getViewId().startsWith("/pages/admin");
	}

	public String prefixWithContext(String path) {
		String contextPath = ec().getRequestContextPath();
		if (StringUtils.isBlank(contextPath)) {
			return "/" + path;
		}
		return contextPath + "/" + path;
	}

	public void forwardTo(String url) {
		RequestDispatcher dispatcher = ((ServletRequest)ec().getRequest()).getRequestDispatcher(url);
		try {
			dispatcher.forward((ServletRequest)ec().getRequest(), (ServletResponse)ec().getResponse());
		} catch (ServletException e) {
			unexpectedError(e);
		} catch (IOException e) {
			unexpectedError(e);
		}
		fc().responseComplete();
	}

	public boolean isUserLoggedIn() {
		return getRequest().getUserPrincipal() != null;
	}

	/*
	 * Component utilities
	 */

	public <T> T createPrimeComponent(Class<? extends UIComponent> componentClass) {
		String componentName = componentClass.getSimpleName();
		return (T) createComponent(PRIME_COMPONENT_PREFIX + componentName, PRIME_COMPONENT_PREFIX + componentName + PRIME_RENDERER_SUFFIX);
	}

	public UIComponent createComponent(String componentClassName, String rendererClassName) {
		return fc().getApplication().createComponent(fc(), componentClassName, rendererClassName);
	}

	public void addErrorStyling(String className) {
		js("jQuery(\"." + className + "\").addClass(\"has-error\")");
	}

	public void js(String script) {
		RequestContext.getCurrentInstance().execute(script);
	}

	public MethodExpression createMethodExpression(String expression, Class<?> returnType, Class<?>... parameterTypes) {
		return fc().getApplication().getExpressionFactory().createMethodExpression(
				fc().getELContext(), expression, returnType, parameterTypes);
	}

}

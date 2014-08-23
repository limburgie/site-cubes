package be.webfactor.sitecubes.faces.helper;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.context.RequestContext;

import javax.el.MethodExpression;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.util.ResourceBundle;

@Named
public class FacesUtil {

	private static final Log LOGGER = LogFactory.getLog(FacesUtil.class);
	private static final String PRIME_COMPONENT_PREFIX = "org.primefaces.component.";
	private static final String PRIME_RENDERER_SUFFIX = "Renderer";

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
		return getRequest().getParameter(key);
	}

	private HttpServletRequest getRequest() {
		return ((HttpServletRequest) fc().getExternalContext().getRequest());
	}

	public boolean isAdminView() {
		return fc().getViewRoot().getViewId().startsWith("/pages/admin");
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

	public String getRootContext() {
		String contextPath = fc().getExternalContext().getRequestContextPath();
		return StringUtils.isBlank(contextPath) ? "/" : contextPath;
	}

	public String prefixWithContext(String path) {
		String contextPath = fc().getExternalContext().getRequestContextPath();
		if (StringUtils.isBlank(contextPath)) {
			return "/" + path;
		}
		return contextPath + "/" + path;
	}

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

	public void unexpectedError(Throwable t) {
		error("unexpected-error");
		LOGGER.error("An unexpected error occurred", t);
	}

	public MethodExpression createMethodExpression(String expression, Class<?> returnType, Class<?>... parameterTypes) {
		return fc().getApplication().getExpressionFactory().createMethodExpression(
				fc().getELContext(), expression, returnType, parameterTypes);
	}

	public boolean isUserLoggedIn() {
		return getRequest().getUserPrincipal() != null;
	}

}

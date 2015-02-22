package be.webfactor.sitecubes.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.inject.Named;

@Named
public class BeanLocator implements ApplicationContextAware {

	private static ApplicationContext ctx;

	/**
	 * Returns the bean of the specified type.
	 */
	public static <T> T getBean(Class<T> serviceClass) {
		return ctx.getBean(serviceClass);
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		ctx = applicationContext;
	}

}

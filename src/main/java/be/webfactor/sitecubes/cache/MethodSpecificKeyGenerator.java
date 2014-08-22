package be.webfactor.sitecubes.cache;

import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.cache.interceptor.KeyGenerator;

import javax.inject.Named;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Named
public class MethodSpecificKeyGenerator implements KeyGenerator {

	public Object generate(Object target, Method method, Object... params) {
		Class<?> objectclass = AopProxyUtils.ultimateTargetClass(target);
		List<Object> cacheKey = new ArrayList<Object>();
		cacheKey.add(objectclass.getName().intern());
		cacheKey.add(System.identityHashCode(target));
		cacheKey.add(method.toString().intern());
		cacheKey.addAll(Arrays.asList(params));
		return cacheKey;
	}

}

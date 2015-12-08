package springbook.learningtest.jdk;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class UppercaseHandler implements InvocationHandler {

	Object target;

	public UppercaseHandler(Hello target) {
		this.target = target;
	}

	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		Object ret = method.invoke(target, args);
		if(ret instanceof String){
			return ((String) ret).toUpperCase();
		}else {
			return ret;
		}
		
	}

}
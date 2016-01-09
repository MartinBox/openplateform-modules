package com.open.shiro.dynamic.permissions.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class CustomerInvocationHandler implements InvocationHandler {

	private Object targetObject;

	public Object newProxy(Object targetObject) {
		this.targetObject = targetObject;
		//得到代理对象的方法，这个是反射机制里面的对象方法  
		return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(), targetObject.getClass().getInterfaces(), this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return method.invoke(this.targetObject, args);

	}
}

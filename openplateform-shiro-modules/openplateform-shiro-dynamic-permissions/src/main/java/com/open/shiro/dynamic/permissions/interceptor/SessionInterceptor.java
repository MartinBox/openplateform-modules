package com.open.shiro.dynamic.permissions.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class SessionInterceptor extends HandlerInterceptorAdapter {

	/**
	* 会话ID
	*/
	private final static String SESSION_KEY = "transactionId";

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {

		// 删除
		MDC.remove(SESSION_KEY);
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		// 放SessionId
		String token = java.util.UUID.randomUUID().toString().replace("-", "");
		MDC.put(SESSION_KEY, token);

		return true;
	}
}

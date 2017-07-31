package com.open.spring.aop1.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.open.spring.aop1.annotation.LogAnnotation;

@Component
@Aspect
public class LogAspect {

	private final Logger logger = LoggerFactory.getLogger(LogAspect.class);
	private final Logger accessLog = LoggerFactory.getLogger("log.access");

	@Around("execution(* com.open.spring.aop1.service.*.*(..)) && @annotation(annotation)")
	public Object around(ProceedingJoinPoint joinpoint, LogAnnotation annotation) {
		Object returnVal = null;
		try {
			long startTime = System.currentTimeMillis();
			returnVal = joinpoint.proceed();
			MethodSignature signature = (MethodSignature) joinpoint.getSignature();
			StringBuffer timeInfo = new StringBuffer();
			timeInfo.append(signature.getDeclaringTypeName() + "." + signature.getMethod().getName()).append("|").append(System.currentTimeMillis() - startTime).append("|");
			accessLog.info(timeInfo.toString());
		} catch (Throwable e) {
			logger.error("error", e);

		}
		return returnVal;
	}
}

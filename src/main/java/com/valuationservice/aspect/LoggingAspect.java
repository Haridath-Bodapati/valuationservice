//Jesus
package com.valuationservice.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging execution of service methods, to print logger for method
 * entry and exit.
 * 
 * @author Haridath Bodapati
 */
@Aspect
@Component
public class LoggingAspect {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * Used to log the method entered logger to console
	 * 
	 * @param joinPoint
	 */
	@Before("execution(* com.valuationservice.service.*.*(..))")
	public void beforeLog(JoinPoint joinPoint) {
		log.info("Entering method: {} with arguments {}", joinPoint.getSignature().getName(), joinPoint.getArgs());
	}

	/**
	 * Used to log the method exiting logger to console
	 * 
	 * @param joinPoint
	 * @param result
	 */
	@AfterReturning(pointcut = "execution(* com.valuationservice.service.*.*(..))", returning = "result")
	public void afterReturningLog(JoinPoint joinPoint, Object result) {
		log.info("Exiting method: {} with result {}", joinPoint.getSignature().getName(), result);
	}
}

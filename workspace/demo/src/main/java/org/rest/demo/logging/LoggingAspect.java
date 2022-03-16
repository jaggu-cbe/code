package org.rest.demo.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	
	private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
	
	@Around("@annotation(org.rest.demo.logging.LogExecutionTime)")
    public Object logBeforeAndAfterServiceMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		long startTime = System.currentTimeMillis();
		logger.info("{}.{}()|started|time:{}|", proceedingJoinPoint.getSignature().getDeclaringTypeName(), proceedingJoinPoint.getSignature().getName(), startTime);
        Object result = proceedingJoinPoint.proceed();
        long endtime = System.currentTimeMillis();
		logger.info("{}.{}()|finished|time:{}|timetaken:{}ms|", proceedingJoinPoint.getSignature().getDeclaringTypeName(), proceedingJoinPoint.getSignature().getName(), endtime, (endtime-startTime));
        return result;
    }
}

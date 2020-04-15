package edu.team3.onlineshop.aspects;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ApplicationLoggerAspect {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Pointcut("within(edu.team3.onlineshop.controller..*)")
	
	public void definePackagePointCuts() {
		//enpty method just to name the location specified in the pointcut
	}
	
	@Before("definePackagePointCuts()")
	public void log(JoinPoint jp) {
		log.debug("\n \n \n ");
		log.debug("*****************************Before Method Execution \n {}.{} () with arguments[s] = {}",
				jp.getSignature().getDeclaringType(),
				jp.getSignature().getName(), Arrays.toString(jp.getArgs()));
		
		log.debug("--------------------------------------------------------------------------\n \n \n");
		
	}

}

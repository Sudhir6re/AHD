package com.mahait.gov.in.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.mahait.gov.in.service.WelcomeService;

//Enables the spring AOP functionality in an application
@Aspect
@Component
public class ApplicationAspect {
	// Displays all the available methods i.e. the advice will be called for all the
	// methods
//	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	WelcomeService welcomeService;
	
	
	@Before(value = "execution(* com.mahait.gov.in.controller.*..*(..)) ") 
	public void beforeAdvice(JoinPoint joinPoint) {  
		welcomeService.idleTerminateConnectivity();
//		logger.info("Before method:" + joinPoint.getSignature());  
	}
	
	
	/*@Pointcut(value = "execution(* com.mahait.gov.in.controller.TopicController.*(..))")
	private void logDisplayingBalance() {
	}

	// Declares the around advice that is applied before and after the method
	// matching with a pointcut expression
	@Around(value = "logDisplayingBalance()")
	public void aroundAdvice(ProceedingJoinPoint jp) throws Throwable {
		logger.info(
				"The method aroundAdvice() before invokation of the method " + jp.getSignature().getName() + " method");
		try {
			jp.proceed();
		} finally {

		}
		logger.info(
				"The method aroundAdvice() after invokation of the method " + jp.getSignature().getName() + " method");
	}*/
}
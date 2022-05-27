package com.example.web.jpa.aspects;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

@Aspect
@Component
public class UsersCRUDAspect {

    private static final Logger logger = Logger.getLogger(UsersCRUDAspect.class.getName());

    /** Following is the definition for a PointCut to select
     *  all the methods available in UsersRepositoryHandler, which is in com.example.web.jpa package
     */
    @Pointcut("within(com.example.web.jpa..*) && execution(* com.example.web.jpa.handler.UsersRepositoryHandler.*(..))")
    public void usersRepositoryClassMethods() {
    }

    @Around("usersRepositoryClassMethods()")
    public Object measureMethodExecutionTime(ProceedingJoinPoint pjp) throws Throwable {
        long startExecutingMethod = System.nanoTime();
        Object proceed = pjp.proceed();
        long endExecutingMethod = System.nanoTime();
        String methodName = pjp.getSignature().getName();
        logger.info(
                "Execution of " + methodName + " took " +
                        TimeUnit.NANOSECONDS.toMillis(endExecutingMethod - startExecutingMethod) + " ms");
        return proceed;
    }
}

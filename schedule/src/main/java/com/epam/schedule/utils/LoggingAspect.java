package com.epam.schedule.utils;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("within(@org.springframework.stereotype.Repository *)" + " || within(@org.springframework.stereotype.Service *)" + " || within(@org.springframework.web.bind.annotation.RestController *)" + " || within(@org.springframework.web.bind.annotation.ControllerAdvice *)")
    public void loggingTargets() {
    }

    @AfterReturning(value = "loggingTargets()", returning = "value")
    public void logAfterExecution(JoinPoint joinPoint, Object value) {
        LOGGER.info("{} executed and returned value [{}]", joinPoint.getSignature().getName(), value);
    }


    @AfterThrowing(value = "loggingTargets()", throwing = "exception")
    public void logAfterException(JoinPoint joinPoint, Exception exception) {
        LOGGER.error("Method {}.{} threw {}. Exception: {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), exception.getClass().getSimpleName(), exception.getMessage());
    }

}
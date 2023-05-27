package ua.foxminded.pinchuk.javaspring.universityschedulewebapp.aspect;


import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Arrays;

@Component
@Aspect
public class ControllerAdvise {

    @Pointcut("execution(* ua.foxminded.pinchuk.javaspring.universityschedulewebapp.controller..*(..))")
    public void controllerPointcut(){}

    @Around("controllerPointcut()")
    public Object beforeEachDaoMethodLoggerAdvice(ProceedingJoinPoint proceedingJoinPoint){
        Object targetResult;

        try {
            targetResult = proceedingJoinPoint.proceed();
        } catch (Throwable e) {
//            LOGGER.error(e);
            Object [] args = proceedingJoinPoint.getArgs();
            for(Object arg : args){
                if(arg instanceof RedirectAttributes){
                    ((RedirectAttributes) arg).addAttribute("errorMessage", e.getMessage());
                }
            }
            return new RedirectView("/error");
        }

        return targetResult;
    }
}

package cn.beautybase.authorization.biz.base;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class ControllerAspect {

    @Pointcut("execution(public cn.beautybase.authorization.biz.base.Result *(..))")
    public void handleControllerMethod() {
    }

    @Around("handleControllerMethod()")
    public Object a(ProceedingJoinPoint pjp) {
        long startTime = System.currentTimeMillis();

        Result<?> result;
        try {
            result = (Result<?>) pjp.proceed();
            log.info(pjp.getSignature() + "use time:" + (System.currentTimeMillis() - startTime));
        } catch (Throwable e) {
            result = handlerException(pjp, e);
        }
        return result;
    }

    private Result<?> handlerException(ProceedingJoinPoint pjp, Throwable e) {

        if(e instanceof ServiceException) {
            ServiceException se = (ServiceException)e;
            return Result.buildFailure(se.getCode(), se.getMessage());
        } else {
            return Result.buildFailure(e);
        }

    }


}

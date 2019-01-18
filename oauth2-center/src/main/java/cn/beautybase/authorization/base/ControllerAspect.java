package cn.beautybase.authorization.base;

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

    @Pointcut("execution(public cn.beautybase.authorization.base.Result *(..))")
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
        /*
        Result<?> result = new Result();
        // 已知异常
        if (e instanceof CheckException) {
            result.setMsg(e.getLocalizedMessage());
            result.setCode(Result.FAIL);
        } else if (e instanceof UnloginException) {
            result.setMsg("Unlogin");
            result.setCode(Result.NO_LOGIN);
        } else {
            log.error(pjp.getSignature() + " error ", e);
            //TODO 未知的异常，应该格外注意，可以发送邮件通知等
            result.setMsg(e.toString());
            result.setCode(Result.FAIL);
        }*/

        Result<?> result = new Result();

        log.error(pjp.getSignature() + " error ", e);
        result.setMsg(e.toString());
        result.setCode(Result.FAIL);

        return result;
    }


}

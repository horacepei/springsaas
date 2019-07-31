package com.springboot.action.saas.common.logging.aspect;

import com.springboot.action.saas.common.logging.domain.Log;
import com.springboot.action.saas.common.logging.service.LogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAspect {
    //日志业务
    @Autowired
    private LogService logService;
    //当前时间
    private long currentTime = 0L;

    /**
     * 配置切入点, 匹配连接点的方法是否有Log注解，定义切点
     */
    @Pointcut("@annotation(com.springboot.action.saas.common.logging.annotation.Log)")
    public void logPointcut() {
        // 该方法无方法体,主要为了让同类中其他方法使用此切入点
    }

    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点，具体要通知在什么条件下执行和执行什么动作
     *
     * @param joinPoint join point for advice
     */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint){
        //返回值
        Object result = null;
        //获取当前时间
        currentTime = System.currentTimeMillis();
        try {
            //执行目标方法
            result = joinPoint.proceed();
        } catch (Throwable e) {
            //抛异常
            throw new RuntimeException(e.getMessage());
        }
        //创建日志对象
        Log log = new Log("INFO",System.currentTimeMillis() - currentTime);
        //记录日志
        logService.save(joinPoint, log);
        //返回目标方法的返回值
        return result;
    }

    /**
     * 配置异常通知，异常的日志也要记录
     *
     * @param joinPoint join point for advice
     * @param e exception
     */
    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        //创建日志对象
        Log log = new Log("ERROR",System.currentTimeMillis() - currentTime);
        //异常设置
        log.setExceptionDetail(e.getMessage());
        //记录异常
        logService.save((ProceedingJoinPoint)joinPoint, log);
    }
}

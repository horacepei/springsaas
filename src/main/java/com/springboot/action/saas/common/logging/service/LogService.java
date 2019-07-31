package com.springboot.action.saas.common.logging.service;

import com.springboot.action.saas.common.logging.domain.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;

/*
* 日志记录业务
*
*/
public interface LogService {
    /*
    * 记录日志，异步执行，不影响正常业务流程
    * 参数 joinPoint 切面方法的信息，当前切入点各种信息
    *     log 要记录的日志信息有那些
    * */
    @Async
    void save(ProceedingJoinPoint joinPoint, Log log);
}

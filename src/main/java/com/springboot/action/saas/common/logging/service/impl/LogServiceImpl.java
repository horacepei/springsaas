package com.springboot.action.saas.common.logging.service.impl;

import com.springboot.action.saas.common.logging.domain.Log;
import com.springboot.action.saas.common.logging.service.LogService;
import com.springboot.action.saas.common.utils.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Service
public class LogServiceImpl implements LogService {
    /*
    * 记录日志接口实现
    **/
    @Override
    public void save(ProceedingJoinPoint joinPoint, Log log) {
        //获取request 请求对象
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
                .getRequest();
        //getSignature获取切面相关信息，比如方法名、目标方法参数等信息
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        //获取抽象类（代理对象）方法
        Method method = signature.getMethod();
        //返回该元素的指定类型的注释，这里是Log注解
        com.springboot.action.saas.common.logging.annotation.Log aopLog = method.getAnnotation(com.springboot.action.saas.common.logging.annotation.Log.class);
        //获取注解传递的参数
        if (log != null) {
            log.setDescription(aopLog.value());
        }
        //通过最笨的反射方法，获取方法路径
        String methodName = joinPoint.getTarget().getClass().getName()+"."+signature.getName()+"()";
        log.setMethod(methodName);
        //参数处理
        //获取参数值
        Object[] argValues = joinPoint.getArgs();
        //获取参数名
        String[] argNames = ((MethodSignature)joinPoint.getSignature()).getParameterNames();
        //组织参数列表
        String params = "{";
        if(argValues != null){
            for (int i = 0; i < argValues.length; i++) {
                params += " " + argNames[i] + ": " + argValues[i];
            }
        }
        log.setParams(params + " }");
        //获取IP地址
        log.setRequestIp(StringUtils.getIP(request));
        //输出下日志到控制台
        System.out.println(log.toString());
    }

}

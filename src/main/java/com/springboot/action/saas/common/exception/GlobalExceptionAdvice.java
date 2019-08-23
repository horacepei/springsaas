package com.springboot.action.saas.common.exception;

import com.springboot.action.saas.common.controller.RestReturn;
import com.springboot.action.saas.common.controller.RestReturnEnum;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionAdvice {
    /**
     * 处理所有不可知的异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public RestReturn defaultException(HttpServletRequest request, Exception e){
        //输出堆栈信息到控制台，以后记录到日志
        e.printStackTrace();
        return new RestReturn(
                false,
                RestReturnEnum.EXCEPTION.getCode(),
                "",
                RestReturnEnum.EXCEPTION.getMessage()
        );
    }

    /**
     * 处理 接口无权访问异常AccessDeniedException FORBIDDEN(403, "Forbidden"),
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public RestReturn accessDeniedException(AccessDeniedException e){
        //输出堆栈信息到控制台，以后记录到日志
        e.printStackTrace();
        return new RestReturn(
                false,
                RestReturnEnum.FORBIDDEN.getCode(),
                "",
                RestReturnEnum.FORBIDDEN.getMessage()
        );
    }

    /**
     * 处理bad请求异常
     * @param e
     * @return
     */
    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public RestReturn badRequestException(RuntimeException e) {
        e.printStackTrace();
        return new RestReturn(
                false,
                RestReturnEnum.BAD_REQUEST.getCode(),
                "",
                RestReturnEnum.BAD_REQUEST.getMessage()
        );
    }

    /**
     * 处理 EntityNotFound 数据库数据未找到
     * @param e
     * @return
     */
    @ExceptionHandler(value = EntityNotFoundException.class)
    @ResponseBody
    public RestReturn entityNotFoundException(EntityNotFoundException e) {
        // 打印堆栈信息
        e.printStackTrace();
        return new RestReturn(
                false,
                RestReturnEnum.NOT_FOUND.getCode(),
                "",
                RestReturnEnum.NOT_FOUND.getMessage()
        );
    }
}

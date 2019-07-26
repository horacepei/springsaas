package com.springboot.action.saas.common.controller;

import org.springframework.core.MethodParameter;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class RestReturnWrapper implements ResponseBodyAdvice<Object> {
    /**
     * 判定哪些请求要执行beforeBodyWrite，返回true执行，返回false不执行
     * */
    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> converterType) {
        //获取当前处理请求的controller的方法
        //String methodName = methodParameter.getMethod().getName();
        // 拦/不拦截处理返回值的方法，如登录
        //String method = "login";
        //这里可以加入很多判定，如果在白名单的List里面，是否拦截
        return true;
    }


    /**
     * 返回前对body，request，response等请求做处理
     *
     * @param body
     * @param methodParameter
     * @param mediaType
     * @param httpMessageConverter
     * @param serverHttpRequest
     * @param serverHttpResponse
     *
     * @return
     * */
    @Override
    public Object beforeBodyWrite(Object body,
                    MethodParameter methodParameter,
                    MediaType mediaType,
                    Class<? extends HttpMessageConverter<?>> httpMessageConverter,
                    ServerHttpRequest serverHttpRequest,
                    ServerHttpResponse serverHttpResponse) {
        //具体返回值处理
        //情况1 如果返回的body为null
        if(body == null){
            if (mediaType == MediaType.APPLICATION_JSON) {
                //返回是json个格式类型，无body内容
                RestReturn restReturn = new RestReturn();
                return restReturn.success("", "");
            } else {
                return null;
            }
        } else {
            //情况2 文件上传下载，不需要改动，直接返回
            if (body instanceof Resource) {
                return body;
            } else if (body instanceof String) {
                // 返回的是 String，
                RestReturn restReturn = new RestReturn();
                try {
                    if (restReturn.isRestReturnJson((String) body)) {
                        // 情况3 已经是RestReturn格式的json 字符串不做统一格式封装
                        return body;
                    } else {
                        //情况4 普通的返回，需要统一格式，把数据赋值回去即可。
                        return restReturn.success(body, "");
                    }
                } catch (Exception e) {
                    // 因为 API返回值为String，理论上不会走到这个分支。
                    return restReturn.error(10000, body, e.getMessage());
                }
            } else {
                //返回的是非字符串格式，实际上很多时候用都是是在应用程返回的对象居多
                if(body instanceof RestReturn){
                    //情况5 如果已经封装成RestReturn,直接return
                    return body;
                }else{
                    //情况6 非字符串非统一格式的返回，需要统一格式
                    RestReturn restReturn = new RestReturn();
                    return restReturn.success(body, "");
                }
            }
        }
    }

}

package com.springboot.action.saas.common.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import sun.rmi.server.InactiveGroupException;

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
        //情况1 如果为null
        if(body == null){
            if (isJsonResponse(mediaType)) {
                //返回是json个格式类型，无body内容
                Object data = new String();
                Object message = new String();
                RestReturn restReturn = new RestReturn();
                return restReturn.success(data, message);
            } else {
                return null;
            }
        }else {
            //情况2 文件上传下载，不需要封装，直接返回
            if (body instanceof Resource) {
                return body;
            } else if (body instanceof String) {
                // 情况3 理论上只要API 返回值是 String / byte[]等
                // 不会由MappingJackson2HttpMessageConverter处理的返回值
                // 都有可能出错，抛出ClassCastException...
                // 目前API 出现的比较多的是String，所以只处理String情况
                // 如果 API返回的是 String，
                try {
                    if (WsResponse.isWsResponseJson((String) body)) {
                        // 情况4 已经是RestReturn格式的 字符串不做统一格式封装
                        return body;
                    } else {
                        if (isJsonResponse(mediaType)) {
                            // 如果 produces = application/json格式时，
                            // String返回值 将被StringHttpMessageConvertor处理，
                            // 所以此时应该返回字符串
                            return mapper.writeValueAsString(WsResponse.success(body));
                        } else {
                            // 如果 produces = text/html时，
                            // String返回值 将被StringHttpMessageConverter处理，
                            // 所以此时应该返回WsResponse的json序列化后的字符串
                            // 如果此时还是返回WsResponse对象，会抛出ClassCastException
                            // 因为StringHttpMessageConverter会把WsResponse对象当做String处理
                            return mapper.writeValueAsString(WsResponse.success(body));
                        }
                    }
                } catch (JsonProcessingException e) {
                    // 因为 API返回值为String，
                    // 所以不会抛异常，理论上不会走到这个分支
                    return body;
                }
            } else {
                //处理body为非字符串格式
                if(body instanceof RestReturn){
                    //如果已经封装成RestReturn,直接return
                    return body;
                }else{
                    //封装成RestReturn
                    return new RestReturn(body);
                }
            }
        }
        RestReturn result = new RestReturn(true, "0", body, null);
        return result;
    }

}

package com.springboot.action.saas.common.controller;

import lombok.Data;

import java.time.Instant;

/**
 * 接口返回统一数据结构
 */
@Data
public class RestReturn {
    //是否成功标志
    private boolean success;
    //code错误码
    private String code;
    //外带数据信息
    private Object data;
    //前端进行页面展示的信息
    private Object message;
    //返回时间戳
    private Long currentTime;
    /**
     *构造函数（无参数）
     */
    public RestReturn() {
        this.currentTime = Instant.now().toEpochMilli();
    }
    /**
     *构造函数（有参数）
     */
    public RestReturn(boolean success, String code, Object data, Object message) {
        this.success = success;
        this.code = code;
        this.data = data;
        this.message = message;
        this.currentTime = Instant.now().toEpochMilli();
    }
    @Override
    public String toString() {
        return "RestReturn{" +
                "success=" + success +
                ",code='" + code + '\'' +
                ",data=" + data +
                ",message=" + message +
                ",currentTime=" + currentTime +
                '}';
    }

    public RestReturn success(Object data, Object message) {
        this.success = true;
        this.code = "0";
        this.data = data;
        this.message = message;

        return this;
    }

    public RestReturn error(String code, Object data, Object message) {
        this.success = false;
        this.code = code;
        this.data = data;
        this.message = message;

        return this;
    }
}


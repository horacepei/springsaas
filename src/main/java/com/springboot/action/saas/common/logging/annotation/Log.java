package com.springboot.action.saas.common.logging.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//作用的目标是类的方法
@Target(ElementType.METHOD)
//注解生效，在程序运行期间
@Retention(RetentionPolicy.RUNTIME)
//自定义注解Log
public @interface Log {
    //成员变量
    String value() default "";
}

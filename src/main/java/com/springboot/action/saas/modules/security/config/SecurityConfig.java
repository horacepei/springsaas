package com.springboot.action.saas.modules.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

//定义配置类被注解的类内部包含有一个或多个被@Bean注解的方法，这些方法将会被
//AnnotationConfigApplicationContext或AnnotationConfigWebApplicationContext类进行扫描，
//并用于构建bean定义，初始化Spring容器。
@Configuration
//加载了WebSecurityConfiguration配置类, 配置安全认证策略。
//加载了AuthenticationConfiguration,
@EnableWebSecurity
//用来构建一个全局的AuthenticationManagerBuilder的标志注解
//开启基于方法的安全认证机制，也就是说在web层的controller启用注解机制的安全确认
@EnableGlobalMethodSecurity(prePostEnabled = true)
//Web Security 配置类
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /*
     * 配置http服务，路径拦截、csrf保护等等均可通过此方法配置
     * */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        //HttpSecurity对象
        httpSecurity
                //禁用 CSRF,不然post调试的时候都403
                .csrf().disable()
                //设置权限定义哪些URL需要被保护、哪些不需要被保护。HttpSecurity对象的方法
                .authorizeRequests()
                //调试期间先允许访问
                .antMatchers("/member/**").permitAll()
                //认证通过后任何请求都可访问。AbstractRequestMatcherRegistry的方法
                .anyRequest().authenticated()
                //连接HttpSecurity其他配置方法
                .and()
                //生成默认登录页，HttpSecurity对象的方法
                .formLogin();
    }
}

package com.springboot.action.saas.modules.security.config;

import com.springboot.action.saas.modules.security.filter.JwtAuthorizationFilter;
import com.springboot.action.saas.modules.security.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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

    //实现UserDetailService接口用来做登录认证
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;
    //自定义基于JWT的安全过滤器，bean
    @Autowired
    JwtAuthorizationFilter authenticationFilter;
    /*
     * 配置http服务，路径拦截、csrf保护等等均可通过此方法配置
     * */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        //HttpSecurity对象
        httpSecurity
                // 禁用 CSRF,不然post调试的时候都403，CSRF和RESTful技术有冲突。CSRF默认支持的方法： GET|HEAD|TRACE|OPTIONS，不支持POST
                .csrf().disable()
                // 由于使用jwt,不创建会话
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 设置权限定义哪些URL需要被保护、哪些不需要被保护。HttpSecurity对象的方法
                .authorizeRequests()
                // 过滤请求,允许对网站静态资源的访问，无需授权
                .antMatchers(
                    HttpMethod.GET,
                    "/*.html",
                    "/favicon.ico",
                    "/**/*.html",
                    "/**/*.css",
                    "/**/*.js"
                ).permitAll()
                // 登陆页面，无需授权
                .antMatchers(HttpMethod.POST, "/security/pwdlogin").permitAll()
                // 调试期间先允许访问
                //.antMatchers("/member/**").permitAll()
                // 认证通过后任何请求都可访问。AbstractRequestMatcherRegistry的方法
                .anyRequest().authenticated()
                // 连接HttpSecurity其他配置方法
                .and()
                // 生成默认登录页，HttpSecurity对象的方法
                .formLogin();
        // 增加jwt filter
        httpSecurity
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }
    /**
     * 设定PsswordEncoder为BeanBcrypt加密方式，后面在设定AuthenticationProvider需要用到
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoderBean() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 创建认证提供者Bean
     * DaoAuthenticationProvider是SpringSecurity提供的AuthenticationProvider默认实现类
     * 授权方式提供者，判断授权有效性，用户有效性，在判断用户是否有效性，
     * 它依赖于UserDetailsService实例，可以自定义UserDetailsService的实现。
     *
     * @return
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        // 创建DaoAuthenticationProvider实例
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // 将自定义的认证逻辑添加到DaoAuthenticationProvider
        authProvider.setUserDetailsService(userDetailsServiceImpl);
        // 设置自定义的密码加密
        authProvider.setPasswordEncoder(passwordEncoderBean());
        return authProvider;
    }

    /*
     * 配置好的认证提供者列表
     *
     * */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 添加自定义的认证逻辑
        auth.authenticationProvider(authenticationProvider());
    }

}

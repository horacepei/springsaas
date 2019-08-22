package com.springboot.action.saas.modules.security.filter;


import com.springboot.action.saas.modules.security.dto.UserDetailsDto;
import com.springboot.action.saas.modules.security.utils.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
* 自定义过滤器，主要负责从请求头中读取token，解析token
* 检查token是否合法
* */
@Component
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    //认证用户信息业务对象
    private final UserDetailsService userDetailsService;
    //jwt 工具对象
    private final JwtUtil jwtUtil;
    //获取http请求头的key
    private final String tokenHeader;

    /*
    * 构造函数
    * @param @Qualifier用来标记service有两个实现类的时候，用那个
    * @param jwtUtil jwt工具对象
    * @param tokenHeader http请求头token字段
    * */
    public JwtAuthorizationFilter(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService,
                                  JwtUtil jwtUtil,
                                  @Value("${jwt.header}") String tokenHeader) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.tokenHeader = tokenHeader;
    }
    /*
    * 抽象类oncePerRequestFilter继承自GenericFilterBean，它保留了GenericFilterBean中的所有方法并对之进行了扩展，
    * 在oncePerRequestFilter中的主要方法是doFilter。在doFilter方法中，doFilterInternal方法由子类实现，
    * 主要作用是规定过滤的具体方法。
    * */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        //获取请求头中key对应的字段
        final String requestHeader = request.getHeader(this.tokenHeader);

        String username = null;
        String token = null;
        //处理请求头中的token
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            //获取token
            token = requestHeader.substring(7);
            //获取用户名（外带数据）
            try {
                username = jwtUtil.getUsernameFromToken(token);
            } catch (ExpiredJwtException e) {
                //发生异常，需要记录日志
                //log.error(e.getMessage());
            }
        }
        //用户名判定
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //获取用户信息
            UserDetailsDto userDetailsDto = (UserDetailsDto)this.userDetailsService.loadUserByUsername(username);
            //判定token是否合法，不合法走异常处理exceptionHandling().authenticationEntryPoint
            if (jwtUtil.validateToken(token, userDetailsDto)) {
                //合法，创建带用户名和密码以及权限的Authentication,这里实例化UsernamePasswordAuthenticationToken
                //构造函数内实际上已经设置为认证通过super.setAuthenticated(true);
                //构造函数3个参数：
                // 用户信息（身份认证信息，还有其他外带信息都可以增加）
                // 用户密码（于证明principal是正确的信息，比如密码）
                // 用户权限（授权信息，比如角色）
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetailsDto,
                        null,
                        userDetailsDto.getAuthorities());
                //设置获取request的一些http信息，把http的信息放到authentication
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                //记录日志
                //log.info("authorizated user '{}', setting security context", username);
                //从SecurityContextHolder获取SecurityContext实例，设置authentication
                //已验证的主体，或删除身份验证信息
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        //调用后续filter
        chain.doFilter(request, response);
    }
}

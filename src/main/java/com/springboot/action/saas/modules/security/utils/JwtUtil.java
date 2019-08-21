package com.springboot.action.saas.modules.security.utils;

import com.springboot.action.saas.modules.security.dto.UserDetailsDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil implements Serializable {

    private static final long serialVersionUID = -3301605591108950415L;
    //jwt包提供的时间对象
    private Clock clock = DefaultClock.INSTANCE; //时间工具实例
    //Header，Payload两部分的签名
    @Value("${jwt.secret}")
    private String secret;
    //超期时间
    @Value("${jwt.expiration}")
    private Long expiration;
    //http请求头字段
    @Value("${jwt.header}")
    private String tokenHeader;

    /*
     * 解析jwt字符串
     * @param token
     * @return Claims对象，jwt信息提供给的一个类
     * */
    private Claims getAllClaimsFromToken(String token) {
        //解析jwt到claims对象
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    /*
     * 判定是否token超期
     * @param token
     * @return boolean
     * */
    private Boolean isTokenExpired(String token) {
        //获取token超期时间
        final Date expiration = getExpirationDateFromToken(token);
        //判定expiration对象表示的瞬间比clock.now()表示的瞬间早，返回为true
        return expiration.before(clock.now());
    }
    /*
     * 获取token外带数据字段，这里是用户名称
     * @param token
     * @return 用户名称
     * */
    public String getUsernameFromToken(String token) {
        //获取jwt对象
        final Claims claims = getAllClaimsFromToken(token);
        return claims.getSubject();
    }

    /*
     * 获取token签发时间字段
     * @param token
     * @return 用户名称
     * */
    public Date getIssuedAtDateFromToken(String token) {
        //获取jwt对象
        final Claims claims = getAllClaimsFromToken(token);
        return claims.getIssuedAt();
    }

    /*
     * 获取token超期时间字段
     * @param token
     * @return 超期时间Date对象
     * */
    public Date getExpirationDateFromToken(String token) {
        //获取jwt对象
        final Claims claims = getAllClaimsFromToken(token);
        return claims.getExpiration();
    }

    /*
     * 判定是否签发时间，是否在修改密码之前
     * @param created 签发时间
     * @param lastPasswordReset 最后一次密码修改时间
     * @return boolean
     * */
    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    /*
     * 生成超期日期对象
     * @param createdDate 发放token日期对象
     * @return Date 超期日期对象
     * */
    private Date calculateExpirationDate(Date createdDate) {
        //时间戳初始化Date对象
        return new Date(createdDate.getTime() + expiration);
    }
    /*
     * 判定是否token超期
     * @param userDetails
     * @return jwt字符串
     * */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);
        //创建jwt
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /*
     * 刷新token
     * @param token token字符串
     * @return jwt字符串
     * */
    public String refreshToken(String token) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);

        final Claims claims = getAllClaimsFromToken(token);
        claims.setIssuedAt(createdDate);
        claims.setExpiration(expirationDate);

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }
    /*
     * 判定token是否合法
     * @param token token字符串
     * @param userDetails 验证用户数据
     * @return Date 超期日期对象
     * */
    public Boolean validateToken(String token, UserDetails userDetails) {
        //获取认证的用户信息
        UserDetailsDto user = (UserDetailsDto)userDetails;
        //获取token中的用户名称
        final String username = getUsernameFromToken(token);
        //获取token中的签发时间
        final Date created = getIssuedAtDateFromToken(token);
        //判定token：用户是否合法，是否超期，判定是否
        return (
                username.equals(user.getUsername())
                        && !isTokenExpired(token)
                        && !isCreatedBeforeLastPasswordReset(created, user.getPasswordResetDate())
        );
    }

}


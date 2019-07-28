package com.easy.work.util.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.easy.work.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Component
public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    public static long EXPIRATION_TIME ; //失效时间
    public static String TOKEN_SECRET ; //加密密码

     /**
      * @Description:生成token
      * @param
      * @author Created by wuzhangwei on 2019/7/27
      */
    public static String generateTokenWithAudience(User user) {
        String token = JWT.create().withAudience(user.getId().toString()) // 将 user id 保存到 token 里面
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) //过期时间
                .sign(Algorithm.HMAC256(TOKEN_SECRET));//密钥
        return token;
    }

     /**
      * @Description: 生成token（传入自定义值）
      * @param
      * @author Created by wuzhangwei on 2019/7/27
      */
    public static String generateTokenWithClaims(User user) {
        String token = JWT.create()
                .withClaim("userId", user.getId()) // 将 user id 保存到 token 里面
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) //过期时间
                .sign(Algorithm.HMAC256(TOKEN_SECRET));//密钥
        return token;
    }

     /**
      * @Description:解析token并返回生成token时放在token中的数据（返回jwt自定义的值,如果没有在负载中定义则返回null）
      * @param
      * @author Created by wuzhangwei on 2019/7/27
      */
    public static Map<String, Claim> validateTokenAndGetClaims(String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).build();
        Map<String, Claim> claim = null;
        try {
            claim = jwtVerifier.verify(token).getClaims();
        } catch (JWTDecodeException e) {
            logger.error("validateTokenAndGetClaims: token解析失败 {}", e.getMessage());
            throw new JWTDecodeException(e.getMessage());
        } catch (JWTVerificationException e) {
            logger.error("validateTokenAndGetClaims: token验证失败 {}", e.getMessage());
            throw new JWTVerificationException(e.getMessage());
        }
        return claim;
    }

    /**
     * @Description:解析token并返回生成token时放在token中的数据（返回jwt所面向的用户的值,如果没有在负载中定义则返回null）
     * @param
     * @author Created by wuzhangwei on 2019/7/27
     */
    public static List<String> validateTokenAndGetAudience(String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).build();
        List<String> audience = null;
        try {
            audience = jwtVerifier.verify(token).getAudience();
        } catch (JWTDecodeException e) {
            logger.error("validateTokenAndGetAudience: token解析失败 {}", e.getMessage());
            throw new JWTDecodeException(e.getMessage());
        } catch (JWTVerificationException e) {
            logger.error("validateTokenAndGetAudience: token验证失败 {}", e.getMessage());
            throw new JWTVerificationException(e.getMessage());
        }
        return audience;
    }

     /**
      * @Description:验证token
      * @param
      * @author Created by wuzhangwei on 2019/7/27
      */
    public static void validateToken(String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(TOKEN_SECRET)).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTDecodeException e) {
            logger.error("validateTokenAndGetAudience: token解析失败 {}", e.getMessage());
            throw new JWTDecodeException(e.getMessage());
        } catch (JWTVerificationException e) {
            logger.error("validateTokenAndGetAudience: token验证失败 {}", e.getMessage());
            throw new JWTVerificationException(e.getMessage());
        }
    }

    @Value("${config.jwt.expire-time}")
    public void setExpirationTime(long expirationTime) {
        EXPIRATION_TIME = expirationTime;
    }

    @Value("${config.jwt.secret}")
    public void setTokenSecret(String tokenSecret) {
        TOKEN_SECRET = tokenSecret;
    }

}

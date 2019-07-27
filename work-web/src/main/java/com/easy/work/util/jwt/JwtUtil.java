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
import java.util.Date;
import java.util.List;
import java.util.Map;

public class JwtUtil {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    public static final long EXPIRATION_TIME = 3600 * 1000; // 失效时间为一小时
    public static final String SECRET = "wuzhangwei"; //密钥，这个密钥不能泄露给别人，否则被人就能伪造你的token

     /**
      * @Description:生成token
      * @param
      * @author Created by wuzhangwei on 2019/7/27
      */
    public static String generateTokenWithAudience(User user) {
        String token = JWT.create().withAudience(user.getId().toString()) // 将 user id 保存到 token 里面
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) //过期时间
                .sign(Algorithm.HMAC256(SECRET));//密钥
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
                .sign(Algorithm.HMAC256(SECRET));//密钥
        return token;
    }

     /**
      * @Description:解析token并返回生成token时放在token中的数据（返回jwt自定义的值,如果没有在负载中定义则返回null）
      * @param
      * @author Created by wuzhangwei on 2019/7/27
      */
    public static Map<String, Claim> validateTokenAndGetClaims(String token) {
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
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
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
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
}

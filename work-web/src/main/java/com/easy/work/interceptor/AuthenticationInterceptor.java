package com.easy.work.interceptor;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.easy.work.annotation.UserLoginToken;
import com.easy.work.api.service.UserService;
import com.easy.work.common.enums.ResultEnum;
import com.easy.work.common.exception.EasyWorkException;
import com.easy.work.model.User;
import com.easy.work.util.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;


/**
* @Description:  token验证拦截器
* @Author: wuzhangwei
* @Date: 2019/7/26
*/
public class AuthenticationInterceptor implements HandlerInterceptor {
    @Autowired
    UserService userService;
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object object) throws Exception {
        String token = httpServletRequest.getHeader("token");// 从 http 请求头中取出 token
        // 如果不是映射到方法直接通过
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod=(HandlerMethod)object;
        Method method=handlerMethod.getMethod();

        //检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(UserLoginToken.class)) {
            UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
            if (userLoginToken.required()) {
                // 执行认证
                if (token == null) {
                    throw new EasyWorkException(ResultEnum.TOKEN_NOT_EXIST);
                }
                // 获取 token 中的 user id
                Long userId;
                try {
                    Map<String, Claim> claim = JwtUtil.validateTokenAndGetClaims(token);
                    userId = claim.get("userId").asLong();
                } catch (JWTDecodeException e) {
                    throw new JWTDecodeException("token解析失败 " + e.getMessage());
                } catch (JWTVerificationException e) {
                    throw new JWTVerificationException("token验证失败 " + e.getMessage());
                }

                User user = userService.findById(userId);
                if (user == null) {
                    throw new EasyWorkException(ResultEnum.USER_NOT_EXIST);
                }

                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}

package com.njmetro.evaluation.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 牟欢
 * @Classname TokenInterceptor
 * @Description TODO
 * @Date 2020-10-29 11:11
 */
@Slf4j
@RequiredArgsConstructor
@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Access-Token");
        log.info("token {}",token);
        return true;
    }
}

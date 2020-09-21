package com.njmetro.evaluation.token;

import com.njmetro.evaluation.constant.Constants;
import com.njmetro.evaluation.exception.AuthenticationException;
import com.njmetro.evaluation.exception.AuthorizationException;
import com.njmetro.evaluation.exception.SystemException;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Aspect
@Component
public class CheckTokenAndRoleHandler {
    /**
     * 定义切点
     *
     * @param checkTokenAndRole 作用于注解 @CheckTokenAndRole
     */
    @Pointcut("@annotation(checkTokenAndRole)")
    public void doCheckTokenAndRole(CheckTokenAndRole checkTokenAndRole) {
    }
    /**
     * 前置通知，校验令牌和权限
     *
     * @param checkTokenAndRole 作用于注解 @CheckTokenAndRole
     */
    @Before(value = "doCheckTokenAndRole(checkTokenAndRole)", argNames = "checkTokenAndRole")
    public void doBefore(CheckTokenAndRole checkTokenAndRole) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            HttpServletRequest request = requestAttributes.getRequest();
            /*
             * 令牌的位置：
             * 1、在请求头的 Authorization 中，其值为令牌
             * 2、在请求参数的 authorization 中，其值为令牌
             */
            if (checkTokenAndRole.needTokenInHeader() || checkTokenAndRole.needTokenInParam()) {
                String token = null;
                if (checkTokenAndRole.needTokenInHeader()) {
                    token = request.getHeader("Authorization");
                    log.info("Header: {}", token);
                }
                // 校验令牌是否合法，然后校验权限
                Claims claims;
                if (token != null && (claims = JwsToken.read(token)) != null) {
                    log.info("claims: {}", claims);
                    int role = Integer.parseInt(claims.get("role").toString());
                    // 超级管理员 SYSTEM_ROOT（role = 4）直接放行
                    if (role != Constants.SYSTEM_ROOT) {
                        int[] needRoleList = checkTokenAndRole.needRoleToAccess();
                        log.info("needRoleList:{}",needRoleList);
                        if (needRoleList.length > 0) {
                            boolean hasRole = false;
                            for (int i : needRoleList) {
                                if (i == role) {
                                    hasRole = true;
                                    break;
                                }
                            }
                            if (!hasRole) {
                                throw new AuthorizationException();
                            }
                        }
                    }
                    // 向 Request 中存储 Token 与 Claims
                    request.setAttribute("token", token);
                    request.setAttribute("claims", claims);
                } else {
                    throw new AuthenticationException();
                }
            }
            }
            else {
                throw new SystemException();
        }
    }
}

package com.digiex.spring.boot.demo.auth;

import com.digiex.spring.boot.demo.common.enums.SessionType;
import com.digiex.spring.boot.demo.common.exception.ApplicationException;
import com.digiex.spring.boot.demo.common.util.Constant;
import com.digiex.spring.boot.demo.controller.model.APIStatus;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Quy Duong
 */
@Aspect
@Component
@Slf4j
public class AuthorizeValidatorInterceptor {
    @Autowired
    AuthHelper authHelper;

    @Before(value = "@annotation(com.digiex.spring.boot.demo.auth.AuthorizeValidator)  && @annotation(roles)")
    public void before(JoinPoint caller, AuthorizeValidator roles) {
        // Capture access token from current request
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();
        String token = request.getHeader(Constant.HEADER_TOKEN);
        // Check Get current Authen user from access token
        AuthUser authUser = authHelper.loadAuthUserFromAuthToken(token);
        if (authUser == null)
            throw new ApplicationException(APIStatus.UNAUTHORIZED);
        // Validate Role
        boolean isValid = isValidate(authUser, roles);
        if (!isValid)
            throw new ApplicationException(APIStatus.FORBIDDEN);
    }

    public boolean isValidate(AuthUser authUser, AuthorizeValidator roles) {
        for (SessionType role : roles.value()) {
            if(role == authUser.getRole())
                return true;
        }

        return false;
    }
}

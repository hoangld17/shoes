package com.digiex.spring.boot.demo.auth;

import com.digiex.spring.boot.demo.common.exception.ApplicationException;
import com.digiex.spring.boot.demo.common.util.Constant;
import com.digiex.spring.boot.demo.controller.model.APIStatus;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author DiGiEx
 */
@Component
public class AuthSessionResolver implements HandlerMethodArgumentResolver {

    private final AuthHelper authHelper;

    public AuthSessionResolver(AuthHelper authHelper) {
        this.authHelper = authHelper;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return (parameter.getParameterAnnotation(AuthSession.class) != null);
    }

    @Override
    public AuthUser resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory){

        AuthUser authUser = null;
        AuthSession authSession = parameter.getParameterAnnotation(AuthSession.class);

        if (authSession != null) {
            // get access token from request header
            String accessToken = webRequest.getHeader(Constant.HEADER_TOKEN);
            if (accessToken != null) {
                authUser = authHelper.loadAuthUserFromAuthToken(accessToken);

            } else if (authSession.required()) {
                throw new ApplicationException(APIStatus.UNAUTHORIZED);
            }
        }
        return authUser;
    }

}

package com.digiex.spring.boot.demo.auth;

import com.digiex.spring.boot.demo.common.enums.SessionType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Quy Duong
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthorizeValidator {
    SessionType[] value() default SessionType.USER;
}

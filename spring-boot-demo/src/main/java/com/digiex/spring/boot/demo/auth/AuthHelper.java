package com.digiex.spring.boot.demo.auth;

import com.digiex.spring.boot.demo.entity.Session;
import com.digiex.spring.boot.demo.common.exception.ApplicationException;
import com.digiex.spring.boot.demo.common.util.DateUtil;
import com.digiex.spring.boot.demo.common.validator.Validator;
import com.digiex.spring.boot.demo.controller.model.APIStatus;
import com.digiex.spring.boot.demo.entity.User;
import com.digiex.spring.boot.demo.service.SessionService;
import com.digiex.spring.boot.demo.service.UserService;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author DiGiEx
 */
@Component
public class AuthHelper {
    final SessionService sessionService;
    final UserService userService;

    public AuthHelper(SessionService sessionService, UserService userService) {
        this.sessionService = sessionService;
        this.userService = userService;
    }

    public AuthUser loadAuthUserFromAuthToken(String authToken) {
        Session session = sessionService.findById(authToken);
        Validator.notNull(session, APIStatus.UNAUTHORIZED, "");
        // Check expired date
        if (DateUtil.convertToUTC(new Date()).getTime() >= session.getExpiryDate().getTime()) {
            throw new ApplicationException(APIStatus.UNAUTHORIZED);
        }
        User user = userService.getUserById(session.getId());
        Validator.notNull(user, APIStatus.UNAUTHORIZED);

        return new AuthUser(user);
    }

}

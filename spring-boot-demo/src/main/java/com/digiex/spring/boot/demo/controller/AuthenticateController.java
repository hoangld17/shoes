package com.digiex.spring.boot.demo.controller;

import com.digiex.spring.boot.demo.auth.AuthSession;
import com.digiex.spring.boot.demo.auth.AuthUser;
import com.digiex.spring.boot.demo.common.exception.ApplicationException;
import com.digiex.spring.boot.demo.common.util.Constant;
import com.digiex.spring.boot.demo.common.validator.Validator;
import com.digiex.spring.boot.demo.controller.model.APIStatus;
import com.digiex.spring.boot.demo.controller.model.RestAPIResponse;
import com.digiex.spring.boot.demo.controller.model.request.UserLoginRequest;
import com.digiex.spring.boot.demo.entity.Session;
import com.digiex.spring.boot.demo.entity.User;
import com.digiex.spring.boot.demo.helper.SessionHelper;
import com.digiex.spring.boot.demo.service.SessionService;
import com.digiex.spring.boot.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Quy Duong
 */
@Controller
@RequestMapping(APIName.AUTHENTICATE_API)
public class AuthenticateController extends AbstractBaseController {

    final SessionService sessionService;
    final UserService userService;
    final PasswordEncoder passwordEncoder;
    final SessionHelper sessionHelper;

    public AuthenticateController(SessionService sessionService, UserService userService, PasswordEncoder passwordEncoder,
                                  SessionHelper sessionHelper) {
        this.sessionService = sessionService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.sessionHelper = sessionHelper;
    }


    /** Login student
     *
     * @param request
     * @return
     */
    @RequestMapping(path = APIName.AUTHENTICATE_LOGIN, method = RequestMethod.POST)
    public ResponseEntity<RestAPIResponse> login(
            @RequestBody UserLoginRequest request
    ) {
        // validate user existed
        User user = userService.getUserByUsernameOrEmail(request.getLoginId(), request.getLoginId());
        Validator.notNull(user, APIStatus.NOT_FOUND, "Student does not existed");

        // validate password
        if (!passwordEncoder.matches(request.getPasswordHash().trim().concat(user.getPasswordSalt()), user.getPasswordHash())) {
            throw new ApplicationException(APIStatus.FORBIDDEN, "Wrong password");
        }

        Session session = sessionHelper.createSession(user);
        sessionService.save(session);
        return responseUtil.successResponse(session);
    }

    /**
     * Get User Authenticate Information
     *
     * @param authUser
     * @return
     */
    @RequestMapping(path = APIName.AUTHENTICATE_INFO, method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getAuthInfo(
            @AuthSession AuthUser authUser
    ) {
        // return auth user info
        return responseUtil.successResponse(authUser);
    }

    /**
     * Logout API
     *
     * @param request
     * @return
     */
    @RequestMapping(path = APIName.AUTHENTICATE_LOGOUT, method = RequestMethod.POST)
    public ResponseEntity<RestAPIResponse> logout(HttpServletRequest request) {
        String authToken = request.getHeader(Constant.HEADER_TOKEN);
        Session session = sessionService.findById(authToken);
        if (session != null) {
            sessionService.delete(session);
        }
        return responseUtil.successResponse("Logout Successfully");
    }

}

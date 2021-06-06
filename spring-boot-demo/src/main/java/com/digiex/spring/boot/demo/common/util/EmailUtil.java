package com.digiex.spring.boot.demo.common.util;

import com.digiex.spring.boot.demo.common.exception.ApplicationException;
import com.digiex.spring.boot.demo.controller.model.APIStatus;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.regex.Pattern;

public class EmailUtil {

    public static void checkEmailFormat(String email) {
        if (!EmailValidator.getInstance().isValid(email))
            throw new ApplicationException(APIStatus.BAD_PARAMS, "Email format is wrong");
    }

}

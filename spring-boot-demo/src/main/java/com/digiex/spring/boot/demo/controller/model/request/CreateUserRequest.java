package com.digiex.spring.boot.demo.controller.model.request;

import com.digiex.spring.boot.demo.common.exception.ApplicationException;
import com.digiex.spring.boot.demo.controller.model.APIStatus;
import com.digiex.spring.boot.demo.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.regex.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    @NotNull
    @NotBlank
    private String email;
    @NotNull
    @NotBlank
    private String passwordHash;
    @NotNull
    @NotBlank
    private String username;
    @NotNull
    @NotBlank
    private String firstName;
    @NotNull
    @NotBlank
    private String lastName;
    private String lang;
    public void checkNullAndEmptyValue(){
        if (username == null)
            throw new ApplicationException(APIStatus.BAD_PARAMS, "Username is null");
        if (email == null)
            throw new ApplicationException(APIStatus.BAD_PARAMS, "Email is null");
        if (firstName == null)
            throw new ApplicationException(APIStatus.BAD_PARAMS, "First name is null");
        if (lastName == null)
            throw new ApplicationException(APIStatus.BAD_PARAMS, "Last name is null");
        if (lang == null)
            throw new ApplicationException(APIStatus.BAD_PARAMS, "Lang is null");
        if (passwordHash == null)
            throw new ApplicationException(APIStatus.BAD_PARAMS, "Password hash is null");
        if (username.isEmpty())
            throw new ApplicationException(APIStatus.BAD_PARAMS, "Username is empty");
        if (email.isEmpty())
            throw new ApplicationException(APIStatus.BAD_PARAMS, "Email is empty");
        if (firstName.isEmpty())
            throw new ApplicationException(APIStatus.BAD_PARAMS, "First name is empty");
        if (lastName.isEmpty())
            throw new ApplicationException(APIStatus.BAD_PARAMS, "Last name is empty");
        if (lang.isEmpty())
            throw new ApplicationException(APIStatus.BAD_PARAMS, "Lang is empty");
        if (passwordHash.isEmpty())
            throw new ApplicationException(APIStatus.BAD_PARAMS, "Password hash is empty");
    }
    public void checkLengthValue(){
        if (username.length() > 100)
            throw new ApplicationException(APIStatus.BAD_PARAMS, "Username is too long");
        if (email.length() > 255)
            throw new ApplicationException(APIStatus.BAD_PARAMS, "Email is too long");
        if (firstName.length() > 100)
            throw new ApplicationException(APIStatus.BAD_PARAMS, "First name is too long");
        if (lastName.length() > 100)
            throw new ApplicationException(APIStatus.BAD_PARAMS, "Last name is too long");
        if (lang.length() > 5)
            throw new ApplicationException(APIStatus.BAD_PARAMS, "Lang is too long");
        if (passwordHash.length() > 100)
            throw new ApplicationException(APIStatus.BAD_PARAMS, "Password hash is too long");
    }
    public void checkEmailFormat() {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        if (!pattern.matcher(email).matches())
            throw new ApplicationException(APIStatus.BAD_PARAMS, "Email format is wrong");
    }
    public void checkExistenceUsernameOrEmail(UserService userService) {
        if (userService.existsByUsername(username))
            throw new ApplicationException(APIStatus.BAD_PARAMS, "Username already exists");
        if (userService.existsByEmail(email))
            throw new ApplicationException(APIStatus.BAD_PARAMS, "Email already exists");
    }

}

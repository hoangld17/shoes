package com.digiex.spring.boot.demo.controller.model.request;

import com.digiex.spring.boot.demo.common.util.ParamError;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {

    @NotBlank(message = ParamError.FIELD_NAME)
    @Size(max = 255, message = ParamError.MAX_LENGTH)
    private String email;

    @NotBlank(message = ParamError.FIELD_NAME)
    @Size(max = 255, message = ParamError.MAX_LENGTH)
    private String passwordHash;

    @NotBlank(message = ParamError.FIELD_NAME)
    @Size(max = 64, message = ParamError.MAX_LENGTH)
    private String username;

    @NotBlank(message = ParamError.FIELD_NAME)
    @Size(max = 64, message = ParamError.MAX_LENGTH)
    private String firstName;

    @NotBlank(message = ParamError.FIELD_NAME)
    @Size(max = 64, message = ParamError.MAX_LENGTH)
    private String lastName;

    @NotBlank(message = ParamError.FIELD_NAME)
    @Size(max = 64, message = ParamError.MAX_LENGTH)
    private String lang;

    @Override
    public String toString() {
        return "CreateUserRequest{" +
                "email='" + email + '\'' +
                ", passwordHash='" + passwordHash + '\'' +
                ", username='" + username + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", lang='" + lang + '\'' +
                '}';
    }
}

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
public class UpdateUserRequest {

    private String email;

    private String username;

    private String firstName;

    private String lastName;

    private String lang;
}

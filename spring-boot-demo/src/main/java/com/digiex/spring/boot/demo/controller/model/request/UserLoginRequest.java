package com.digiex.spring.boot.demo.controller.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author DiGiEx
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginRequest {

    @NotNull
    @NotBlank
    private String loginId; // username or email

    @NotNull
    @NotBlank
    private String passwordHash;
}

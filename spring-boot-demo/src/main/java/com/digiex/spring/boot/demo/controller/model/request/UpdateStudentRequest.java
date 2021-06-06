package com.digiex.spring.boot.demo.controller.model.request;

import com.digiex.spring.boot.demo.common.enums.Gender;
import com.digiex.spring.boot.demo.common.util.ParamError;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Getter
@Setter
@NoArgsConstructor
public class UpdateStudentRequest {
    @Size(max = 64, message = ParamError.MAX_LENGTH)
    private String lastName;
    @Size(max = 64, message = ParamError.MAX_LENGTH)
    private String firstName;
    @Size(max = 64, message = ParamError.MAX_LENGTH)
    private String address;
    private Gender gender;
    private String dateOfBirth;
}

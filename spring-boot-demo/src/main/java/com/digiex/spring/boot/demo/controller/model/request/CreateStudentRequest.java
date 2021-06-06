package com.digiex.spring.boot.demo.controller.model.request;

import com.digiex.spring.boot.demo.common.enums.Gender;
import com.digiex.spring.boot.demo.common.util.Constant;
import com.digiex.spring.boot.demo.common.util.ParamError;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateStudentRequest {

    @NotNull(message = ParamError.FIELD_NAME)
    private Long clazzId;

    @NotBlank(message = ParamError.FIELD_NAME)
    @Size(max = 64, message = ParamError.MAX_LENGTH)
    private String lastName;
    @NotBlank(message = ParamError.FIELD_NAME)
    @Size(max = 64, message = ParamError.MAX_LENGTH)
    private String firstName;

    private String address;
    @NotNull(message = ParamError.FIELD_NAME)
    private Gender gender;
    @NotBlank(message = ParamError.FIELD_NAME)
    @Size(max = 64, message = ParamError.MAX_LENGTH)
    private String dateOfBirth;

    List<CreateStudentHistoryRequest> studentHistories;
}

package com.digiex.spring.boot.demo.controller.model.request;

import com.digiex.spring.boot.demo.common.util.ParamError;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateStudentHistoryRequest {

    @NotBlank(message = ParamError.FIELD_NAME)
    @Size(max = 64, message = ParamError.MAX_LENGTH)
    private String nameSchool;
    @NotBlank(message = ParamError.FIELD_NAME)
    @Size(max = 64, message = ParamError.MAX_LENGTH)
    private String address;
    @NotBlank(message = ParamError.FIELD_NAME)
    @Size(max = 64, message = ParamError.MAX_LENGTH)
    private String startDate;
    @NotBlank(message = ParamError.FIELD_NAME)
    @Size(max = 64, message = ParamError.MAX_LENGTH)
    private String endDate;
}

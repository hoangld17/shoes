package com.digiex.spring.boot.demo.controller.model.request;

import com.digiex.spring.boot.demo.common.util.ParamError;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class UpdateStudentHistoryRequest {
    @Size(max = 64, message = ParamError.MAX_LENGTH)
    private String nameSchool;
    @Size(max = 64, message = ParamError.MAX_LENGTH)
    private String address;
    @Size(max = 64, message = ParamError.MAX_LENGTH)
    private String startDate;
    @Size(max = 64, message = ParamError.MAX_LENGTH)
    private String endDate;
}

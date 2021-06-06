package com.digiex.spring.boot.demo.controller.model.request;

import com.digiex.spring.boot.demo.common.util.ParamError;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateClazzRequest {

    @NotBlank(message = ParamError.FIELD_NAME)
    @Size(max = 64, message = ParamError.MAX_LENGTH)
    private String name;

}

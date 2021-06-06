package com.digiex.spring.boot.demo.controller.model.response;

import com.digiex.spring.boot.demo.common.enums.AppStatus;
import com.digiex.spring.boot.demo.common.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor
public class StudentResponse {
    private Long id;
    private Long clazzId;
    private String lastName;
    private String firstName;
    private String address;
    private Gender gender;
    private Date dateOfBirth;
    private AppStatus status;
    List<StudentHistoryResponse> studentHistoryResponses;
}

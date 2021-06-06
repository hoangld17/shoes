package com.digiex.spring.boot.demo.controller.model.response;

import com.digiex.spring.boot.demo.common.enums.AppStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class StudentHistoryResponse {
    private Long id;
    private Long studentId;
    private String nameSchool;
    private String address;
    private Date startDate;
    private Date endDate;
    private AppStatus status;
    private Date createdDate;
    private Date updatedDate;
}

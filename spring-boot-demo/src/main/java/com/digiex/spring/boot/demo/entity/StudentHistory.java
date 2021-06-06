package com.digiex.spring.boot.demo.entity;

import com.digiex.spring.boot.demo.common.enums.AppStatus;
import com.digiex.spring.boot.demo.common.exception.ApplicationException;
import com.digiex.spring.boot.demo.common.util.Constant;
import com.digiex.spring.boot.demo.common.validator.Validator;
import com.digiex.spring.boot.demo.controller.model.APIStatus;
import com.digiex.spring.boot.demo.controller.model.ParamError;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.apache.commons.validator.routines.DateValidator;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "student_history")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ToString
public class StudentHistory extends AuditableDomain implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long studentId;
    private String nameSchool;
    private String address;
    private Date startDate;
    private Date endDate;
    private AppStatus status;

    public void setNameSchool(String nameSchool) {
        Validator.checkNullEmptyAndLength(nameSchool, 64, "nameSchool");
        this.nameSchool = nameSchool;
    }

    public void setAddress(String address) {
        Validator.checkNullEmptyAndLength(address, 64, "address");
        this.address = address;
    }

    public void setStartDate(String startDate) {
        this.startDate = Validator.convertDate(startDate,"startDate");
    }

    public void setEndDate(String endDate) {
        this.endDate = Validator.convertDate(endDate, "endDate");
    }
}

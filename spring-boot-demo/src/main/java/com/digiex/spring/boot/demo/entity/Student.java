package com.digiex.spring.boot.demo.entity;

import com.digiex.spring.boot.demo.common.enums.AppStatus;
import com.digiex.spring.boot.demo.common.enums.Gender;
import com.digiex.spring.boot.demo.common.exception.ApplicationException;
import com.digiex.spring.boot.demo.common.util.Constant;
import com.digiex.spring.boot.demo.common.validator.Validator;
import com.digiex.spring.boot.demo.controller.model.APIStatus;
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
@Table(name = "student")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ToString
public class Student extends AuditableDomain implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long clazzId;
    private String lastName;
    private String firstName;
    private String address;
    private Gender gender;
    private Date dateOfBirth;
    private AppStatus status;

    public void setLastName(String lastName) {
        Validator.checkNullEmptyAndLength(lastName, 64, "lastName");
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        Validator.checkNullEmptyAndLength(firstName, 64, "firstName");
        this.firstName = firstName;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = Validator.convertDate(dateOfBirth, "dateOfBirth");
    }

    public void setAddress(String address) {
        Validator.checkNullEmptyAndLength(address, 64, "address");
        this.address = address;
    }
}

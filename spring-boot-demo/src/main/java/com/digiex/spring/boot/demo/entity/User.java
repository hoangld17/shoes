/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digiex.spring.boot.demo.entity;

import com.digiex.spring.boot.demo.common.enums.AppStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Quy Duong
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "user")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class User extends AuditableDomain implements Serializable {
    
    @Id
    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwordHash;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String passwordSalt;
    private String lang;
    private AppStatus status;
    private Date createdDate;
    @OneToMany(mappedBy = "user")
    private List<Address> addresses;

//    @Override
//    public String toString() {
//        return "User{" +
//                "id='" + id + '\'' +
//                ", username='" + username + '\'' +
//                ", email='" + email + '\'' +
//                ", firstName='" + firstName + '\'' +
//                ", lastName='" + lastName + '\'' +
//                ", passwordHash='" + passwordHash + '\'' +
//                ", passwordSalt='" + passwordSalt + '\'' +
//                ", lang='" + lang + '\'' +
//                ", status=" + status +
//                ", createdDate=" + createdDate +
//                '}';
//    }
}

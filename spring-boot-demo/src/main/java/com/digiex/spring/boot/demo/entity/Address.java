package com.digiex.spring.boot.demo.entity;

import com.digiex.spring.boot.demo.common.enums.SessionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@EntityListeners(AuditingEntityListener.class)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address")
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String address;
    @Column(length = 50)
    private String phone;
    @Column(length = 50)
    private String fax;
    @Column(length = 50)
    private String city;
    @Column(length = 50)
    private String country;
    @ManyToOne
    @JoinColumn(name = "fk_user")
    private User user;
    public String getUser() {
        return user.getId();
    }
}

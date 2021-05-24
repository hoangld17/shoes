package com.digiex.spring.boot.demo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.digiex.spring.boot.demo.common.enums.SessionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author DiGiEx
 */
@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
@EntityListeners(AuditingEntityListener.class)
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "session")
public class Session implements Serializable {

    @Id
    private String id;
    private String userId;
    private Date createdDate;
    private Date expiryDate;
    @Enumerated(EnumType.STRING)
    private SessionType type;
}

package com.digiex.spring.boot.demo.entity;

import com.digiex.spring.boot.demo.common.util.*;
import com.fasterxml.jackson.annotation.*;
import lombok.*;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Data
@ToString
@MappedSuperclass
@JsonInclude(JsonInclude.Include.NON_NULL)
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableDomain implements Serializable {

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.API_FORMAT_DATE)
    protected Date createdDate;

    @LastModifiedDate
    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Constant.API_FORMAT_DATE)
    protected Date updatedDate;

    @PrePersist
    protected void onCreate() {
        this.createdDate = DateUtil.convertToUTC(new Date());
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedDate = DateUtil.convertToUTC(new Date());
    }

}

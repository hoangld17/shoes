package com.digiex.spring.boot.demo.repository;

import com.digiex.spring.boot.demo.common.enums.AppStatus;
import com.digiex.spring.boot.demo.entity.Clazz;
import com.digiex.spring.boot.demo.entity.StudentHistory;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClazzRepository extends PagingAndSortingRepository<Clazz, Long>, JpaSpecificationExecutor<Clazz> {
    Clazz findOneByIdAndStatus(Long id, AppStatus appStatus);

    List<Clazz> findAllByStatus(AppStatus appStatus);
}

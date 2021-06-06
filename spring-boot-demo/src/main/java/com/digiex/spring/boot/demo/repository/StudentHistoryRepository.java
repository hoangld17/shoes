package com.digiex.spring.boot.demo.repository;

import com.digiex.spring.boot.demo.common.enums.AppStatus;
import com.digiex.spring.boot.demo.entity.StudentHistory;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface StudentHistoryRepository extends PagingAndSortingRepository<StudentHistory, Long>, JpaSpecificationExecutor<StudentHistory> {
    StudentHistory findOneByIdAndStatus(Long id, AppStatus appStatus);
    List<StudentHistory> findAllByStudentIdAndStatus(Long id, AppStatus appStatus);
    List<StudentHistory> findAllByStatus(AppStatus appStatus);
}

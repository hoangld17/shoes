package com.digiex.spring.boot.demo.repository;

import com.digiex.spring.boot.demo.common.enums.AppStatus;
import com.digiex.spring.boot.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {
    Student findOneByIdAndStatus(Long id, AppStatus appStatus);
    List<Student> findAllByClazzIdAndStatus(Long id, AppStatus appStatus);
    List<Student> findAllByStatus(AppStatus appStatus);
}

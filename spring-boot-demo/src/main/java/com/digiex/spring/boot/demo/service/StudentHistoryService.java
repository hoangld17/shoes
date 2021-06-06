package com.digiex.spring.boot.demo.service;

import com.digiex.spring.boot.demo.common.enums.AppStatus;
import com.digiex.spring.boot.demo.controller.model.request.CreateStudentHistoryRequest;
import com.digiex.spring.boot.demo.controller.model.request.UpdateStudentHistoryRequest;
import com.digiex.spring.boot.demo.entity.Clazz;
import com.digiex.spring.boot.demo.entity.Student;
import com.digiex.spring.boot.demo.entity.StudentHistory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional
@Service
public interface StudentHistoryService {
    StudentHistory saveStudentHistory(StudentHistory StudentHistory);

    StudentHistory getStudentHistoryById(Long id);

    List<StudentHistory> getAllStudentHistory();

    StudentHistory addStudentHistory(CreateStudentHistoryRequest createStudentHistoryRequest);

    StudentHistory updateStudentHistory(Long id, UpdateStudentHistoryRequest updateStudentHistoryRequest);

    List<StudentHistory> findAllByStudentId(Long id);

    StudentHistory deleteStudentHistory(Long id);

    Page<StudentHistory> getPagingStudentHistory(String nameSchool, String address, String startDate, String endDate, String search, int page, int size, boolean sort, String sortField);
}

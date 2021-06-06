package com.digiex.spring.boot.demo.service;

import com.digiex.spring.boot.demo.common.enums.AppStatus;
import com.digiex.spring.boot.demo.common.enums.Gender;
import com.digiex.spring.boot.demo.controller.model.request.CreateStudentRequest;
import com.digiex.spring.boot.demo.controller.model.request.UpdateStudentRequest;
import com.digiex.spring.boot.demo.controller.model.response.StudentResponse;
import com.digiex.spring.boot.demo.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface StudentService {


    Student saveStudent(Student Student);

    Student getStudentById(Long id);

    StudentResponse getStudentResponseById(Long id);

    List<Student> getAllStudent();

    StudentResponse addStudent(CreateStudentRequest createStudentRequest);

    Student updateStudent(Long id, UpdateStudentRequest updateStudentRequest);

    Page<StudentResponse> getPagingStudent(String firstName, String lastName, String address, String dateOfBirth, Gender gender, String search, int page, int size, boolean sort, String sortField);

    Student deleteStudent(Long id);

    List<Student> findAllByClazzId(Long id);
}

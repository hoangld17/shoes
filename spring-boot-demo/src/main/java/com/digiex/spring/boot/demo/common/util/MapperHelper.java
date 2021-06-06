package com.digiex.spring.boot.demo.common.util;

import com.digiex.spring.boot.demo.common.enums.AppStatus;
import com.digiex.spring.boot.demo.controller.model.request.CreateStudentHistoryRequest;
import com.digiex.spring.boot.demo.controller.model.request.CreateStudentRequest;
import com.digiex.spring.boot.demo.controller.model.request.UpdateStudentHistoryRequest;
import com.digiex.spring.boot.demo.controller.model.request.UpdateStudentRequest;
import com.digiex.spring.boot.demo.entity.Student;
import com.digiex.spring.boot.demo.entity.StudentHistory;

public class MapperHelper {
    public static Student mapEntity(CreateStudentRequest createStudentRequest){
        Student student = new Student();
        student.setFirstName(createStudentRequest.getFirstName());
        student.setLastName(createStudentRequest.getLastName());
        student.setAddress(createStudentRequest.getAddress());
        student.setClazzId(createStudentRequest.getClazzId());
        student.setGender(createStudentRequest.getGender());
        student.setDateOfBirth(createStudentRequest.getDateOfBirth());
        student.setStatus(AppStatus.ACTIVE);
        return student;
    }
    public static StudentHistory mapEntity(CreateStudentHistoryRequest createStudentHistoryRequest){
        StudentHistory studentHistory = new StudentHistory();
        studentHistory.setNameSchool(createStudentHistoryRequest.getNameSchool());
        studentHistory.setAddress(createStudentHistoryRequest.getAddress());
        studentHistory.setStartDate(createStudentHistoryRequest.getStartDate());
        studentHistory.setEndDate(createStudentHistoryRequest.getEndDate());
        studentHistory.setStatus(AppStatus.ACTIVE);
        return studentHistory;
    }

    public static void mapEntity(UpdateStudentHistoryRequest updateStudentHistoryRequest, StudentHistory studentHistory){
        if (updateStudentHistoryRequest.getNameSchool() != null && !updateStudentHistoryRequest.getNameSchool().isBlank())
            studentHistory.setNameSchool(updateStudentHistoryRequest.getNameSchool());
        if (updateStudentHistoryRequest.getAddress() != null && !updateStudentHistoryRequest.getAddress().isBlank())
            studentHistory.setAddress(updateStudentHistoryRequest.getAddress());
        if (updateStudentHistoryRequest.getStartDate() != null && !updateStudentHistoryRequest.getStartDate().isBlank())
            studentHistory.setStartDate(updateStudentHistoryRequest.getStartDate());
        if (updateStudentHistoryRequest.getEndDate() != null && !updateStudentHistoryRequest.getEndDate().isBlank())
            studentHistory.setEndDate(updateStudentHistoryRequest.getEndDate());
    }
    public static void mapEntity(UpdateStudentRequest updateStudentRequest, Student student){
        if (updateStudentRequest.getFirstName() != null && !updateStudentRequest.getFirstName().isBlank()) {
            student.setFirstName(updateStudentRequest.getFirstName());
        }
        if (updateStudentRequest.getLastName() != null && !updateStudentRequest.getLastName().isBlank()) {
            student.setLastName(updateStudentRequest.getLastName());
        }
        if (updateStudentRequest.getAddress() != null && !updateStudentRequest.getAddress().isBlank()) {
            student.setAddress(updateStudentRequest.getAddress());
        }
        if (updateStudentRequest.getGender() != null) {
            student.setGender(updateStudentRequest.getGender());
        }
        if (updateStudentRequest.getDateOfBirth() != null && !updateStudentRequest.getDateOfBirth().isBlank()) {
            student.setDateOfBirth(updateStudentRequest.getDateOfBirth());
        }
    }
}

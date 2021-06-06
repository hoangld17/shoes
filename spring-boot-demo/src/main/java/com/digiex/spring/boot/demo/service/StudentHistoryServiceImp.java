package com.digiex.spring.boot.demo.service;

import com.digiex.spring.boot.demo.common.enums.AppStatus;
import com.digiex.spring.boot.demo.common.exception.ApplicationException;
import com.digiex.spring.boot.demo.common.util.MapperHelper;
import com.digiex.spring.boot.demo.common.validator.Validator;
import com.digiex.spring.boot.demo.controller.model.APIStatus;
import com.digiex.spring.boot.demo.controller.model.request.CreateStudentHistoryRequest;
import com.digiex.spring.boot.demo.controller.model.request.UpdateStudentHistoryRequest;
import com.digiex.spring.boot.demo.entity.StudentHistory;
import com.digiex.spring.boot.demo.repository.StudentHistoryRepository;
import com.digiex.spring.boot.demo.repository.StudentHistorySpecification;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
@Service
@Transactional
public class StudentHistoryServiceImp implements StudentHistoryService{
    @Autowired
    StudentHistoryRepository studentHistoryRepository;
    @Autowired
    StudentService studentService;
    @Autowired
    StudentHistorySpecification studentHistorySpecification;

    @Override
    public StudentHistory saveStudentHistory(StudentHistory studentHistory) {
        studentService.getStudentById(studentHistory.getStudentId());
        return studentHistoryRepository.save(studentHistory);
    }

    @Override
    public StudentHistory getStudentHistoryById(Long id) {
        StudentHistory studentHistory = studentHistoryRepository.findOneByIdAndStatus(id, AppStatus.ACTIVE);
        if (studentHistory == null)
            throw new ApplicationException(APIStatus.NO_RESULT, "The student history id does not exist.");
        return studentHistory;
    }

    @Override
    public List<StudentHistory> getAllStudentHistory() {
        return studentHistoryRepository.findAllByStatus(AppStatus.ACTIVE);
    }

    @Override
    public StudentHistory addStudentHistory(CreateStudentHistoryRequest createStudentHistoryRequest) {
        StudentHistory studentHistory = MapperHelper.mapEntity(createStudentHistoryRequest);
        if (studentHistory.getStartDate().after(studentHistory.getEndDate()))
            throw new ApplicationException(APIStatus.BAD_PARAMS, "Start date must be before end date!");
        return saveStudentHistory(studentHistory);
    }

    @Override
    public StudentHistory updateStudentHistory(Long id, UpdateStudentHistoryRequest updateStudentHistoryRequest) {
        StudentHistory studentHistory = getStudentHistoryById(id);
        MapperHelper.mapEntity(updateStudentHistoryRequest, studentHistory);
        if (studentHistory.getStartDate().after(studentHistory.getEndDate()))
            throw new ApplicationException(APIStatus.BAD_PARAMS, "Start date must be before end date!");
        return saveStudentHistory(studentHistory);
    }

    @Override
    public List<StudentHistory> findAllByStudentId(Long id) {
        studentService.getStudentById(id);
        return studentHistoryRepository.findAllByStudentIdAndStatus(id, AppStatus.ACTIVE);
    }

    @Override
    public StudentHistory deleteStudentHistory(Long id) {
        StudentHistory studentHistory = getStudentHistoryById(id);
        studentHistory.setStatus(AppStatus.INACTIVE);
        return saveStudentHistory(studentHistory);
    }

    @Override
    public Page<StudentHistory> getPagingStudentHistory(String nameSchool, String address, String startDate, String endDate, String search, int page, int size, boolean sort, String sortField) {
        Date startDateNew = startDate == null || startDate.isBlank() ? null : Validator.convertDate(startDate, "startDate");
        Date endDateNew = endDate == null || endDate.isBlank() ? null : Validator.convertDate(endDate, "endDate");
        Specification<StudentHistory> specification = studentHistorySpecification.doFilterStudentHistory(nameSchool, address, startDateNew, endDateNew, search, sort, sortField);
        Pageable pageable = PageRequest.of(page - 1, size);
        return studentHistoryRepository.findAll(specification, pageable);
    }


}

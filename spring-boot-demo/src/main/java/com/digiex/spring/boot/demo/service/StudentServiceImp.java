package com.digiex.spring.boot.demo.service;

import com.digiex.spring.boot.demo.common.enums.AppStatus;
import com.digiex.spring.boot.demo.common.enums.Gender;
import com.digiex.spring.boot.demo.common.exception.ApplicationException;
import com.digiex.spring.boot.demo.common.util.MapperHelper;
import com.digiex.spring.boot.demo.common.validator.Validator;
import com.digiex.spring.boot.demo.controller.model.APIStatus;
import com.digiex.spring.boot.demo.controller.model.request.CreateStudentRequest;
import com.digiex.spring.boot.demo.controller.model.request.UpdateStudentRequest;
import com.digiex.spring.boot.demo.controller.model.response.StudentHistoryResponse;
import com.digiex.spring.boot.demo.controller.model.response.StudentResponse;
import com.digiex.spring.boot.demo.entity.Student;
import com.digiex.spring.boot.demo.entity.StudentHistory;
import com.digiex.spring.boot.demo.repository.StudentRepository;
import com.digiex.spring.boot.demo.repository.StudentSpecification;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentServiceImp implements StudentService{
    @Autowired
    private ClazzService clazzService;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentHistoryService studentHistoryService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private StudentSpecification studentSpecification;

    @Override
    public Student saveStudent(Student student) {
        clazzService.getClazzById(student.getClazzId());
        return studentRepository.save(student);
    }

    @Override
    public Student getStudentById(Long id) {
        Student student = studentRepository.findOneByIdAndStatus(id, AppStatus.ACTIVE);
        if (student == null)
            throw new ApplicationException(APIStatus.NO_RESULT, "The student id does not exist.");
        return student;
    }

    @Override
    public StudentResponse getStudentResponseById(Long id) {
        StudentResponse studentResponse = modelMapper.map(getStudentById(id), StudentResponse.class);
        studentResponse.setStudentHistoryResponses(studentHistoryService.findAllByStudentId(studentResponse.getId()).stream().map(x -> modelMapper.map(x, StudentHistoryResponse.class)).collect(Collectors.toList()));
        return studentResponse;
    }

    @Override
    public List<Student> getAllStudent() {
        return studentRepository.findAllByStatus(AppStatus.ACTIVE);
    }

    @Override
    public StudentResponse addStudent(CreateStudentRequest createStudentRequest) {
        Student student = saveStudent(MapperHelper.mapEntity(createStudentRequest));
        List<StudentHistoryResponse> studentHistoryResponses = new ArrayList<>();

        if (createStudentRequest.getStudentHistories() != null && !createStudentRequest.getStudentHistories().isEmpty()) {
            createStudentRequest.getStudentHistories().forEach(x -> {
                StudentHistory studentHistory = MapperHelper.mapEntity(x);
                studentHistory.setStudentId(student.getId());
                studentHistoryResponses.add(modelMapper.map(studentHistoryService.saveStudentHistory(studentHistory), StudentHistoryResponse.class));
            });
        }
        StudentResponse studentResponse = modelMapper.map(student, StudentResponse.class);
        studentResponse.setStudentHistoryResponses(studentHistoryResponses);
        return studentResponse;
    }

    @Override
    public Student updateStudent(Long id, UpdateStudentRequest updateStudentRequest) {
        Student student = getStudentById(id);
        MapperHelper.mapEntity(updateStudentRequest, student);
        return saveStudent(student);
    }

    @Override
    public Page<StudentResponse> getPagingStudent(String firstName, String lastName, String address, String dateOfBirth, Gender gender, String search, int page, int size, boolean sort, String sortField) {
        Date newDate = dateOfBirth == null || dateOfBirth.isBlank() ? null : Validator.convertDate(dateOfBirth, "date of birth");
        Specification specification = studentSpecification.doFilterStudent(firstName, lastName, address, newDate, gender, search, sort, sortField);
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Student> students = studentRepository.findAll(specification, pageable);
        return students.map(x -> {
            StudentResponse studentResponse = modelMapper.map(x, StudentResponse.class);
            studentResponse.setStudentHistoryResponses(studentHistoryService.findAllByStudentId(x.getId()).stream().map(y -> modelMapper.map(y, StudentHistoryResponse.class)).collect(Collectors.toList()));
            return studentResponse;
        });
    }

    @Override
    public Student deleteStudent(Long id) {
        Student student = getStudentById(id);
        if (studentHistoryService.findAllByStudentId(student.getId()).isEmpty())
            student.setStatus(AppStatus.INACTIVE);
        else throw new ApplicationException(APIStatus.BAD_REQUEST, "Can not delete student!");
        return saveStudent(student);
    }

    @Override
    public List<Student> findAllByClazzId(Long id) {
        clazzService.getClazzById(id);
        return studentRepository.findAllByClazzIdAndStatus(id, AppStatus.ACTIVE);
    }


}

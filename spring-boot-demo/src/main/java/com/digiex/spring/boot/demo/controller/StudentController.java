package com.digiex.spring.boot.demo.controller;

import com.digiex.spring.boot.demo.common.enums.AppStatus;
import com.digiex.spring.boot.demo.common.enums.Gender;
import com.digiex.spring.boot.demo.common.exception.ApplicationException;
import com.digiex.spring.boot.demo.common.util.MapperHelper;
import com.digiex.spring.boot.demo.common.validator.StudentHistoryValidator;
import com.digiex.spring.boot.demo.controller.model.APIStatus;
import com.digiex.spring.boot.demo.controller.model.RestAPIResponse;
import com.digiex.spring.boot.demo.controller.model.request.CreateClazzRequest;
import com.digiex.spring.boot.demo.controller.model.request.CreateStudentHistoryRequest;
import com.digiex.spring.boot.demo.controller.model.request.CreateStudentRequest;
import com.digiex.spring.boot.demo.controller.model.request.UpdateStudentRequest;
import com.digiex.spring.boot.demo.entity.Clazz;
import com.digiex.spring.boot.demo.entity.Student;
import com.digiex.spring.boot.demo.service.StudentService;
import com.digiex.spring.boot.demo.service.StudentServiceImp;
import lombok.AllArgsConstructor;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(APIName.STUDENT_API)
@AllArgsConstructor
public class StudentController extends AbstractBaseController {

    private StudentService studentService;

    @RequestMapping(path = APIName.GET_LIST, method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getAllUser(
    ){
        List<Student> students = studentService.getAllStudent();
        return responseUtil.successResponse(students) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestAPIResponse> getStudentById(
            @PathVariable("id") Long id
    ) {
        return responseUtil.successResponse(studentService.getStudentResponseById(id));
    }

    @DeleteMapping
    public ResponseEntity<RestAPIResponse> deleteStudent(
            @PathVariable("id") Long id
    ) {
        return responseUtil.successResponse(studentService.deleteStudent(id));
    }
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<RestAPIResponse> createStudent(
            @Valid @RequestBody CreateStudentRequest createStudentRequest
    ) {
        return responseUtil.successResponse(studentService.addStudent(createStudentRequest));
    }
    @PutMapping("/{id}")
    public ResponseEntity<RestAPIResponse> updateStudent(
            @PathVariable("id") Long id,
            @Valid @RequestBody UpdateStudentRequest UpdateStudentRequest
    ) {
        return responseUtil.successResponse(studentService.updateStudent(id, UpdateStudentRequest));
    }

    @GetMapping("/paging")
    public ResponseEntity<RestAPIResponse> getPagingStudent(
            @RequestParam(value = "first_name", required = false) String firstName,
            @RequestParam(value = "last_name", required = false) String lastName,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "date_of_birth", required = false) String dateOfBirth,
            @RequestParam(value = "gender", required = false) Gender gender,
            @RequestParam(value = "search", required = false, defaultValue = "") String search,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort", required = false, defaultValue = "false") boolean sort,
            @RequestParam(value = "sort_field", required = false, defaultValue = "") String sortField
    ) {
        return responseUtil.successResponse(studentService.getPagingStudent(firstName, lastName, address,dateOfBirth, gender,search, page, size, sort, sortField));
    }
}



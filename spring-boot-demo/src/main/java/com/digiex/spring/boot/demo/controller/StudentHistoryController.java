package com.digiex.spring.boot.demo.controller;

import com.digiex.spring.boot.demo.controller.model.RestAPIResponse;
import com.digiex.spring.boot.demo.controller.model.request.CreateStudentHistoryRequest;
import com.digiex.spring.boot.demo.controller.model.request.UpdateStudentHistoryRequest;
import com.digiex.spring.boot.demo.service.StudentHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping(APIName.STUDENT_HISTORY_API)
@AllArgsConstructor
public class StudentHistoryController extends AbstractBaseController {
    private StudentHistoryService studentHistoryService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<RestAPIResponse> createStudentHistory(
            @Valid @RequestBody CreateStudentHistoryRequest createStudentHistoryRequest
    ) {
        return responseUtil.successResponse(studentHistoryService.addStudentHistory(createStudentHistoryRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestAPIResponse> updateStudentHistory(
            @PathVariable("id") Long id,
            @Valid @RequestBody UpdateStudentHistoryRequest updateStudentHistoryRequest
    ) {
        return responseUtil.successResponse(studentHistoryService.updateStudentHistory(id, updateStudentHistoryRequest));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestAPIResponse> getStudentHistoryById(
            @PathVariable("id") Long id
    ) {
        return responseUtil.successResponse(studentHistoryService.getStudentHistoryById(id));
    }

    @GetMapping("/paging")
    public ResponseEntity<RestAPIResponse> getPagingStudentHistory(
            @RequestParam(value = "nameSchool", required = false) String nameSchool,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "search", required = false, defaultValue = "") String search,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort", required = false, defaultValue = "false") boolean sort,
            @RequestParam(value = "sort_field", required = false, defaultValue = "") String sortField
    ) {
        return responseUtil.successResponse(studentHistoryService.getPagingStudentHistory(nameSchool, address, startDate, endDate, search, page, size, sort, sortField));
    }

    @RequestMapping(path = APIName.GET_LIST, method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getAllUser(
    ){
        return responseUtil.successResponse(studentHistoryService.getAllStudentHistory()) ;
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<RestAPIResponse> deleteStudentHistory(
            @PathVariable("id") Long id
    ) {
        return responseUtil.successResponse(studentHistoryService.deleteStudentHistory(id));
    }
}

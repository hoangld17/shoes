package com.digiex.spring.boot.demo.controller;

import com.digiex.spring.boot.demo.common.enums.AppStatus;
import com.digiex.spring.boot.demo.common.exception.ApplicationException;
import com.digiex.spring.boot.demo.controller.model.APIStatus;
import com.digiex.spring.boot.demo.controller.model.RestAPIResponse;
import com.digiex.spring.boot.demo.controller.model.request.CreateClazzRequest;
import com.digiex.spring.boot.demo.controller.model.request.UpdateClazzRequest;
import com.digiex.spring.boot.demo.controller.model.request.UpdateStudentRequest;
import com.digiex.spring.boot.demo.entity.Clazz;
import com.digiex.spring.boot.demo.entity.User;
import com.digiex.spring.boot.demo.service.ClazzService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(APIName.CLAZZ_API)
public class ClazzController extends AbstractBaseController {
    @Autowired
    ClazzService clazzService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<RestAPIResponse> createClazz(
          @Valid @RequestBody CreateClazzRequest createClazzRequest
    ) {
        Clazz clazz = new Clazz();
        clazz.setName(createClazzRequest.getName().trim());
        clazz.setStatus(AppStatus.ACTIVE);
        clazzService.saveClazz(clazz);
        return responseUtil.successResponse(clazz);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestAPIResponse> updateClazz(
            @PathVariable("id") Long id,
            @Valid @RequestBody UpdateClazzRequest updateClazzRequest
    ) {
        Clazz clazz = clazzService.getClazzById(id);
        if (updateClazzRequest.getName() != null && !updateClazzRequest.getName().isBlank()) {
            clazz.setName(updateClazzRequest.getName());
            clazz = clazzService.saveClazz(clazz);
        }
        return responseUtil.successResponse(clazz);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestAPIResponse> getClazzById(
            @PathVariable("id") Long id
    ) {
        return responseUtil.successResponse(clazzService.getClazzById(id));
    }

    @GetMapping("/paging")
    public ResponseEntity<RestAPIResponse> getPagingClazz(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "search", required = false, defaultValue = "") String search,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort", required = false, defaultValue = "false") boolean sort,
            @RequestParam(value = "sort_field", required = false, defaultValue = "") String sortField
    ) {
        return responseUtil.successResponse(clazzService.getPagingClazz(name, search, page, size, sort, sortField));
    }

    @RequestMapping(path = APIName.GET_LIST, method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getAllUser(
    ){
        List<Clazz> clazzList = clazzService.getAllClazz();
        return responseUtil.successResponse(clazzList) ;
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<RestAPIResponse> deleteClazz(
            @PathVariable("id") Long id
    ) {
        return responseUtil.successResponse(clazzService.deleteClazz(id));
    }

}

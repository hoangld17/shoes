/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digiex.spring.boot.demo.controller;

import com.digiex.spring.boot.demo.common.enums.AppStatus;
import com.digiex.spring.boot.demo.common.exception.ApplicationException;
import com.digiex.spring.boot.demo.common.util.Constant;
import com.digiex.spring.boot.demo.common.util.EmailUtil;
import com.digiex.spring.boot.demo.common.util.ParamError;
import com.digiex.spring.boot.demo.common.validator.Validator;
import com.digiex.spring.boot.demo.controller.model.APIStatus;
import com.digiex.spring.boot.demo.controller.model.RestAPIResponse;
import com.digiex.spring.boot.demo.controller.model.request.CreateUserRequest;
import com.digiex.spring.boot.demo.controller.model.request.UpdateUserRequest;
import com.digiex.spring.boot.demo.entity.User;
import com.digiex.spring.boot.demo.helper.UserHelper;
import com.digiex.spring.boot.demo.service.UserService;
import lombok.NonNull;
import org.apache.commons.validator.routines.DateValidator;
import org.apache.tomcat.util.bcel.Const;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.Console;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Quy Duong
 */
@RestController
@RequestMapping(APIName.USER_API)
public class UserController extends AbstractBaseController {

    final UserService userService;
    final UserHelper userHelper;

    public UserController(UserService userService, UserHelper userHelper) {
        this.userService = userService;
        this.userHelper = userHelper;
    }

//    ModelMapper modelMapper = new ModelMapper();

    /**
     * Create new User API
     *
     * @param createUserRequest
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<RestAPIResponse> createUser(
           @Valid @RequestBody CreateUserRequest createUserRequest
    ) {

        EmailUtil.checkEmailFormat(createUserRequest.getEmail());

        User emailExits = userService.getByEmail(createUserRequest.getEmail().trim());
        Validator.mustNull(emailExits, APIStatus.BAD_REQUEST, "Email already exists");

        User userNameExits = userService.getByUsername(createUserRequest.getUsername().trim());
        Validator.mustNull(userNameExits, APIStatus.BAD_REQUEST, "Username already exists");

        System.out.println(createUserRequest);
        User user = userHelper.createUser(createUserRequest);

        userService.saveUser(user);
        return responseUtil.successResponse(user);
    }

//    /**
//     * Get All Users API
//     * @return
//     */
    @RequestMapping(path = APIName.GET_LIST, method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getAllUser(
    ){
        List<User> users = userService.getAllUser();
        return responseUtil.successResponse(users) ;
    }

    @RequestMapping(path = APIName.GET_PAGE, method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getPageUser(
            @RequestParam(value = "search", required = false, defaultValue = "") String search,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort", required = false, defaultValue = "false") boolean sort,
            @RequestParam(value = "sort_field", required = false, defaultValue = "") String sortField
    ) {
        Page<User> userPage = userService.getUserPaging(search, page, size, sort, sortField);
        return responseUtil.successResponse(userPage);
    }

    /**
     * Get User Detail API
     *
     * @param id
     * @return
     */
    @RequestMapping(path = APIName.GET_DETAIL, method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getUser(
            @PathVariable(name = "id") @NotBlank(message = ParamError.FIELD_NAME) String id
    ) {
        User user = userService.getUserById(id);
        Validator.notNull(user, APIStatus.NOT_FOUND, " User not found");

        return responseUtil.successResponse(user);
    }

    /**
     * Update User API
     *
     * @param id
     * @return
     */
    @RequestMapping(path = APIName.UPDATE, method = RequestMethod.PUT)
    public ResponseEntity<RestAPIResponse> updateUser(
            @PathVariable(name = "id") @NotBlank(message = ParamError.FIELD_NAME) String id,
            @RequestBody UpdateUserRequest updateUserRequest
    ) {

        User user = userService.getUserById(id);
        Validator.notNull(user, APIStatus.NOT_FOUND, " User not found");

        if (updateUserRequest.getUsername() != null && !updateUserRequest.getUsername().isEmpty() && !updateUserRequest.getUsername().equals(user.getUsername())) {
            User userNameExits = userService.getByUsername(updateUserRequest.getUsername().trim());
            Validator.mustNull(userNameExits, APIStatus.BAD_REQUEST, "Username already exists");

            user.setUsername(updateUserRequest.getUsername());
        }

        if (updateUserRequest.getEmail() != null
                && !updateUserRequest.getEmail().isEmpty()
                && !updateUserRequest.getEmail().equals(user.getEmail())) {

            User emailExits = userService.getByEmail(updateUserRequest.getEmail().trim());
            Validator.mustNull(emailExits, APIStatus.BAD_REQUEST, "Email already exists");

            user.setEmail(updateUserRequest.getEmail());

        }

        if (updateUserRequest.getFirstName() != null && !updateUserRequest.getFirstName().isEmpty()) {
            user.setFirstName(updateUserRequest.getFirstName());
        }

        if (updateUserRequest.getLastName() != null && !updateUserRequest.getLastName().isEmpty()) {
            user.setLastName(updateUserRequest.getLastName());
        }

        if (updateUserRequest.getLang() != null && !updateUserRequest.getLang().isEmpty()) {
            user.setLang(updateUserRequest.getLang());
        }

        userService.saveUser(user);
        // TODO Update
        return responseUtil.successResponse(user);
    }

    /**
     * Delete User
     *
     * @param id
     * @return
     */
    @RequestMapping(path = APIName.DELETE, method = RequestMethod.DELETE)
    public ResponseEntity<RestAPIResponse> deleteUser(
            @PathVariable(name = "id") @NotBlank(message = ParamError.FIELD_NAME) String id
    ) {

        User user = userService.getUserById(id);
        Validator.notNull(user, APIStatus.NOT_FOUND, " User not found");

        user.setStatus(AppStatus.INACTIVE);
        userService.saveUser(user);


        return responseUtil.successResponse("OK");
    }


    @GetMapping(path = APIName.GET_INFO)
    public ResponseEntity<RestAPIResponse> getAllUser(
            @RequestParam(value = "first_name", required = false) String firstName,
            @RequestParam(value = "last_name", required = false) String lastName,
            @RequestParam(value = "start_date", required = false) String startDateString,
            @RequestParam(value = "end_date", required = false) String endDateString,
            @RequestParam(value = "search", required = false, defaultValue = "") String search,
            @RequestParam(value = "page") int page,
            @RequestParam(value = "size") int size,
            @RequestParam(value = "sort", required = false, defaultValue = "false") boolean sort,
            @RequestParam(value = "sort_field", required = false, defaultValue = "") String sortField

    ) {
        Date startDate = null;
        Date endDate = null;
        if (startDateString != null || endDateString != null) {
            if (startDateString == null || endDateString == null)
                throw new ApplicationException(APIStatus.BAD_PARAMS, "Start date must be before end date.");

            DateValidator.getInstance().isValid(startDateString, "MM/dd/yyyy");
        }

        Page<User> users = userService.getAllUser(firstName,lastName,startDate,endDate, search, sortField, sort, page, size);

        return responseUtil.successResponse(users);
    }
}
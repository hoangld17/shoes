/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digiex.spring.boot.demo.controller;

import com.digiex.spring.boot.demo.common.exception.ApplicationException;
import com.digiex.spring.boot.demo.controller.model.APIStatus;
import com.digiex.spring.boot.demo.controller.model.RestAPIResponse;
import com.digiex.spring.boot.demo.controller.model.request.CreateUserRequest;
import com.digiex.spring.boot.demo.entity.User;
import com.digiex.spring.boot.demo.helper.UserHelper;
import com.digiex.spring.boot.demo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author Quy Duong
 */
@RestController
@RequestMapping(APIName.USER_API)
public class UserController extends AbstractBaseController{
    
    final UserService userService;
    final UserHelper userHelper;

    public UserController(UserService userService, UserHelper userHelper) {
        this.userService = userService;
        this.userHelper = userHelper;
    }

    /**
     * Create new User API
     * @param createUserRequest
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<RestAPIResponse> createUser(
            @RequestBody CreateUserRequest createUserRequest
    ){
        // Should validate existed before create new user
        if (userService.existsByUsername(createUserRequest.getUsername()))
            throw new ApplicationException(APIStatus.BAD_PARAMS, "Username already exists");
        if (userService.existsByUsername(createUserRequest.getEmail()))
            throw new ApplicationException(APIStatus.BAD_PARAMS, "Email already exists");
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        if (!pattern.matcher(createUserRequest.getEmail()).matches())
            throw new ApplicationException(APIStatus.BAD_PARAMS, "Email format is wrong");
        if (createUserRequest.getUsername().isEmpty())
            throw new ApplicationException(APIStatus.BAD_PARAMS, "Username is empty");
        if (createUserRequest.getEmail().isEmpty())
            throw new ApplicationException(APIStatus.BAD_PARAMS, "Email is empty");
        if (createUserRequest.getFirstName().isEmpty())
            throw new ApplicationException(APIStatus.BAD_PARAMS, "First name is empty");
        if (createUserRequest.getLastName().isEmpty())
            throw new ApplicationException(APIStatus.BAD_PARAMS, "Last name is empty");
        if (createUserRequest.getLang().isEmpty())
            throw new ApplicationException(APIStatus.BAD_PARAMS, "Lang is empty");
        if (createUserRequest.getPasswordHash().isEmpty())
            throw new ApplicationException(APIStatus.BAD_PARAMS, "Password hash is empty");
        if (createUserRequest.getUsername().length() > 100)
            throw new ApplicationException(APIStatus.BAD_PARAMS, "Username is too long");
        if (createUserRequest.getEmail().length() > 255)
            throw new ApplicationException(APIStatus.BAD_PARAMS, "Email is too long");
        if (createUserRequest.getFirstName().length() > 100)
            throw new ApplicationException(APIStatus.BAD_PARAMS, "First name is too long");
        if (createUserRequest.getLastName().length() > 100)
            throw new ApplicationException(APIStatus.BAD_PARAMS, "Last name is too long");
        if (createUserRequest.getLang().length() > 5)
            throw new ApplicationException(APIStatus.BAD_PARAMS, "Lang is too long");
        if (createUserRequest.getPasswordHash().length() > 100)
            throw new ApplicationException(APIStatus.BAD_PARAMS, "Password hash is too long");
        User user = userHelper.createUser(createUserRequest);
        userService.saveUser(user);
        return responseUtil.successResponse(user) ;
    }

    /**
     * Get All Users API
     * @param request
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getAllUser(
            HttpServletRequest request
    ){
        List<User> users = userService.getAllUser();
        if (users.isEmpty()){
            throw new ApplicationException(APIStatus.NO_RESULT);
        }
                
        return responseUtil.successResponse(users) ;
    }

    /**
     * Get User Detail API
      * @param id
     * @return
     */
    @RequestMapping(path = "{id}", method = RequestMethod.GET)
    public ResponseEntity<RestAPIResponse> getUser(
            @PathVariable String id
    ){
        User user = userService.getUser(id);
        if (user == null){
            throw new ApplicationException(APIStatus.NOT_FOUND, "User not found");
        }
                
        return responseUtil.successResponse(user) ;
    }

    /**
     * Update User API
     * @param id
     * @param request
     * @return
     */
    @RequestMapping(path = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<RestAPIResponse> updateUser(
            @PathVariable String id,
            HttpServletRequest request
    ){
        User user = userService.getUser(id);
        if (user == null){
            throw new ApplicationException(APIStatus.NOT_FOUND);
        }
        // TODO Update
        return responseUtil.successResponse(user) ;
    }

    /**
     * Delete User
     * @param id
     * @return
     */
    @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<RestAPIResponse> deleteUser(
            @PathVariable String id
    ){
        User user = userService.getUser(id);
        if (user == null){
            throw new ApplicationException(APIStatus.NOT_FOUND);
        }
        userService.deleteUser(user);
        return responseUtil.successResponse("OK") ;
    }
    
}

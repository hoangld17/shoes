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
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        createUserRequest.checkNullAndEmptyValue();
        createUserRequest.checkLengthValue();
        createUserRequest.checkEmailFormat();
        createUserRequest.checkExistenceUsernameOrEmail(userService);
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
        if (!userService.existsById(id))
            throw new ApplicationException(APIStatus.NOT_FOUND, "The user id does not exist");
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
        if (!userService.existsById(id))
            throw new ApplicationException(APIStatus.NOT_FOUND, "The user id does not exist");
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
        if (!userService.existsById(id))
            throw new ApplicationException(APIStatus.NOT_FOUND, "The user id does not exist");
        User user = userService.getUser(id);
        if (user == null){
            throw new ApplicationException(APIStatus.NOT_FOUND);
        }
        userService.deleteUser(user);
        return responseUtil.successResponse("OK") ;
    }
    
}

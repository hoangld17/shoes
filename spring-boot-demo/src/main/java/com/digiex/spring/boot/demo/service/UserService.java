/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digiex.spring.boot.demo.service;

import com.digiex.spring.boot.demo.common.enums.AppStatus;
import com.digiex.spring.boot.demo.entity.User;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

/**
 *
 * @author Quy Duong
 */
public interface UserService {
    
    User saveUser(User user);
    
    User getUserById(String id);
    
    List<User> getAllUser();
    
    void deleteUser(User user);

    User getUserByUsernameOrEmail(String userName, String email);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsById(String id);

    User getByEmail(String email );

    User getByUsername(String userName );

    Page<User> getUserPaging(String search, int page, int size, boolean sort, String sortField);

    List<User> getByLastNameOrFirstNameOrCreatDate(String fistName, String lastName, Date startDate, Date endDate);

    List<User> getByLastNameOrFirstName(String fistName, String lastName);

    Page<User> getAllUser(String firstName, String lastName, Date startDate, Date endDate, String searchKey, String sortField, boolean ascSort, int page, int size);
}

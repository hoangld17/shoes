/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digiex.spring.boot.demo.service;

import com.digiex.spring.boot.demo.entity.User;

import java.util.List;

/**
 *
 * @author Quy Duong
 */
public interface UserService {
    
    User saveUser(User user);
    
    User getUser(String id);
    
    List<User> getAllUser();
    
    void deleteUser(User user);

    User getUserByUsernameOrEmail(String userName, String email);
}

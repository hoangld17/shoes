/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digiex.spring.boot.demo.service;

import com.digiex.spring.boot.demo.common.enums.AppStatus;
import com.digiex.spring.boot.demo.common.util.Constant;
import com.digiex.spring.boot.demo.entity.User;
import com.digiex.spring.boot.demo.repository.UserRepository;
import com.digiex.spring.boot.demo.repository.UserSpecification;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author Quy Duong
 */
@Service
public class UserServiceImpl implements UserService{

    final UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    public UserSpecification userSpecification;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(String id) {
        return userRepository.findByIdAndStatus(id, AppStatus.ACTIVE);
    }

    @Override
    public List<User> getAllUser() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    @Override
    public User getUserByUsernameOrEmail(String userName, String email) {
        return null;
    }

    @Override
    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean existsById(String id) {
        return userRepository.existsById(id);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmailAndStatus(email, AppStatus.ACTIVE);
    }

    @Override
    public User getByUsername(String userName) {
        return userRepository.findByUsernameAndStatus(userName, AppStatus.ACTIVE);
    }

    @Override
    public Page<User> getUserPaging(String search, int page, int size, boolean ascSort, String sortField) {

        Sort.Direction direction = (ascSort) ? Sort.Direction.ASC : Sort.Direction.DESC;

        String properties;

        switch (sortField) {
            case Constant.SORT_BY_USER_NAME:
                properties = "userName";
                break;
            case Constant.SORT_BY_EMAIL:
                properties = "email";
                break;
            case Constant.SORT_BY_FIRST_NAME:
                properties = "firstName";
                break;
            case Constant.SORT_BY_LAST_NAME:
                properties = "lastName";
                break;

            default:
                properties = "createdDate";
                break;
        }

        PageRequest pageReq = PageRequest.of((page - 1), size, direction, properties);

        return userRepository.getPageUser(AppStatus.ACTIVE, "%" + search.trim() + "%", pageReq);

    }

    @Override
    public List<User> getByLastNameOrFirstNameOrCreatDate(String fistName, String lastName, Date startDate, Date endDate) {
        return userRepository.listInfoByFirstNameOrLastNameOrCreatedDate(fistName, lastName, startDate, endDate);
    }
    @Override
    public List<User> getByLastNameOrFirstName(String fistName, String lastName) {
        return userRepository.listInfoByFirstNameOrLastName(fistName, lastName);
    }

    @Override
    public Page<User> getAllUser(String firstName, String lastName, Date startDate, Date endDate, String searchKey, String sortField, boolean ascSort, int page, int size) {
        System.out.println(searchKey+"ssa");
        Specification specification = userSpecification.doFilterWorkerType(firstName, lastName, startDate, endDate, searchKey, sortField, ascSort);
        Pageable pageable = PageRequest.of(page - 1, size);
        return userRepository.findAll(specification, pageable);
    }
}

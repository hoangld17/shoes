/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digiex.spring.boot.demo.repository;

import com.digiex.spring.boot.demo.entity.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 *
 * @author Quy Duong
 */
@Repository
@Transactional
public interface UserRepository extends PagingAndSortingRepository<User, String> {
    User findOneById(String id);
}

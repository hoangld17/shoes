/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.digiex.spring.boot.demo.repository;

import com.digiex.spring.boot.demo.common.enums.AppStatus;
import com.digiex.spring.boot.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Quy Duong
 */
@Repository
@Transactional
public interface UserRepository extends PagingAndSortingRepository<User, String>, JpaSpecificationExecutor<User> {
    User findByIdAndStatus(String id, AppStatus status);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsById(String id);

    User findByEmailAndStatus(String email , AppStatus status);

    User findByUsernameAndStatus(String userName , AppStatus status);

    @Query("select u from User u  where u.status = :status and" +
            " (u.username like :searchKey or u.email like :searchKey) or u.lastName like :searchKey or u.firstName like :searchKey")
    Page<User> getPageUser(@Param("status") AppStatus status,
                           @Param("searchKey") String searchKey, Pageable pageable);


    @Query("select u from User u where u.firstName like :firstName and u.lastName like :lastName and u.createdDate between :startDate and :endDate")
    List<User> listInfoByFirstNameOrLastNameOrCreatedDate(String firstName, String lastName, Date startDate, Date endDate);

    @Query("select u from User u where u.firstName like :firstName and u.lastName like :lastName")
    List<User> listInfoByFirstNameOrLastName(String firstName, String lastName);


}

package com.digiex.spring.boot.demo.repository;

import com.digiex.spring.boot.demo.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * @author DiGiEx
 */
@Repository
@Transactional
public interface SessionRepository extends JpaRepository<Session, String> {

    Session findOneById(String id);
}

package com.digiex.spring.boot.demo.service;

import com.digiex.spring.boot.demo.entity.Session;
import com.digiex.spring.boot.demo.entity.User;

import java.util.Date;
import java.util.List;

/**
 * @author DiGiEx
 */
public interface SessionService {
    Session save(Session session);
    Session findById(String id);
    void delete(Session session);


}

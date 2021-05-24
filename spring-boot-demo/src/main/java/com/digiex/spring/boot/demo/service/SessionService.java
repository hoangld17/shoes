package com.digiex.spring.boot.demo.service;

import com.digiex.spring.boot.demo.entity.Session;

/**
 * @author DiGiEx
 */
public interface SessionService {
    Session save(Session session);
    Session findById(String id);
    void delete(Session session);
}

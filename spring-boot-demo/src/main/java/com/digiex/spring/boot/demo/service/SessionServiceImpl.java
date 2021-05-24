package com.digiex.spring.boot.demo.service;

import com.digiex.spring.boot.demo.entity.Session;
import com.digiex.spring.boot.demo.repository.SessionRepository;
import org.springframework.stereotype.Service;

/**
 * @author DiGiEx
 */
@Service
public class SessionServiceImpl implements SessionService {

    final SessionRepository sessionRepository;

    public SessionServiceImpl(SessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @Override
    public Session save(Session session) {
        return sessionRepository.save(session);
    }

    @Override
    public Session findById(String id) {
        return sessionRepository.findOneById(id);
    }

    @Override
    public void delete(Session session) {
        sessionRepository.delete(session);
    }
}

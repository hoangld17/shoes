package com.digiex.spring.boot.demo.service;

import com.digiex.spring.boot.demo.common.enums.Gender;
import com.digiex.spring.boot.demo.controller.model.response.StudentResponse;
import com.digiex.spring.boot.demo.entity.Clazz;
import com.digiex.spring.boot.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ClazzService {
    Clazz saveClazz(Clazz clazz);

    Clazz getClazzById(Long id);

    List<Clazz> getAllClazz();

    Clazz deleteClazz(Long id);

    Page<Clazz> getPagingClazz(String name, String search, int page, int size, boolean sort, String sortField);


}

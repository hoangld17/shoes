package com.digiex.spring.boot.demo.service;

import com.digiex.spring.boot.demo.common.enums.AppStatus;
import com.digiex.spring.boot.demo.common.exception.ApplicationException;
import com.digiex.spring.boot.demo.controller.model.APIStatus;
import com.digiex.spring.boot.demo.entity.Clazz;
import com.digiex.spring.boot.demo.entity.Student;
import com.digiex.spring.boot.demo.repository.ClazzRepository;
import com.digiex.spring.boot.demo.repository.ClazzSpecification;
import com.digiex.spring.boot.demo.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class ClazzServiceImp implements ClazzService{
    @Autowired
    private ClazzRepository clazzRepository;
    @Autowired
    private StudentService studentService;

    @Autowired
    private ClazzSpecification clazzSpecification;

    @Override
    public Clazz saveClazz(Clazz clazz) {
        return clazzRepository.save(clazz);
    }

    @Override
    public Clazz getClazzById(Long id) {
        Clazz clazz = clazzRepository.findOneByIdAndStatus(id, AppStatus.ACTIVE);
        if (clazz == null)
            throw new ApplicationException(APIStatus.NO_RESULT, "The class id does not exist.");
        return clazz;
    }

    @Override
    public List<Clazz> getAllClazz() {
       return clazzRepository.findAllByStatus(AppStatus.ACTIVE);
    }

    @Override
    public Clazz deleteClazz(Long id) {
        Clazz clazz = getClazzById(id);
        if (studentService.findAllByClazzId(id).isEmpty()) {
            clazz.setStatus(AppStatus.INACTIVE);
            clazz = saveClazz(clazz);
        } else throw new ApplicationException(APIStatus.BAD_REQUEST, "The class can not delete!");
        return clazz;
    }

    @Override
    public Page<Clazz> getPagingClazz(String name, String search, int page, int size, boolean sort, String sortField) {
        Specification<Clazz> specification = clazzSpecification.doFilterClazz(name, search, sort, sortField);
        Pageable pageable = PageRequest.of(page - 1, size);
        return clazzRepository.findAll(specification, pageable);
    }
}

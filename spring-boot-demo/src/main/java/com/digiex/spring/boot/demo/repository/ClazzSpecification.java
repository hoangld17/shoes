package com.digiex.spring.boot.demo.repository;

import com.digiex.spring.boot.demo.common.enums.AppStatus;
import com.digiex.spring.boot.demo.common.enums.Gender;
import com.digiex.spring.boot.demo.entity.Clazz;
import com.digiex.spring.boot.demo.entity.Student;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Component
public class ClazzSpecification {
    public Specification<Clazz> doFilterClazz(
            String name,
            String search,
            boolean sort,
            String sortField

    ) {
        return (
                Root<Clazz> clazzRoot, CriteriaQuery<?> cq, CriteriaBuilder cb
        ) -> {
            cq.distinct(true);
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(clazzRoot.get("status"), AppStatus.ACTIVE));


            if (name != null && !name.trim().isEmpty()) {
                predicates.add(cb.equal(clazzRoot.get("name"), name));
            }
            if (search != null && !search.trim().isEmpty()) {
                String searchNew = search.trim();
                predicates.add(cb.or(
                        cb.like(cb.concat(clazzRoot.get("firstName"), clazzRoot.get("name")), "%" + searchNew + "%")));

            }

            Path orderClause;
            switch (sortField.trim()) {
                case "name":
                    orderClause = clazzRoot.get("name");
                    break;
                default:
                    orderClause = clazzRoot.get("createdDate");
                    break;
            }

            if (sort) {
                cq.orderBy(cb.asc(orderClause));
            } else {
                cq.orderBy(cb.desc(orderClause));
            }

            return cb.and(predicates.toArray(new Predicate[]{}));

        };
    }
}

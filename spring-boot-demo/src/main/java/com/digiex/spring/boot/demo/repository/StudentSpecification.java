package com.digiex.spring.boot.demo.repository;

import com.digiex.spring.boot.demo.common.enums.AppStatus;
import com.digiex.spring.boot.demo.common.enums.Gender;
import com.digiex.spring.boot.demo.common.util.Constant;
import com.digiex.spring.boot.demo.entity.Student;
import com.digiex.spring.boot.demo.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class StudentSpecification {
    public Specification<Student> doFilterStudent(
            String firstName,
            String lastName,
            String address,
            Date dateOfBirth,
            Gender gender,
            String search,
            boolean sort,
            String sortField

    ) {
        return (
                Root<Student> studentRoot, CriteriaQuery<?> cq, CriteriaBuilder cb
        ) -> {
            cq.distinct(true);
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(studentRoot.get("status"), AppStatus.ACTIVE));


            if (firstName != null && !firstName.trim().isEmpty()) {
                predicates.add(cb.equal(studentRoot.get("firstName"), firstName));
            }
            if (lastName != null && !lastName.trim().isEmpty()) {
                predicates.add(cb.equal(studentRoot.get("lastName"), lastName));
            }
            if (address != null && !address.trim().isEmpty()) {
                predicates.add(cb.equal(studentRoot.get("address"), address));
            }
            if (dateOfBirth != null) {
                predicates.add(cb.equal(studentRoot.get("dateOfBirth"), dateOfBirth));
            }
            if (gender != null) {
                predicates.add(cb.equal(studentRoot.get("gender"), gender));
            }
            if (search != null && !search.trim().isEmpty()) {
                String searchNew = search.trim();
                predicates.add(cb.or(
                        cb.like(cb.concat(studentRoot.get("firstName"), studentRoot.get("lastName")), "%" + searchNew + "%"),
                        cb.like(studentRoot.get("address"), "%" + searchNew + "%"),
                        cb.like(studentRoot.get("dateOfBirth"), "%" + searchNew + "%"),
                        cb.like(studentRoot.get("gender"), "%" + searchNew + "%")));

            }

            Path orderClause;
            switch (sortField.trim()) {
                case "firstName":
                    orderClause = studentRoot.get("firstName");
                    break;
                case "lastName":
                    orderClause = studentRoot.get("lastName");
                    break;
                case "address":
                    orderClause = studentRoot.get("address");
                    break;
                case "dateOfBirth":
                    orderClause = studentRoot.get("dateOfBirth");
                    break;
                case "gender":
                    orderClause = studentRoot.get("gender");
                    break;
                default:
                    orderClause = studentRoot.get("createdDate");
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

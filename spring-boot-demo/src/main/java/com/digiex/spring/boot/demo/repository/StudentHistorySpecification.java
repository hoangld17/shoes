package com.digiex.spring.boot.demo.repository;

import com.digiex.spring.boot.demo.common.enums.AppStatus;
import com.digiex.spring.boot.demo.common.enums.Gender;
import com.digiex.spring.boot.demo.entity.Student;
import com.digiex.spring.boot.demo.entity.StudentHistory;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class StudentHistorySpecification {
    public Specification<StudentHistory> doFilterStudentHistory(
            String schoolName,
            String address,
            Date startDate,
            Date endDate,
            String search,
            boolean sort,
            String sortField

    ) {
        return (
                Root<StudentHistory> studentHistoryRoot, CriteriaQuery<?> cq, CriteriaBuilder cb
        ) -> {
            cq.distinct(true);
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(studentHistoryRoot.get("status"), AppStatus.ACTIVE));


            if (schoolName != null && !schoolName.trim().isEmpty()) {
                predicates.add(cb.equal(studentHistoryRoot.get("schoolName"), schoolName));
            }

            if (address != null && !address.trim().isEmpty()) {
                predicates.add(cb.equal(studentHistoryRoot.get("address"), address));
            }
            if (startDate != null) {
                predicates.add(cb.equal(studentHistoryRoot.get("startDate"), startDate));
            }
            if (endDate != null) {
                predicates.add(cb.equal(studentHistoryRoot.get("endDate"), endDate));
            }

            if (search != null && !search.trim().isEmpty()) {
                String searchNew = search.trim();
                predicates.add(cb.or(
                        cb.like(studentHistoryRoot.get("schoolName"), "%" + searchNew + "%"),
                        cb.like(studentHistoryRoot.get("address"), "%" + searchNew + "%"),
                        cb.like(studentHistoryRoot.get("startDate"), "%" + searchNew + "%"),
                        cb.like(studentHistoryRoot.get("endDate"), "%" + searchNew + "%")));

            }

            Path orderClause;
            switch (sortField.trim()) {
                case "schoolName":
                    orderClause = studentHistoryRoot.get("schoolName");
                    break;
                case "address":
                    orderClause = studentHistoryRoot.get("address");
                    break;
                case "startDate":
                    orderClause = studentHistoryRoot.get("startDate");
                    break;
                case "endDate":
                    orderClause = studentHistoryRoot.get("endDate");
                    break;
                default:
                    orderClause = studentHistoryRoot.get("createdDate");
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

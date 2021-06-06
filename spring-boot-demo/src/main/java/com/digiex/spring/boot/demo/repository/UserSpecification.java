package com.digiex.spring.boot.demo.repository;

import com.digiex.spring.boot.demo.common.enums.AppStatus;
import com.digiex.spring.boot.demo.common.util.Constant;
import com.digiex.spring.boot.demo.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserSpecification {

    public Specification<User> doFilterWorkerType(
            String firstName,
            String lastName,
            Date startDate,
            Date endDate,
            String searchKey, String sortField, boolean ascSort

    ) {
        return (
                Root<User> userRoot, CriteriaQuery<?> cq, CriteriaBuilder cb
        ) -> {
            cq.distinct(true);
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(userRoot.get("status"), AppStatus.ACTIVE));


            if (firstName != null && !firstName.trim().isEmpty()) {
                predicates.add(cb.equal(userRoot.get("firstName"), firstName));
            }
            if (lastName != null && !lastName.trim().isEmpty()) {
                predicates.add(cb.equal(userRoot.get("lastName"), lastName));
            }
            if (startDate != null && endDate != null) {
                predicates.add(cb.between(userRoot.get("createdDate"), startDate, endDate));
            }
            System.out.println(searchKey);
            if (searchKey != null && !searchKey.trim().isEmpty()) {
                predicates.add(cb.or(cb.like(cb.concat(userRoot.get("firstName"), userRoot.get("lastName")), "%" + searchKey.trim() + "%"),
                        cb.like(userRoot.get("email"), "%" + searchKey.trim() + "%"),
                        cb.like(userRoot.get("username"), "%" + searchKey.trim() + "%"),
                        cb.like(userRoot.join("addresses").get("address"), "%" + searchKey.trim() + "%"),
                        cb.like(userRoot.join("addresses").get("phone"), "%" + searchKey.trim() + "%"),
                        cb.like(userRoot.join("addresses").get("fax"), "%" + searchKey.trim() + "%"),
                        cb.like(userRoot.join("addresses").get("city"), "%" + searchKey.trim() + "%"),
                        cb.like(userRoot.join("addresses").get("country"), "%" + searchKey.trim() + "%")));

            }

            Path orderClause;
            switch (sortField) {
                case Constant.SORT_BY_EMAIL:
                    orderClause = userRoot.get("email");
                    break;
                case Constant.SORT_BY_FIRST_NAME:
                    orderClause = userRoot.get("firstName");
                    break;
                case Constant.SORT_BY_LAST_NAME:
                    orderClause = userRoot.get("lastName");
                    break;
                case Constant.SORT_BY_USER_NAME:
                    orderClause = userRoot.get("username");
                    break;
                default:
                    orderClause = userRoot.get("createdDate");
                    break;
            }

            if (ascSort) {
                cq.orderBy(cb.asc(orderClause));
            } else {
                cq.orderBy(cb.desc(orderClause));
            }

            return cb.and(predicates.toArray(new Predicate[]{}));

        };
    }

}

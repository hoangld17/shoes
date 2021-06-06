package com.digiex.spring.boot.demo.repository;

import com.digiex.spring.boot.demo.entity.Address;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AddressRepository extends PagingAndSortingRepository<Address, String>, JpaSpecificationExecutor<Address> {
}

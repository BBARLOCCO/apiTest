package com.crm.apiTest.customer.repository;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.crm.apiTest.customer.model.Customer;
import com.crm.apiTest.customer.projection.CustomerProjection;

@RepositoryRestResource(excerptProjection =  CustomerProjection.class)
public interface CustomerRepository extends PagingAndSortingRepository<Customer, UUID> {
		
}

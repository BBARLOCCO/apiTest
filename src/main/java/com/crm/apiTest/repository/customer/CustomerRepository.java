package com.crm.apiTest.repository.customer;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.crm.apiTest.model.Customer;
import com.crm.apiTest.projection.CustomerProjection;

@RepositoryRestResource(excerptProjection =  CustomerProjection.class)
public interface CustomerRepository extends PagingAndSortingRepository<Customer, UUID> {
		
}

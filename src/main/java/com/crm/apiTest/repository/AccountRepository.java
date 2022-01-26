package com.crm.apiTest.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import com.crm.apiTest.model.Account;
import com.crm.apiTest.projection.AccountProjection;

@RepositoryRestResource(excerptProjection =  AccountProjection.class)
public interface AccountRepository extends PagingAndSortingRepository<Account, UUID> {
	
	@Override
	@PreAuthorize("hasAuthority('ADMIN')")
	<S extends Account> S save(S entity);

	@Override
	@PreAuthorize("hasAuthority('ADMIN')")
	Optional<Account> findById(UUID id);

	@Override
	@PreAuthorize("hasAuthority('ADMIN')")
	Iterable<Account> findAll();

	@Override
	@PreAuthorize("hasAuthority('ADMIN')")
	void deleteById(UUID id);

	@RestResource(exported = false)
	Optional<Account> findByEmail(String email);
	
}

package com.crm.apiTest.repository.customer;

import javax.persistence.NoResultException;

import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import com.crm.apiTest.model.Customer;

@RepositoryEventHandler(Customer.class) 
public class CustomerEventHandler {
	
	    
    @HandleBeforeCreate
    public void handleCustomerBeforeCreate(Customer c){
    	
    	if(c.getId() != null) {
    		throw new NoResultException();
    	}
    	
    	audit(c);
    }
    
    @HandleBeforeSave
    public void handleCustomerBeforeSave(Customer c){
        audit(c);
    }
    
    private void audit( Customer entity) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		Jwt jwt = (Jwt) authentication.getPrincipal();		
		String userId = jwt.getClaimAsString("sub");
		if(entity.getId() == null) {
			entity.setCreatedBy(userId);
		}
		entity.setUpdatedBy(userId);	
	}
}
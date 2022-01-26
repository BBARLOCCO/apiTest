package com.crm.apiTest.repository.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.crm.apiTest.model.Account;
import com.crm.apiTest.model.Customer;
import com.crm.apiTest.repository.AccountRepository;

@RepositoryEventHandler(Customer.class) 
public class CustomerEventHandler {
	
	@Autowired 
	private AccountRepository accountRepository;
	    
    @HandleBeforeCreate
    public void handleCustomerBeforeCreate(Customer c){
    	audit(c);
    }
    
    @HandleBeforeSave
    public void handleCustomerBeforeSave(Customer c){
        audit(c);        
    }
    
    private void audit( Customer entity) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		String userEmail = (String) authentication.getPrincipal();		
		Account account = accountRepository.findByEmail(userEmail).get();
		if(entity.getId() == null) {
			entity.setAccount1(account);
		}
		entity.setAccount2(account);	
	}
}
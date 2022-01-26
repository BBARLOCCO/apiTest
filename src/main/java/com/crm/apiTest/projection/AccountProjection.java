package com.crm.apiTest.projection;

import java.util.UUID;

import org.springframework.data.rest.core.config.Projection;

import com.crm.apiTest.model.Account;

@Projection(  name = "AccountProjection", 
types = { Account.class }) 
public interface AccountProjection {
	UUID getId();
	String getEmail();
	AccountRoleProjection getAccountRole();
}

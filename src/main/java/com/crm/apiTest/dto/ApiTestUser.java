package com.crm.apiTest.dto;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class ApiTestUser extends User{
	
	private static final long serialVersionUID = -1775369632216394313L;
	
	public ApiTestUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);		
	}

}

package com.crm.apiTest.dto;

import com.crm.apiTest.service.authentication.UserPasswordLogin;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest implements UserPasswordLogin {
	String username;
	String password;
	
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

}

package com.crm.apiTest.authentication.dto;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class EditUserRequest {
	String password;
	String given_name;
	String family_name;
	String connection = "Username-Password-Authentication";
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getGiven_name() {
		return given_name;
	}
	
	public void setGiven_name(String given_name) {
		this.given_name = given_name;
	}
	
	public String getFamily_name() {
		return family_name;
	}
	
	public void setFamily_name(String family_name) {
		this.family_name = family_name;
	}
	
	public String getConnection() {
		return connection;
	}
	
	public void setConnection(String connection) {
		this.connection = connection;
	}
	
	
}

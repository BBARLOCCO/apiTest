package com.crm.apiTest.authentication.dto;

public class ApiUser implements User {	

	String email;
	String givenName;
	String familyName;
	String userId;
	
	@Override
	public String getEmail() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getGivenName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFamilyName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUserId() {
		// TODO Auto-generated method stub
		return userId;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}

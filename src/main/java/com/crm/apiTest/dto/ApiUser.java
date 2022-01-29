package com.crm.apiTest.dto;

import java.util.Date;

import com.crm.apiTest.service.authentication.User;

public class ApiUser implements User {	

	String email;
	String givenName;
	String familyName;
	Date createdAt;
	Date updatedAt;
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
	public Date getCreatedAt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getUpdatedAt() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUserId() {
		// TODO Auto-generated method stub
		return null;
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

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}

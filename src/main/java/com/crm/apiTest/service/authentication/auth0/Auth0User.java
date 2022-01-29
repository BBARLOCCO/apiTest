package com.crm.apiTest.service.authentication.auth0;

import java.util.Date;

import com.crm.apiTest.service.authentication.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Auth0User implements User {	

	String email;
	@JsonProperty("given_name")
	String givenName;
	@JsonProperty("family_name")
	String familyName;

	@JsonProperty("created_at")
	Date createdAt;
	@JsonProperty("updated_at")
	Date updatedAt;
	@JsonProperty("user_id")
	String userId;
	
	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public String getGivenName() {
		return givenName;
	}

	@Override
	public String getFamilyName() {
		return familyName;
	}

	@Override
	public Date getCreatedAt() {
		return createdAt;
	}

	@Override
	public Date getUpdatedAt() {
		return updatedAt;
	}

	@Override
	public String getUserId() {
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

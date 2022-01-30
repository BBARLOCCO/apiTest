package com.crm.apiTest.authentication.provider.auth0.dto;

import com.crm.apiTest.authentication.dto.User;
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
	
	

	public void setUserId(String userId) {
		this.userId = userId;
	}

}

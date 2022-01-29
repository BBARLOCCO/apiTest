package com.crm.apiTest.service.authentication;

import java.util.Date;

public interface User {
	String getEmail();
	String getGivenName();
	String getFamilyName();
	Date getCreatedAt();
	Date getUpdatedAt();
	String getUserId();
}

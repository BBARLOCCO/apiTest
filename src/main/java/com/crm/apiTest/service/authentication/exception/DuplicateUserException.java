package com.crm.apiTest.service.authentication.exception;

public class DuplicateUserException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public String getMessage() {
		return "Duplicated user";
	}

}

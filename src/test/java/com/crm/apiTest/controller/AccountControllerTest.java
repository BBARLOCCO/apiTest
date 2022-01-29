package com.crm.apiTest.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.crm.apiTest.service.authentication.auth0.Auth0Service;


@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

	@InjectMocks
	private AccountController accountController;
	
	@Mock
	private Auth0Service service;
	
	@Test
	void shouldReturnListOfUsersFromAuth0WhenGetUsersIsCalled() {
		ResponseEntity<String> jsonList = ResponseEntity.ok("[list]");
		when(service.getUsers(null)).thenReturn(jsonList);
		
		ResponseEntity<String> result = accountController.getUsers(null);
		assertThat(result).isEqualTo(jsonList);
		
	}
	
}

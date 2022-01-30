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
import org.springframework.boot.test.context.SpringBootTest;

import com.crm.apiTest.authentication.controller.AccountController;
import com.crm.apiTest.authentication.dto.GetUsersResponse;
import com.crm.apiTest.authentication.provider.auth0.service.Auth0Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

	@InjectMocks
	private AccountController accountController;
	
	@Mock
	private Auth0Service service;
	
	@Test
	void shouldReturnListOfUsersFromAuth0WhenGetUsersIsCalled() throws JsonMappingException, JsonProcessingException {
		GetUsersResponse response = new GetUsersResponse();
		when(service.getUsers(null)).thenReturn(response);
		
		GetUsersResponse result = accountController.getUsers(null).getBody();
		assertThat(result).isEqualTo(response);
		
	}
	
}

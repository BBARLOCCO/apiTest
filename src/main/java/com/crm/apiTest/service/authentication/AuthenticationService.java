package com.crm.apiTest.service.authentication;

import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import com.crm.apiTest.dto.Auth0usersResponse;
import com.crm.apiTest.dto.EditUserRequest;
import com.crm.apiTest.dto.GetUsersResponse;
import com.crm.apiTest.dto.NewUserRequest;
import com.crm.apiTest.dto.PermissionsRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface AuthenticationService {		
		
		
		public GetUsersResponse getUsers(Optional<Integer> page) throws JsonMappingException, JsonProcessingException;
		
		public ResponseEntity<Auth0usersResponse> findUserByEmail(String email);
		
		public ResponseEntity<Auth0usersResponse> findUserById(String id);
		
		public HttpEntity<String> newUser(NewUserRequest body) throws JsonProcessingException;
		
		public HttpEntity<String> login(UserPasswordLogin user) throws JsonProcessingException;
		
		public HttpEntity<String> edit(String userId, EditUserRequest body) throws JsonProcessingException;
		
		public HttpEntity<String> delete(String userId) throws JsonProcessingException;
		
		public HttpEntity<String> addPermissions(String userId, PermissionsRequest permissions) throws JsonProcessingException;
		
		public HttpEntity<String> deletePermissions(String userId, PermissionsRequest permission) throws JsonProcessingException;
		

	}
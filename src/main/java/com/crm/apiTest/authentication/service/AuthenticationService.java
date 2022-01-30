package com.crm.apiTest.authentication.service;

import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import com.crm.apiTest.authentication.auth0.dto.Auth0usersResponse;
import com.crm.apiTest.authentication.dto.EditUserRequest;
import com.crm.apiTest.authentication.dto.GetUsersResponse;
import com.crm.apiTest.authentication.dto.NewUserRequest;
import com.crm.apiTest.authentication.dto.PermissionsRequest;
import com.crm.apiTest.authentication.dto.User;
import com.crm.apiTest.authentication.dto.UserPasswordLogin;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface AuthenticationService {		
		
		
		public GetUsersResponse getUsers(Optional<Integer> page) throws JsonMappingException, JsonProcessingException;
		
		public ResponseEntity<Auth0usersResponse> findUserByEmail(String email);
		
		public ResponseEntity<Auth0usersResponse> findUserById(String id);
		
		public HttpEntity<? extends User> newUser(NewUserRequest body) throws JsonProcessingException;
		
		public HttpEntity<String> login(UserPasswordLogin user) throws JsonProcessingException;
		
		public ResponseEntity<? extends User> edit(String userId, EditUserRequest body) throws JsonProcessingException;
		
		public HttpEntity<String> delete(String userId) throws JsonProcessingException;
		
		public HttpEntity<String> addPermissions(String userId, PermissionsRequest permissions) throws JsonProcessingException;
		
		public HttpEntity<String> deletePermissions(String userId, PermissionsRequest permission) throws JsonProcessingException;
		

	}
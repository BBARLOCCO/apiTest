package com.crm.apiTest.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crm.apiTest.dto.LoginRequest;
import com.crm.apiTest.service.authentication.auth0.Auth0Service;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping(value = "api/v1/login", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoginController {
	
	
	@Autowired
	private Auth0Service auth0service;
	
	public LoginController() {
		
	}
	
	@PostMapping
	public ResponseEntity<String> login(@Valid @RequestBody LoginRequest user) throws JsonProcessingException{
		return ResponseEntity.ok(auth0service.login(user).getBody());		
	}
	
}

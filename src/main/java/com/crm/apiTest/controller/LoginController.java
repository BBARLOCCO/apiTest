package com.crm.apiTest.controller;

import java.util.Optional;

import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crm.apiTest.dto.AuthenticationRequest;
import com.crm.apiTest.dto.AuthenticationResponse;
import com.crm.apiTest.dto.EditUserRequest;
import com.crm.apiTest.dto.LoginRequest;
import com.crm.apiTest.dto.NewUserRequest;
import com.crm.apiTest.dto.ApiTestUser;
import com.crm.apiTest.service.authentication.UserPasswordLogin;
import com.crm.apiTest.service.authentication.auth0.Auth0Service;
import com.crm.apiTest.service.authentication.exception.DuplicateUserException;
import com.crm.apiTest.service.authentication.exception.UserNotFoundException;
import com.crm.apiTest.util.JwtUtil;
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

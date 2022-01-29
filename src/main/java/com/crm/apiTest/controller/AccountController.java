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
import com.crm.apiTest.dto.NewUserRequest;
import com.crm.apiTest.dto.ApiTestUser;

import com.crm.apiTest.service.Auth0Service;
import com.crm.apiTest.service.DuplicateUserException;
import com.crm.apiTest.service.UserNotFoundException;
import com.crm.apiTest.util.JwtUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
@RequestMapping(value = "api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {
	
	/*private final AuthenticationManager authenticationManager;
	
	private final ApiTestUserDetailsService foodLogUserDetailsService;
	private JwtUtil jwtUtil;*/
	
	@Autowired
	private Auth0Service auth0service;
	
	public AccountController() {
		
	}
	
	@GetMapping
	public ResponseEntity<String> getUsers(@RequestParam("page") Optional<Integer> page){
		return ResponseEntity.ok(auth0service.getUsers(page).getBody());
	}
	
	@PostMapping
	public ResponseEntity<String> postUser(@Valid @RequestBody NewUserRequest body) throws JsonProcessingException{
		try {
			return ResponseEntity.ok(auth0service.newUser(body).getBody());
		}catch(DuplicateUserException e) {
			
			return ResponseEntity.internalServerError().body("{\"message\":\"There's already an user with that email\"}");
		}
		
	}
	
	@PutMapping("{id}")
	public ResponseEntity<String> edit(@PathVariable("id") String id, @Valid @RequestBody EditUserRequest body) throws JsonProcessingException{
		try {
			return ResponseEntity.ok(auth0service.edit(id, body).getBody());
		}catch(UserNotFoundException e) {			
			return ResponseEntity.notFound().build();
		}
		
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> delete(@PathVariable("id") String id) throws JsonProcessingException{
		try {
			return ResponseEntity.ok(auth0service.delete(id).getBody());
		}catch(UserNotFoundException e) {			
			return ResponseEntity.notFound().build();
		}
		
	}
	/*
	public AccountController(AuthenticationManager authenticationManager,
			PasswordEncoder passwordEncoder, ApiTestUserDetailsService nutritionixUserDetailsService,
			 JwtUtil jwtUtil) {
		this.authenticationManager = authenticationManager;
		this.foodLogUserDetailsService = nutritionixUserDetailsService;
		this.jwtUtil = jwtUtil;
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
			ApiTestUser userDetails = this.foodLogUserDetailsService.loadUserByUsername(request.getEmail());
			
			String token = jwtUtil.generateToken(userDetails);
			
			return ResponseEntity.ok(
					new AuthenticationResponse("Bearer " + token)
			);
		}catch(BadCredentialsException | UsernameNotFoundException e ) {
			throw new RuntimeException("Invalid credentials");
		}
		
		 
	}*/
	
}

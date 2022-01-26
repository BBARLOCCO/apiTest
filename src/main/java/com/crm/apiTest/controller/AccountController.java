package com.crm.apiTest.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.crm.apiTest.dto.AuthenticationRequest;
import com.crm.apiTest.dto.AuthenticationResponse;
import com.crm.apiTest.dto.ApiTestUser;

import com.crm.apiTest.service.ApiTestUserDetailsService;
import com.crm.apiTest.util.JwtUtil;

@RestController
@RequestMapping("api/v1")
public class AccountController {
	
	private final AuthenticationManager authenticationManager;
	
	private final ApiTestUserDetailsService foodLogUserDetailsService;
	private JwtUtil jwtUtil;
	
	
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
		
		 
	}
	
}

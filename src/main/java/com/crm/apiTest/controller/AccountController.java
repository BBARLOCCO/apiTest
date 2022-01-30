package com.crm.apiTest.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crm.apiTest.dto.EditUserRequest;
import com.crm.apiTest.dto.GetUsersResponse;
import com.crm.apiTest.dto.NewUserRequest;
import com.crm.apiTest.dto.PermissionsRequest;
import com.crm.apiTest.service.authentication.AuthenticationService;
import com.crm.apiTest.service.authentication.User;
import com.crm.apiTest.service.authentication.exception.DuplicateUserException;
import com.crm.apiTest.service.authentication.exception.UserNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestController
@RequestMapping(value = "api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class AccountController {
	
	@Autowired
	private AuthenticationService authService;
	
	public AccountController() {
		
	}
	
	@GetMapping
	@PreAuthorize("hasAuthority('read:users')")
	public ResponseEntity<GetUsersResponse> getUsers(@RequestParam("page") Optional<Integer> page) throws JsonMappingException, JsonProcessingException{
		return ResponseEntity.ok(authService.getUsers(page));
	}
	
	@PostMapping
	@PreAuthorize("hasAuthority('write:users')")
	public ResponseEntity<? extends User> postUser(@Valid @RequestBody NewUserRequest body) throws JsonProcessingException{
		ResponseEntity.status(HttpStatus.CREATED);
		return ResponseEntity.ok(authService.newUser(body).getBody());
		
	}
	
	@PutMapping("{id}")
	@PreAuthorize("hasAuthority('write:users')")
	public ResponseEntity<? extends User> edit(@PathVariable("id") String id, @Valid @RequestBody EditUserRequest body) throws JsonProcessingException{
		try {
			return ResponseEntity.ok(authService.edit(id, body).getBody());
		}catch(UserNotFoundException e) {			
			return ResponseEntity.notFound().build();
		}
		
	}
	
	@DeleteMapping("{id}")
	@PreAuthorize("hasAuthority('delete:users')")
	public ResponseEntity<String> delete(@PathVariable("id") String id) throws JsonProcessingException{
		try {
			return ResponseEntity.ok(authService.delete(id).getBody());
		}catch(UserNotFoundException e) {			
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping("{id}/permissions")
	@PreAuthorize("hasAuthority('write:users')")
	public ResponseEntity<String> addPermissions(@PathVariable("id") String id, @RequestBody PermissionsRequest permissions ) throws JsonProcessingException{
		try {
			return ResponseEntity.ok(authService.addPermissions(id, permissions).getBody());
		}catch(UserNotFoundException e) {			
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("{id}/permissions")
	@PreAuthorize("hasAuthority('write:users')")
	public ResponseEntity<String> deletePermissions(@PathVariable("id") String id, @RequestBody PermissionsRequest permissions ) throws JsonProcessingException{
		try {
			return ResponseEntity.ok(authService.deletePermissions(id, permissions).getBody());
		}catch(UserNotFoundException e) {			
			return ResponseEntity.notFound().build();
		}
	}
	
	
}

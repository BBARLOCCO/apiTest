package com.crm.apiTest.authentication.provider.auth0.service;

import java.util.Optional;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.crm.apiTest.authentication.dto.EditUserRequest;
import com.crm.apiTest.authentication.dto.GetUsersResponse;
import com.crm.apiTest.authentication.dto.NewUserRequest;
import com.crm.apiTest.authentication.dto.PermissionsRequest;
import com.crm.apiTest.authentication.dto.User;
import com.crm.apiTest.authentication.dto.UserPasswordLogin;
import com.crm.apiTest.authentication.exception.DuplicateUserException;
import com.crm.apiTest.authentication.exception.UserNotFoundException;
import com.crm.apiTest.authentication.provider.auth0.dto.Auth0User;
import com.crm.apiTest.authentication.provider.auth0.dto.Auth0UsersResponse;
import com.crm.apiTest.authentication.provider.auth0.util.HttpEntityBuilder;
import com.crm.apiTest.authentication.provider.auth0.util.RequestBodyMapper;
import com.crm.apiTest.authentication.service.AuthenticationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class Auth0Service implements AuthenticationService{
	
	@Value("${auth0.domain}")
	String domain;
	
	@Value("${auth0.connection}")
	String connection;
	
	@Value("${auth0.client-id}")
	String clientId;
	
	@Value("${auth0.client-secret}")
	String clientSecret;
	
	@Autowired
	HttpEntityBuilder httpEntityBuilder;
	
	@Autowired
	RequestBodyMapper bodyMapper;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	public GetUsersResponse getUsers(Optional<Integer> page) throws JsonMappingException, JsonProcessingException {
		
		StringBuilder url = new StringBuilder("https://")
				.append(domain)
				.append("/api/v2/users?per_page=50&include_totals=true&page=")
				.append(page.orElse(0));

		HttpEntity<String> entity = httpEntityBuilder.build(null);
				
		ResponseEntity<Auth0UsersResponse> responseEntity = restTemplate.exchange(
				url.toString(), 
				HttpMethod.GET, 
			    entity, 
			    new ParameterizedTypeReference<Auth0UsersResponse>() {
			    });
		
		GetUsersResponse response = new GetUsersResponse();
		Auth0UsersResponse users = responseEntity.getBody();
		response.set_embedded( users.getUsers());
		response.setPage(users.getTotal(), page.orElse(0));
		
		return response;
	}
	
	private ResponseEntity<Auth0User> apiExchange(String url, String body, HttpMethod method){
		
		HttpEntity<String> entity = httpEntityBuilder.build(body);
		
		ResponseEntity<Auth0User> responseEntity = restTemplate.exchange(
				url, 
			    method, 
			    entity, 
			    new ParameterizedTypeReference<Auth0User>() {
			    });
		return responseEntity;
	}
	
	private ResponseEntity<String> apiExchangeString(String url, String body, HttpMethod method){
		
		HttpEntity<String> entity = httpEntityBuilder.build(body);
		
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				url, 
			    method, 
			    entity, 
			    new ParameterizedTypeReference<String>() {
			    });
		return responseEntity;
	}
	
	public ResponseEntity<Auth0UsersResponse> findUserByEmail(String email) {
		
		StringBuilder url = new StringBuilder("https://")
				.append(domain)
				.append("/api/v2/users?include_totals=true&q=email:")
				.append(email);
		
		HttpEntity<String> entity = httpEntityBuilder.build("");

		ResponseEntity<Auth0UsersResponse> responseEntity = restTemplate.exchange(
				url.toString(), 
			    HttpMethod.GET, 
			    entity, 
			    new ParameterizedTypeReference<Auth0UsersResponse>() {
			    });
		return responseEntity;
	}
	
	public ResponseEntity<Auth0UsersResponse> findUserById(String id) {
		
		StringBuilder url = new StringBuilder("https://")
				.append(domain)
				.append("/api/v2/users?include_totals=true&q=user_id:")
				.append(id);
		
		HttpEntity<String> entity = httpEntityBuilder.build("");

		ResponseEntity<Auth0UsersResponse> responseEntity = restTemplate.exchange(
				url.toString(), 
			    HttpMethod.GET, 
			    entity, 
			    new ParameterizedTypeReference<Auth0UsersResponse>() {
			    });
		return responseEntity;
	}
	
	public HttpEntity<? extends User> newUser(NewUserRequest body) throws JsonProcessingException {
		
		body.setConnection(connection);
		
		Auth0UsersResponse existingUser = this.findUserByEmail(body.getEmail()).getBody();
		if(existingUser.getTotal() > 0) {
			throw new DuplicateUserException();
		}
		
		StringBuilder url = new StringBuilder("https://")
				.append(domain)
				.append("/api/v2/users");
		
		String bodyString = objectMapper.writeValueAsString(body);
		
		return apiExchange( url.toString(), bodyString, HttpMethod.POST);
	}
	
	public HttpEntity<String> login(UserPasswordLogin user) throws JsonProcessingException {
		StringBuilder url = new StringBuilder("https://")
				.append(domain)
				.append("/oauth/token");
		
		return apiExchangeString( url.toString(), bodyMapper.map(user), HttpMethod.POST);
	}

	
	public ResponseEntity<? extends User> edit(String userId, EditUserRequest body) throws JsonProcessingException {
		
		checkIfUserExists(userId, true);	
		
		StringBuilder url = new StringBuilder("https://")
				.append(domain)
				.append("/api/v2/users/")
				.append(userId);
		
		String bodyString = objectMapper.writeValueAsString(body);
		
		enablePatchMethod();
		return apiExchange( url.toString(), bodyString, HttpMethod.PATCH);
	}

	private void enablePatchMethod() {
		HttpClient httpClient = HttpClientBuilder.create().build();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
	}
	
	public HttpEntity<String> delete(String userId) throws JsonProcessingException {
		checkIfUserExists(userId, true);
		
		StringBuilder url = new StringBuilder("https://")
				.append(domain)
				.append("/api/v2/users/")
				.append(userId);
		
		return apiExchangeString( url.toString(), null, HttpMethod.DELETE);
	}

	@Override
	public HttpEntity<String> addPermissions(String userId, PermissionsRequest permissions) throws JsonProcessingException {
		checkIfUserExists(userId, true);
		
		StringBuilder url = new StringBuilder("https://")
				.append(domain)
				.append("/api/v2/users/")
				.append(userId)
				.append("/permissions");
		
		return apiExchangeString( url.toString(), bodyMapper.map(permissions), HttpMethod.POST);
	}
	
	@Override
	public HttpEntity<String> deletePermissions(String userId, PermissionsRequest permissions) throws JsonProcessingException {
		checkIfUserExists(userId, true);
		
		StringBuilder url = new StringBuilder("https://")
				.append(domain)
				.append("/api/v2/users/")
				.append(userId)
				.append("/permissions");
		
		return apiExchangeString( url.toString(), bodyMapper.map(permissions), HttpMethod.DELETE);
	}

	private void checkIfUserExists(String userId, Boolean shouldExist) {
		Auth0UsersResponse existingUser = this.findUserById(userId).getBody();
		if(existingUser.getTotal() == 0 && shouldExist) {
			throw new UserNotFoundException();
		}else if(!shouldExist) {
			throw new DuplicateUserException();
		}		
	}
}

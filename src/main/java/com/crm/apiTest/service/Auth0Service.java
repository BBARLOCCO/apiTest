package com.crm.apiTest.service;

import java.util.Collections;
import java.util.Optional;

import javax.validation.Valid;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.crm.apiTest.dto.Auth0usersResponse;
import com.crm.apiTest.dto.EditUserRequest;
import com.crm.apiTest.dto.NewUserRequest;
import com.crm.apiTest.service.authentication.UserPasswordLogin;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class Auth0Service {
	
	@Value("${auth0.token}")
	String auth0token;
	
	@Value("${auth0.domain}")
	String domain;
	
	@Value("${auth0.client_id}")
	String clientId;
	
	@Value("${auth0.client_secret}")
	String clientSecret;
	
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	
	public ResponseEntity<String> getUsers(Optional<Integer> page) {
		
		StringBuilder url = new StringBuilder("https://")
				.append(domain)
				.append("/api/v2/users?per_page=50&include_totals=true&page=")
				.append(page.orElse(0));
		
		HttpEntity<String> entity = getEntityWithHeaders("");

		ResponseEntity<String> responseEntity = restTemplate.exchange(
				url.toString(), 
			    HttpMethod.GET, 
			    entity, 
			    new ParameterizedTypeReference<String>() {
			    });
		return responseEntity;
	}
	
	public ResponseEntity<Auth0usersResponse> findUserByEmail(String email) {
		
		StringBuilder url = new StringBuilder("https://")
				.append(domain)
				.append("/api/v2/users?include_totals=true&q=email:")
				.append(email);
		
		HttpEntity<String> entity = getEntityWithHeaders("");

		ResponseEntity<Auth0usersResponse> responseEntity = restTemplate.exchange(
				url.toString(), 
			    HttpMethod.GET, 
			    entity, 
			    new ParameterizedTypeReference<Auth0usersResponse>() {
			    });
		return responseEntity;
	}
	
	public ResponseEntity<Auth0usersResponse> findUserById(String id) {
		
		StringBuilder url = new StringBuilder("https://")
				.append(domain)
				.append("/api/v2/users?include_totals=true&q=user_id:")
				.append(id);
		
		HttpEntity<String> entity = getEntityWithHeaders("");

		ResponseEntity<Auth0usersResponse> responseEntity = restTemplate.exchange(
				url.toString(), 
			    HttpMethod.GET, 
			    entity, 
			    new ParameterizedTypeReference<Auth0usersResponse>() {
			    });
		return responseEntity;
	}
	
	public HttpEntity<String> newUser(NewUserRequest body) throws JsonProcessingException {
		Auth0usersResponse existingUser = this.findUserByEmail(body.getEmail()).getBody();
		if(existingUser.getTotal() > 0) {
			throw new DuplicateUserException();
		}
		StringBuilder url = new StringBuilder("https://")
				.append(domain)
				.append("/api/v2/users");
		
		ObjectMapper objectMapper = new ObjectMapper();
		String bodyString = objectMapper.writeValueAsString(body);
		HttpEntity<String> entity = getEntityWithHeaders(bodyString);
		
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				url.toString(), 
			    HttpMethod.POST, 
			    entity, 
			    new ParameterizedTypeReference<String>() {
			    });
		return responseEntity;
	}
	
	public HttpEntity<String> login(UserPasswordLogin user) throws JsonProcessingException {
		StringBuilder url = new StringBuilder("https://")
				.append(domain)
				.append("/oauth/token");
		
		ObjectMapper objectMapper = new ObjectMapper();
		String bodyString = objectMapper.writeValueAsString(user);
		HttpEntity<String> entity = getEntityWithHeaders(bodyString);
		
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				url.toString(), 
			    HttpMethod.POST, 
			    entity, 
			    new ParameterizedTypeReference<String>() {
			    });
		return responseEntity;
	}
	
	public HttpEntity<String> edit(String userId, @Valid EditUserRequest body) throws JsonProcessingException {
		HttpClient httpClient = HttpClientBuilder.create().build();
		restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));
		
		Auth0usersResponse existingUser = this.findUserById(userId).getBody();
		if(existingUser.getTotal() == 0) {
			throw new UserNotFoundException();
		}
		
		StringBuilder url = new StringBuilder("https://")
				.append(domain)
				.append("/api/v2/users/")
				.append(userId);
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		String bodyString = objectMapper.writeValueAsString(body);
		HttpEntity<String> entity = getEntityWithHeaders(bodyString);
		
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				url.toString(), 
			    HttpMethod.PATCH, 
			    entity, 
			    new ParameterizedTypeReference<String>() {
			    });
		return responseEntity;
	}
	
	public HttpEntity<String> delete(String userId) throws JsonProcessingException {

		Auth0usersResponse existingUser = this.findUserById(userId).getBody();
		if(existingUser.getTotal() == 0) {
			throw new UserNotFoundException();
		}
		
		StringBuilder url = new StringBuilder("https://")
				.append(domain)
				.append("/api/v2/users/")
				.append(userId);
		
		HttpEntity<String> entity = getEntityWithHeaders(null);
		
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				url.toString(), 
			    HttpMethod.DELETE, 
			    entity, 
			    new ParameterizedTypeReference<String>() {
			    });
		return responseEntity;
	}

	
	private HttpEntity<String> getEntityWithHeaders(String body){
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.set("Authorization", auth0token);
		headers.setContentType(MediaType.APPLICATION_JSON);
		return  new HttpEntity<>(body, headers);
	}

	

}

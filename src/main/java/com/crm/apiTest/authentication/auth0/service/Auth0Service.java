package com.crm.apiTest.authentication.auth0.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

import com.crm.apiTest.authentication.auth0.dto.Auth0User;
import com.crm.apiTest.authentication.auth0.dto.Auth0usersResponse;
import com.crm.apiTest.authentication.dto.EditUserRequest;
import com.crm.apiTest.authentication.dto.GetUsersResponse;
import com.crm.apiTest.authentication.dto.NewUserRequest;
import com.crm.apiTest.authentication.dto.PermissionsRequest;
import com.crm.apiTest.authentication.dto.User;
import com.crm.apiTest.authentication.dto.UserPasswordLogin;
import com.crm.apiTest.authentication.exception.DuplicateUserException;
import com.crm.apiTest.authentication.exception.UserNotFoundException;
import com.crm.apiTest.authentication.service.AuthenticationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class Auth0Service implements AuthenticationService{
	
	@Value("${auth0.token}")
	String auth0token;
	
	@Value("${auth0.domain}")
	String domain;
	
	@Value("${auth0.audience}")
	String audience;
	
	@Value("${auth0.connection}")
	String connection;
	
	@Value("${auth0.client-id}")
	String clientId;
	
	@Value("${auth0.client-secret}")
	String clientSecret;
	
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	public GetUsersResponse getUsers(Optional<Integer> page) throws JsonMappingException, JsonProcessingException {
		
		StringBuilder url = new StringBuilder("https://")
				.append(domain)
				.append("/api/v2/users?per_page=50&include_totals=true&page=")
				.append(page.orElse(0));

		HttpEntity<String> entity = getEntityWithHeaders(null);
				
		ResponseEntity<Auth0usersResponse> responseEntity = restTemplate.exchange(
				url.toString(), 
				HttpMethod.GET, 
			    entity, 
			    new ParameterizedTypeReference<Auth0usersResponse>() {
			    });
		
		GetUsersResponse response = new GetUsersResponse();
		Auth0usersResponse users = responseEntity.getBody();
		response.set_embedded( users.getUsers());
		response.setPage(users.getTotal(), page.orElse(0));
		
		return response;
	}
	
	private ResponseEntity<Auth0User> apiExchange(String url, String body, HttpMethod method){
		
		HttpEntity<String> entity = getEntityWithHeaders(body);
		
		ResponseEntity<Auth0User> responseEntity = restTemplate.exchange(
				url, 
			    method, 
			    entity, 
			    new ParameterizedTypeReference<Auth0User>() {
			    });
		return responseEntity;
	}
	
private ResponseEntity<String> apiExchangeString(String url, String body, HttpMethod method){
		
		HttpEntity<String> entity = getEntityWithHeaders(body);
		
		ResponseEntity<String> responseEntity = restTemplate.exchange(
				url, 
			    method, 
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
	
	public HttpEntity<? extends User> newUser(NewUserRequest body) throws JsonProcessingException {
		
		body.setConnection(connection);
		
		Auth0usersResponse existingUser = this.findUserByEmail(body.getEmail()).getBody();
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
		
		ObjectMapper objectMapper = new ObjectMapper();
		
		String bodyString = objectMapper.writeValueAsString(buildLoginRequestBody(user));
		
		return apiExchangeString( url.toString(), bodyString, HttpMethod.POST);
	}

	private Map<String, Object> buildLoginRequestBody(UserPasswordLogin user){
		Map<String, Object> body = new HashMap<String,Object>();
		body.put("client_id", clientId);
		body.put("client_secret", clientSecret);
		body.put("audience", audience);
		body.put("grant_type", "password");
		body.put("username", user.getUsername());
		body.put("password", user.getPassword());
		body.put("connection", connection);
		return body;
	}
	
	public ResponseEntity<? extends User> edit(String userId, @Valid EditUserRequest body) throws JsonProcessingException {
		
		
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
		
		String bodyString = objectMapper.writeValueAsString(body);
		
		return apiExchange( url.toString(), bodyString, HttpMethod.PATCH);
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
		
		return apiExchangeString( url.toString(), null, HttpMethod.DELETE);
	}

	
	private HttpEntity<String> getEntityWithHeaders(String body){
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.set("Authorization", auth0token);
		headers.setContentType(MediaType.APPLICATION_JSON);
		return  new HttpEntity<>(body, headers);
	}

	@Override
	public HttpEntity<String> addPermissions(String userId, PermissionsRequest permissions) throws JsonProcessingException {
		Auth0usersResponse existingUser = this.findUserById(userId).getBody();
		if(existingUser.getTotal() == 0) {
			throw new UserNotFoundException();
		}
		
		StringBuilder url = new StringBuilder("https://")
				.append(domain)
				.append("/api/v2/users/")
				.append(userId)
				.append("/permissions");
		
		String bodyString = objectMapper.writeValueAsString(buildPermissionsBody(permissions));
		
		return apiExchangeString( url.toString(), bodyString, HttpMethod.POST);
	}
	
	@Override
	public HttpEntity<String> deletePermissions(String userId, PermissionsRequest permissions) throws JsonProcessingException {
		Auth0usersResponse existingUser = this.findUserById(userId).getBody();
		if(existingUser.getTotal() == 0) {
			throw new UserNotFoundException();
		}
		
		StringBuilder url = new StringBuilder("https://")
				.append(domain)
				.append("/api/v2/users/")
				.append(userId)
				.append("/permissions");
		
		String bodyString = objectMapper.writeValueAsString(buildPermissionsBody(permissions));
		
		return apiExchangeString( url.toString(), bodyString, HttpMethod.DELETE);
	}
	
	private Map<String, Object> buildPermissionsBody(PermissionsRequest request){
		
		Map<String, Object> body = new HashMap<String, Object>();
		
		List<Map<String, Object>> permissions = new ArrayList<Map<String, Object>>();
		request.getPermissions().forEach( permission -> {
			Map<String, Object> object = new HashMap<String,Object>();
			object.put("permission_name", permission);
			object.put("resource_server_identifier", audience);
			permissions.add(object);
		});
		
		body.put("permissions", permissions);
		
		return body;
	}

}

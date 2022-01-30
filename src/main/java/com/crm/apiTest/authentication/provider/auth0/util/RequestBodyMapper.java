package com.crm.apiTest.authentication.provider.auth0.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.crm.apiTest.authentication.dto.PermissionsRequest;
import com.crm.apiTest.authentication.dto.UserPasswordLogin;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class RequestBodyMapper {

	@Value("${auth0.audience}")
	String audience;
	
	@Value("${auth0.client-id}")
	String clientId;
	
	@Value("${auth0.client-secret}")
	String clientSecret;
	
	@Value("${auth0.connection}")
	String connection;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	public String map(PermissionsRequest request) throws JsonProcessingException{
		
		Map<String, Object> body = new HashMap<String, Object>();
		
		List<Map<String, Object>> permissions = new ArrayList<Map<String, Object>>();
		request.getPermissions().forEach( permission -> {
			Map<String, Object> object = new HashMap<String,Object>();
			object.put("permission_name", permission);
			object.put("resource_server_identifier", audience);
			permissions.add(object);
		});
		
		body.put("permissions", permissions);
		
		return objectMapper.writeValueAsString(body);
	}
	
	public String map(UserPasswordLogin user) throws JsonProcessingException{
		Map<String, Object> body = new HashMap<String,Object>();
		body.put("client_id", clientId);
		body.put("client_secret", clientSecret);
		body.put("audience", audience);
		body.put("grant_type", "password");
		body.put("username", user.getUsername());
		body.put("password", user.getPassword());
		body.put("connection", connection);
		return objectMapper.writeValueAsString(body);
	}

}

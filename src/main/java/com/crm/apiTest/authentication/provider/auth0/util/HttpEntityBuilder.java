package com.crm.apiTest.authentication.provider.auth0.util;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Component
public class HttpEntityBuilder {
	
	@Value("${auth0.token}")
	String auth0token;
	
	public HttpEntity<String> build(String body){
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		headers.set("Authorization", auth0token);
		headers.setContentType(MediaType.APPLICATION_JSON);
		return  new HttpEntity<>(body, headers);
	}
}

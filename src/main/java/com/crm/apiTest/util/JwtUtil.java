package com.crm.apiTest.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil {	
	
	@Autowired
	JwtDecoder decoder;


	private Claims extractClaims(String token) {
		Jwt jwt = decoder.decode(token);
		return Jwts.claims(jwt.getClaims());
		
	}
}

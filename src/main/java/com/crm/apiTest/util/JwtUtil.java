package com.crm.apiTest.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.crm.apiTest.dto.ApiTestUser;
import com.crm.apiTest.service.ApiTestUserDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {
	
	@Value( "${foodLog.secretKey}" )
	private String SECRET_KEY;
	
	@Autowired
	ApiTestUserDetailsService userDetailsService;
	
	public String generateToken(ApiTestUser userDetails) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("PRIVILEGES", userDetails.getAuthorities());
		return createToken(claims, userDetails.getUsername());
	}

	private String createToken(Map<String, Object> claims, String username) {
		
		return Jwts.builder().setClaims(claims)
				.setSubject(username)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	public Authentication validateToken(String token) {

		Claims claims = extractClaims(token);
		
		if(claims.getExpiration().before(new Date())) {
			return null;
		}

		String userName = claims.getSubject();
		UserDetails user = this.userDetailsService.loadUserByUsername(userName);
		return new UsernamePasswordAuthenticationToken(userName , null, user.getAuthorities());
	}

	private Claims extractClaims(String token) {
		
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
		
	}
}

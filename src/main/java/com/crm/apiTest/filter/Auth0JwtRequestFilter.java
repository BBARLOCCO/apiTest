package com.crm.apiTest.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.crm.apiTest.util.JwtUtil;

//@Component
public class Auth0JwtRequestFilter extends OncePerRequestFilter{
	
	private final JwtUtil jwtUtil;

	public Auth0JwtRequestFilter(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String tokenWithBearer = request.getHeader("Authorization");
		
		if(tokenWithBearer == null || !tokenWithBearer.startsWith("Bearer")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String token = tokenWithBearer.substring(7);

		//Authentication auth =  jwtUtil.validateToken(token);
		
		//SecurityContextHolder.getContext().setAuthentication(auth);
		
		filterChain.doFilter(request, response);
	}

}

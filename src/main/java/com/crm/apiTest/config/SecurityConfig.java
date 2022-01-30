package com.crm.apiTest.config;

import java.util.Arrays;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.web.cors.CorsConfiguration;

import com.crm.apiTest.authentication.auth0.validator.AudienceValidator;

import org.springframework.security.oauth2.jwt.*;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	//private final Auth0JwtRequestFilter jwtRequestFilter;
	
	@Value("${auth0.audience}")
	private String audience;
	
	@Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
	private String issuer;
	
	public SecurityConfig() {
		super();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
		.cors().configurationSource( request -> {
			CorsConfiguration corsConfiguration = new CorsConfiguration();
			corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
			corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
			corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
			return corsConfiguration; 
		}).and()
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			//.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
			.authorizeRequests().antMatchers("/api/v1/login").permitAll()
			.and()
			.authorizeRequests().antMatchers("/api/v1/images/{filename:.+}").permitAll()
			.anyRequest().authenticated().and()
			.oauth2ResourceServer()
			.jwt()
			.decoder(jwtDecoder())
			.jwtAuthenticationConverter(jwtAuthenticationConverter());
		
	}
	
	JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter converter = new JwtGrantedAuthoritiesConverter();
        converter.setAuthoritiesClaimName("permissions");
        converter.setAuthorityPrefix("");

        JwtAuthenticationConverter jwtConverter = new JwtAuthenticationConverter();
        jwtConverter.setJwtGrantedAuthoritiesConverter(converter);
        return jwtConverter;
    }
	/*@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		
		return super.authenticationManager();
	}*/
	
	@Bean
	public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
	    return new SecurityEvaluationContextExtension();
	}
	
	JwtDecoder jwtDecoder() {
	    OAuth2TokenValidator<Jwt> withAudience = new AudienceValidator(audience);
	    OAuth2TokenValidator<Jwt> withIssuer = JwtValidators.createDefaultWithIssuer(issuer);
	    OAuth2TokenValidator<Jwt> validator = new DelegatingOAuth2TokenValidator<>(withAudience, withIssuer);

	    NimbusJwtDecoder jwtDecoder = (NimbusJwtDecoder) JwtDecoders.fromOidcIssuerLocation(issuer);
	    jwtDecoder.setJwtValidator(validator);
	    return jwtDecoder;
	  }
	
}

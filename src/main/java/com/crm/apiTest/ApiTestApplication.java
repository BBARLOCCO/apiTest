package com.crm.apiTest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.crm.apiTest.storage.config.StorageProperties;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class ApiTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiTestApplication.class, args);
	}
	
	@Bean
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}
	
	@Bean
	public ObjectMapper getObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		return objectMapper;
	}

}

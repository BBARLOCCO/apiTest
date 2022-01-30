package com.crm.apiTest.customer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.crm.apiTest.customer.repository.CustomerEventHandler;

@Configuration
public class RepositoryConfiguration{
    
    public RepositoryConfiguration(){
        super();
    }

    @Bean
    CustomerEventHandler customerEventHandler(){
        return new CustomerEventHandler();
    }
}
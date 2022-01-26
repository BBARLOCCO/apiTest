package com.crm.apiTest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.crm.apiTest.repository.customer.CustomerEventHandler;

@Configuration
public class RepositoryConfiguration{
    
    public RepositoryConfiguration(){
        super();
    }

    @Bean
    CustomerEventHandler bookEventHandler(){
        return new CustomerEventHandler();
    }
}
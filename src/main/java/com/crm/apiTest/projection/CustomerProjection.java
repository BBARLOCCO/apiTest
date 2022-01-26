package com.crm.apiTest.projection;

import java.util.UUID;

import org.springframework.data.rest.core.config.Projection;

import com.crm.apiTest.model.Customer;

@Projection(  name = "CustomerProjection", 
types = { Customer.class }) 
public interface CustomerProjection {
	UUID getId();
	String getName();
	String getLastName();
	String getPhoto();
}

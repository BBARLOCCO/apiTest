package com.crm.apiTest.projection;

import org.springframework.data.rest.core.config.Projection;

import com.crm.apiTest.model.Privilege;


@Projection(  name = "AccountProjection", 
types = { Privilege.class }) 
public interface PrivilegeProjection {
	String getName();
}

package com.crm.apiTest.projection;

import java.util.List;

import org.springframework.data.rest.core.config.Projection;

import com.crm.apiTest.model.AccountRole;


@Projection(  name = "AccountRoleProjection", 
types = { AccountRole.class }) 
public interface AccountRoleProjection {
	String getName();
	List<AccountRolePrivilegeProjection> getAccountRolePrivileges();
}

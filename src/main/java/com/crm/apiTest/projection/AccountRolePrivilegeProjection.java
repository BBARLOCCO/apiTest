package com.crm.apiTest.projection;


import org.springframework.data.rest.core.config.Projection;

import com.crm.apiTest.model.AccountRolePrivilege;

@Projection(  name = "AccountRolePrivilegeProjection", 
types = { AccountRolePrivilege.class }) 
public interface AccountRolePrivilegeProjection {
	PrivilegeProjection getPrivilege();
}

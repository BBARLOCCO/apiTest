package com.crm.apiTest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

import com.crm.apiTest.projection.AccountRolePrivilegeProjection;
import com.crm.apiTest.projection.CustomerProjection;

@Configuration
public class ProjectionsConfiguration implements RepositoryRestConfigurer {
 
  public void configureRepositoryRestConfiguration(
      RepositoryRestConfiguration config) {
    config
      .getProjectionConfiguration()
      .addProjection(CustomerProjection.class)
      .addProjection(AccountRolePrivilegeProjection.class);
  }
}
package com.crm.apiTest.dto;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import com.crm.apiTest.service.authentication.auth0.Auth0User;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Auth0usersResponse extends RepresentationModel<Auth0usersResponse>{
	
	Integer start;
	Integer limit;
	Integer length;
	Integer total;
	List<Auth0User> users;
	
	public Integer getStart() {
		return start;
	}
	public void setStart(Integer start) {
		this.start = start;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public List<Auth0User> getUsers() {
		return users;
	}
	public void setUsers(List<Auth0User> users) {
		this.users = users;
	}
	
}

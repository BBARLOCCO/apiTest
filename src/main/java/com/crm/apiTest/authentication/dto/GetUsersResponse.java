package com.crm.apiTest.authentication.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class GetUsersResponse {
	
	private EmbeddedUsers _embedded;
	private Page page;
	
	public EmbeddedUsers get_embedded() {
		return _embedded;
	}

	public void set_embedded(List<? extends User> users) {
		this._embedded = new EmbeddedUsers(users);
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Integer totalElements,Integer number) {
		this.page = new Page(20, totalElements, (totalElements / 20) + 1, number);
	}
	
}

@AllArgsConstructor
class EmbeddedUsers{
	public List<? extends User> users;
}

@AllArgsConstructor
class Page{
	public Integer size;
	public Integer totalElements;
	public Integer totalPages;
	public Integer number;
}

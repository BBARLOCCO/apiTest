package com.crm.apiTest.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.crm.apiTest.dto.ApiTestUser;
import com.crm.apiTest.model.Account;
import com.crm.apiTest.repository.AccountRepository;

@Service
public class ApiTestUserDetailsService implements UserDetailsService{
	
	@Autowired
	private AccountRepository repository;

	private final PasswordEncoder passwordEncoder;
	
	public ApiTestUserDetailsService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder; 
	}

	@Override
	public ApiTestUser loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Account> userAccount = repository.findByEmail(username);
		ArrayList<SimpleGrantedAuthority> privileges = new ArrayList<SimpleGrantedAuthority>();
		if(userAccount.isPresent()) {
			Account account = userAccount.get();
			account.getAccountRole().getAccountRolePrivileges().forEach( item -> {
				privileges.add(new SimpleGrantedAuthority(item.getPrivilege().getName()));
			});
			return new ApiTestUser(username, passwordEncoder.encode("password"), privileges );
		}else {
			throw new UsernameNotFoundException("Invalid username");
		}
		
		
	}

}

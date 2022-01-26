package com.crm.apiTest.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;


/**
 * The persistent class for the account_role database table.
 * 
 */
@Entity
@Table(name="account_role")
@NamedQuery(name="AccountRole.findAll", query="SELECT a FROM AccountRole a")
public class AccountRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private UUID id;

	@CreationTimestamp
	@Column(name="created_at")
	private Timestamp createdAt;

	private String name;

	@UpdateTimestamp
	@Column(name="updated_at")
	private Timestamp updatedAt;

	//bi-directional many-to-one association to Account
	@OneToMany(mappedBy="accountRole")
	private List<Account> accounts;

	//bi-directional many-to-one association to AccountRolePrivilege
	@OneToMany(mappedBy="accountRole",fetch = FetchType.EAGER)
	@JsonManagedReference
	private List<AccountRolePrivilege> accountRolePrivileges;

	public AccountRole() {
	}

	public UUID getId() {
		return this.id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Timestamp getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public List<Account> getAccounts() {
		return this.accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public Account addAccount(Account account) {
		getAccounts().add(account);
		account.setAccountRole(this);

		return account;
	}

	public Account removeAccount(Account account) {
		getAccounts().remove(account);
		account.setAccountRole(null);

		return account;
	}

	public List<AccountRolePrivilege> getAccountRolePrivileges() {
		return this.accountRolePrivileges;
	}

	public void setAccountRolePrivileges(List<AccountRolePrivilege> accountRolePrivileges) {
		this.accountRolePrivileges = accountRolePrivileges;
	}

	public AccountRolePrivilege addAccountRolePrivilege(AccountRolePrivilege accountRolePrivilege) {
		getAccountRolePrivileges().add(accountRolePrivilege);
		accountRolePrivilege.setAccountRole(this);

		return accountRolePrivilege;
	}

	public AccountRolePrivilege removeAccountRolePrivilege(AccountRolePrivilege accountRolePrivilege) {
		getAccountRolePrivileges().remove(accountRolePrivilege);
		accountRolePrivilege.setAccountRole(null);

		return accountRolePrivilege;
	}

}
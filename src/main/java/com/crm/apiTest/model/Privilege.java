package com.crm.apiTest.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;


/**
 * The persistent class for the privilege database table.
 * 
 */
@Entity
@NamedQuery(name="Privilege.findAll", query="SELECT p FROM Privilege p")
public class Privilege implements Serializable {
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

	//bi-directional many-to-one association to AccountRolePrivilege
	@OneToMany(mappedBy="privilege")
	@JsonBackReference
	private List<AccountRolePrivilege> accountRolePrivileges;

	public Privilege() {
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

	public List<AccountRolePrivilege> getAccountRolePrivileges() {
		return this.accountRolePrivileges;
	}

	public void setAccountRolePrivileges(List<AccountRolePrivilege> accountRolePrivileges) {
		this.accountRolePrivileges = accountRolePrivileges;
	}

	public AccountRolePrivilege addAccountRolePrivilege(AccountRolePrivilege accountRolePrivilege) {
		getAccountRolePrivileges().add(accountRolePrivilege);
		accountRolePrivilege.setPrivilege(this);

		return accountRolePrivilege;
	}

	public AccountRolePrivilege removeAccountRolePrivilege(AccountRolePrivilege accountRolePrivilege) {
		getAccountRolePrivileges().remove(accountRolePrivilege);
		accountRolePrivilege.setPrivilege(null);

		return accountRolePrivilege;
	}

}
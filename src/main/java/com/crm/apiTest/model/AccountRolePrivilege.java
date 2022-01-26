package com.crm.apiTest.model;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.sql.Timestamp;
import java.util.UUID;


/**
 * The persistent class for the account_role_privilege database table.
 * 
 */
@Entity
@Table(name="account_role_privilege")
@NamedQuery(name="AccountRolePrivilege.findAll", query="SELECT a FROM AccountRolePrivilege a")
public class AccountRolePrivilege implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private UUID id;

	@CreationTimestamp
	@Column(name="created_at")
	private Timestamp createdAt;

	@UpdateTimestamp
	@Column(name="updated_at")
	private Timestamp updatedAt;

	//bi-directional many-to-one association to AccountRole
	@ManyToOne
	@JoinColumn(name="account_role_id")
	@JsonBackReference
	private AccountRole accountRole;

	//bi-directional many-to-one association to Privilege
	@ManyToOne
	@JsonManagedReference
	private Privilege privilege;

	public AccountRolePrivilege() {
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

	public Timestamp getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public AccountRole getAccountRole() {
		return this.accountRole;
	}

	public void setAccountRole(AccountRole accountRole) {
		this.accountRole = accountRole;
	}

	public Privilege getPrivilege() {
		return this.privilege;
	}

	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
	}

}
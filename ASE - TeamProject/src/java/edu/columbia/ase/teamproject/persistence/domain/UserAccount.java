package edu.columbia.ase.teamproject.persistence.domain;

import java.util.List;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import edu.columbia.ase.teamproject.security.Permission;

@Entity
@Table(name = "USER")
public class UserAccount {

	public static enum AccountType {
		/**
		 * User who authenticates using an in-database username/password.
		 */
		LOCAL,
		/**
		 * User who authenticates using OpenId.
		 */
		OPENID;
	};

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "id", nullable=false)
	private long id;

	@Column(name="accountType", nullable=false)
	@Enumerated
	private AccountType accountType;

	@Column(name="username", nullable=false)
	private String username;

	@Column(name="password")
	private String password;

	@ElementCollection(fetch=FetchType.EAGER)
	@Column(name="permissions")
	private List<Permission> permissions;

	// A no-arg constructor is required for Hibernate.
	@SuppressWarnings("unused")
	private UserAccount() { }

	public UserAccount(AccountType type, String username,
			@Nullable String password, List<Permission> permissions) {
		this.accountType = Preconditions.checkNotNull(type);
		Preconditions.checkArgument(!username.isEmpty());
		Preconditions.checkNotNull(permissions);

		this.username = username;
		this.password = password;
		// Create a copy of the list so creators can't modify the permissions.
		this.permissions = Lists.newArrayList(permissions.iterator());
	}

	@Nullable
	public String getUsername() {
		return username;
	}

	@Nullable
	public String getPassword() {
		return password;
	}

	public AccountType getAccountType() {
		return accountType;
	}

	public List<Permission> getPermissions() {
		return Lists.newArrayList(permissions.iterator());
	}
}

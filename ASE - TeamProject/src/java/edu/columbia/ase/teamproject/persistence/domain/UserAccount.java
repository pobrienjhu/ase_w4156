package edu.columbia.ase.teamproject.persistence.domain;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import edu.columbia.ase.teamproject.persistence.dao.util.ColumnLength;
import edu.columbia.ase.teamproject.persistence.domain.enumeration.AccountType;
import edu.columbia.ase.teamproject.security.Permission;

// TODO: Auto-generated Javadoc
/**
 * The Class UserAccount.
 */
@Entity
@Table(name = "UserAccount")
public class UserAccount {

	// See constraints in schema.sql.
	/** The Constant MAX_USERNAME_LENGTH. */
	public static final int MAX_USERNAME_LENGTH = 256;

	/** The Constant MAX_PASSWORD_LENGTH. */
	public static final int MAX_PASSWORD_LENGTH = 256;

	/** The Constant MAX_DISPLAY_NAME_LENGTH. */
	public static final int MAX_DISPLAY_NAME_LENGTH = 64;

	/** The Constant MAX_EMAIL_LENGTH. */
	public static final int MAX_EMAIL_LENGTH = 128;

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "Id", nullable = false)
	private Long id;

	/** The account type. */
	@Column(name = "accountType", nullable = false)
	@Enumerated(EnumType.STRING)
	private AccountType accountType;

	/** The username. */
	@Column(name = "username", nullable = false)
	@ColumnLength(value = MAX_USERNAME_LENGTH)
	private String username;

	/** The password. */
	@Column(name = "password")
	@ColumnLength(value = MAX_PASSWORD_LENGTH)
	private String password;

	/** The display name. */
	@Column(name = "displayName")
	@ColumnLength(value = MAX_DISPLAY_NAME_LENGTH)
	private String displayName;

	/** The email. */
	@Column(name = "email", nullable = false)
	@ColumnLength(value = MAX_EMAIL_LENGTH)
	private String email;

	/** The permissions. */
	@ElementCollection(fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	@CollectionTable(name = "Permission", joinColumns = @JoinColumn(name = "userId"))
	@Column(name = "permissionName")
	@Type(type = "edu.columbia.ase.teamproject.persistence.domain.enumeration.PermissionUserType")
	private List<Permission> permissions;

	/** The admin events. */
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE })
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "Admin_Event", joinColumns = { @JoinColumn(name = "userId") }, inverseJoinColumns = { @JoinColumn(name = "eventId") })
	private List<Event> adminEvents;

	/** The user events. */
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE })
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name = "User_Event", joinColumns = { @JoinColumn(name = "userId") }, inverseJoinColumns = { @JoinColumn(name = "eventId") })
	private List<Event> userEvents;

	/** The votes. */
	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REMOVE }, orphanRemoval = true, mappedBy = "userAccount")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Vote> votes;

	/** The optimistic locking version. */
	@Version
	private Integer optimisticLockingVersion;

	// A no-arg constructor is required for Hibernate.
	/**
	 * Instantiates a new user account.
	 */
	private UserAccount() {
		userEvents = new ArrayList<Event>();
		adminEvents = new ArrayList<Event>();
		votes = new ArrayList<Vote>();
	}

	/**
	 * Instantiates a new user account.
	 *
	 * @param type
	 *            the type
	 * @param username
	 *            the username
	 * @param displayName
	 *            the display name
	 * @param password
	 *            the password
	 * @param email
	 *            the email
	 * @param permissions
	 *            the permissions
	 */
	public UserAccount(AccountType type, String username,
			@Nullable String displayName, @Nullable String password,
			String email, List<Permission> permissions) {
		this();
		Preconditions.checkArgument(!username.isEmpty());
		Preconditions.checkArgument(username.length() < MAX_USERNAME_LENGTH);
		Preconditions
				.checkArgument(displayName == null
						|| (displayName != null && displayName.length() < MAX_DISPLAY_NAME_LENGTH));
		Preconditions
				.checkArgument(password == null
						|| (password != null && password.length() < MAX_PASSWORD_LENGTH));
		Preconditions.checkArgument(!email.isEmpty());
		this.accountType = Preconditions.checkNotNull(type);
		Preconditions.checkNotNull(permissions);

		this.username = username;
		this.password = password;
		this.displayName = displayName;
		this.email = email;
		// Create a copy of the list so creators can't modify the permissions.
		this.permissions = Lists.newArrayList(permissions.iterator());
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Adds the admin event.
	 *
	 * @param event
	 *            the event
	 */
	public void addAdminEvent(Event event) {
		Preconditions.checkNotNull(event);
		adminEvents.add(event);
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	@Nullable
	public String getUsername() {
		return username;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	@Nullable
	public String getPassword() {
		return password;
	}

	/**
	 * Gets the account type.
	 *
	 * @return the account type
	 */
	public AccountType getAccountType() {
		return accountType;
	}

	/**
	 * Adds the permission.
	 *
	 * @param permission
	 *            the permission
	 */
	public void addPermission(Permission permission) {
		Preconditions.checkState(!permissions.contains(permission));
		permissions.add(permission);
	}

	/**
	 * Revoke permission.
	 *
	 * @param permission
	 *            the permission
	 */
	public void revokePermission(Permission permission) {
		int idx = permissions.indexOf(permission);
		if (idx == -1) {
			throw new IllegalArgumentException();
		}
		permissions.remove(idx);
	}

	/**
	 * Gets the permissions.
	 *
	 * @return the permissions
	 */
	public List<Permission> getPermissions() {
		return Lists.newArrayList(permissions.iterator());
	}

	/**
	 * Gets the display name.
	 *
	 * @return the display name
	 */
	@Nullable
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * Sets the username.
	 *
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Sets the password.
	 *
	 * @param password
	 *            the password to set
	 */
	public void setPassword(@Nullable String password) {
		this.password = password;
	}

	/**
	 * Sets the display name.
	 *
	 * @param displayName
	 *            the displayName to set
	 */
	public void setDisplayName(@Nullable String displayName) {
		this.displayName = displayName;
	}

	/**
	 * Gets the email.
	 *
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Gets the optimistic locking version.
	 *
	 * @return the optimistic locking version
	 */
	public Integer getOptimisticLockingVersion() {
		return optimisticLockingVersion;
	}

	/**
	 * Sets the optimistic locking version.
	 *
	 * @param version
	 *            the new optimistic locking version
	 */
	public void setOptimisticLockingVersion(Integer version) {
		this.optimisticLockingVersion = version;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {

		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("id", id).append("displayName", displayName)
				.append("username", username)
				.append("accountType", accountType)
				.append("permissions", permissions)
				.append("adminEvents", Joiner.on("\n").join(adminEvents))
				.append("userEvents", Joiner.on("\n").join(userEvents))
				.toString();
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserAccount) {
			UserAccount rhs = (UserAccount) obj;
			return rhs.getAccountType().equals(accountType)
					&& rhs.getUsername().equals(username);
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return Objects.hashCode(username, accountType);
	}

	/**
	 * Removes the vote.
	 *
	 * @param vote
	 *            the vote
	 */
	public void removeVote(Vote vote) {
		Preconditions.checkNotNull(vote);
		votes.remove(vote);
	}

	/**
	 * Gets the votes.
	 *
	 * @return the votes
	 */
	public List<Vote> getVotes() {
		return votes;

	}

	/**
	 * Adds the vote.
	 *
	 * @param vote
	 *            the vote
	 */
	public void addVote(Vote vote) {
		votes.add(vote);
	}

}

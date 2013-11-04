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

@Entity
@Table(name = "UserAccount")
public class UserAccount {

	// See constraints in schema.sql.
	public static final int MAX_USERNAME_LENGTH = 256;
	public static final int MAX_PASSWORD_LENGTH = 256;
	public static final int MAX_DISPLAY_NAME_LENGTH = 64;
	public static final int MAX_EMAIL_LENGTH = 128;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "Id", nullable=false)
	private Long id;

	@Column(name="accountType", nullable=false)
	@Enumerated(EnumType.STRING)
	private AccountType accountType;

	@Column(name="username", nullable=false)
	@ColumnLength(value = MAX_USERNAME_LENGTH)
	private String username;

	@Column(name="password")
	@ColumnLength(value = MAX_PASSWORD_LENGTH)
	private String password;

	@Column(name="displayName")
	@ColumnLength(value = MAX_DISPLAY_NAME_LENGTH)
	private String displayName;

	@Column(name="email", nullable=false)
	@ColumnLength(value = MAX_EMAIL_LENGTH)
	private String email;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	@CollectionTable( 
			name="Permission",
	        joinColumns=@JoinColumn(name="userId")
	  )
	@Column(name="permissionName")
	@Type(type="edu.columbia.ase.teamproject.persistence.domain.enumeration.PermissionUserType")
	private List<Permission> permissions;

	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name="Admin_Event",
		joinColumns={@JoinColumn(name="userId")},
		inverseJoinColumns={@JoinColumn(name="eventId")})
	private List<Event> adminEvents;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name="User_Event",
    	joinColumns={@JoinColumn(name="userId")},
    	inverseJoinColumns={@JoinColumn(name="eventId")})
	private List<Event> userEvents;
	
	
	
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval=true, mappedBy="userAccount")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Vote> votes;
	
	
    @Version
    private Integer optimisticLockingVersion;
	
	// A no-arg constructor is required for Hibernate.
	private UserAccount() {
		userEvents = new ArrayList<Event>();
		adminEvents = new ArrayList<Event>();
	}

	public UserAccount(AccountType type, String username,
			@Nullable String displayName, @Nullable String password,
			String email, List<Permission> permissions) {
		this();
		Preconditions.checkArgument(!username.isEmpty());
		Preconditions.checkArgument(username.length() < MAX_USERNAME_LENGTH);
		Preconditions.checkArgument(displayName == null ||
				(displayName != null &&
				displayName.length() < MAX_DISPLAY_NAME_LENGTH));
		Preconditions.checkArgument(password == null ||
				(password != null &&
				password.length() < MAX_PASSWORD_LENGTH));
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
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public void addAdminEvent(Event event){
		Preconditions.checkNotNull(event);
		adminEvents.add(event);
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

	public void addPermission(Permission permission) {
		Preconditions.checkState(!permissions.contains(permission));
		permissions.add(permission);
	}

	public void revokePermission(Permission permission) {
		int idx = permissions.indexOf(permission);
		if (idx == -1) {
			throw new IllegalArgumentException();
		}
		permissions.remove(idx);
	}

	public List<Permission> getPermissions() {
		return Lists.newArrayList(permissions.iterator());
	}

	@Nullable
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(@Nullable String password) {
		this.password = password;
	}

	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(@Nullable String displayName) {
		this.displayName = displayName;
	}

	public String getEmail() {
		return email;
	}

	public Integer getOptimisticLockingVersion() {
		return optimisticLockingVersion;
	}


	public void setOptimisticLockingVersion(Integer version) {
		this.optimisticLockingVersion = version;
	}
	
	@Override
	public String toString() {
		
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE )
				.append("id", id)
				.append("displayName", displayName)
				.append("username", username)
				.append("accountType", accountType)
				.append("permissions", permissions)
				.append("adminEvents", Joiner.on("\n").join(adminEvents))
				.append("userEvents", Joiner.on("\n").join(userEvents))
				.toString();		
	}	

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof UserAccount) {
			UserAccount rhs = (UserAccount) obj;
			return rhs.getAccountType().equals(accountType) &&
					rhs.getUsername().equals(username);
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(username, accountType);
	}
	
	public void removeVote(Vote vote){
		Preconditions.checkNotNull(vote);
		votes.remove(vote);
	}
	
	public List<Vote> getVotes()
	{
		return votes;
	
	}
}

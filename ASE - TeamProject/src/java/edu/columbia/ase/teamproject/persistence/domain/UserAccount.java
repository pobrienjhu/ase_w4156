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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Type;

import edu.columbia.ase.teamproject.util.ToStringHelper;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import edu.columbia.ase.teamproject.persistence.dao.util.ColumnLength;
import edu.columbia.ase.teamproject.persistence.domain.enumeration.AccountType;
import edu.columbia.ase.teamproject.security.Permission;

@Entity
@Table(name = "UserAccount")
public class UserAccount {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "Id", nullable=false)
	private Long id;

	@Column(name="accountType", nullable=false)
	@Enumerated(EnumType.STRING)
	private AccountType accountType;

	@Column(name="username", nullable=false)
	@ColumnLength(value = 50)
	private String username;

	@Column(name="password")
	@ColumnLength(value = 25)
	private String password;

	@Column(name="displayName")
	@ColumnLength(value = 30)
	private String displayName;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	@CollectionTable( 
			name="Permission",
	        joinColumns=@JoinColumn(name="userId")
	  )
	@Column(name="permissionName")
	@Type(type="edu.columbia.ase.teamproject.persistence.domain.enumeration.PermissionUserType")
	private List<Permission> permissions;

	@OneToMany(mappedBy="admin", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch=FetchType.LAZY)
	private List<Event> adminEvents;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch=FetchType.LAZY)
	@JoinTable(name="User_Event",
    	joinColumns={@JoinColumn(name="userId")},
    	inverseJoinColumns={@JoinColumn(name="eventId")})
	private List<Event> userEvents;
	
	// A no-arg constructor is required for Hibernate.
	@SuppressWarnings("unused")
	private UserAccount() {  
		userEvents = new ArrayList<Event>();
		adminEvents = new ArrayList<Event>();
	}

	public UserAccount(AccountType type, String username, String displayName,
			@Nullable String password, List<Permission> permissions) {
		this.accountType = Preconditions.checkNotNull(type);
		Preconditions.checkArgument(!username.isEmpty());
		Preconditions.checkNotNull(permissions);

		this.username = username;
		this.password = password;
		this.displayName = displayName;
		// Create a copy of the list so creators can't modify the permissions.
		this.permissions = Lists.newArrayList(permissions.iterator());
	}

	
	public void addAdminEvent(Event event){
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

	public List<Permission> getPermissions() {
		return Lists.newArrayList(permissions.iterator());
	}

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
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	@Override
	public String toString() {
		
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE )
				.append("id", id)
				.append("displayName", displayName)
				.append("username", username)
				.append("accountType", accountType)
				.append("permissions", permissions)
				.append("adminEvents", ToStringHelper.toString(adminEvents) )
				.append("userEvents", ToStringHelper.toString(userEvents) )
				.toString();		
	}	
	
}

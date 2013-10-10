package edu.columbia.ase.teamproject.persistence.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import edu.columbia.ase.teamproject.persistence.dao.util.ColumnLength;
import edu.columbia.ase.teamproject.util.ToStringHelper;

@Entity
@Table(name = "Event")
public class Event {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "Id", nullable=false)
	private Long id;
	
	@ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="adminId")
	private UserAccount admin;
	
	@Column(name="name")
	@ColumnLength(value = 50)
	private String name;
	
	@Column(name="description")
	@ColumnLength(value = 255)
	private String description;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch=FetchType.LAZY)
	@JoinTable(name="User_Event",
    	joinColumns={@JoinColumn(name="eventId")},
    	inverseJoinColumns={@JoinColumn(name="userId")})
	private List<UserAccount> eventUsers;
	
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch=FetchType.LAZY, mappedBy="id")
	private List<VoteCategory> voteCategories;
	
	// A no-arg constructor is required for Hibernate.
	private Event() { 
		super(); 
		eventUsers = new ArrayList<UserAccount>();
		voteCategories = new ArrayList<VoteCategory>();
	}
	
	
	
	public Event(UserAccount admin, String name, String description) {
		this();
		this.admin = admin;
		this.name = name;
		this.description = description;
		this.admin.addAdminEvent(this);
	}


	public void addEventUser( UserAccount userAccount){
		eventUsers.add(userAccount);
	}
	
	public void addAllEventUser( Collection<UserAccount> userAccounts){
		eventUsers.addAll(userAccounts);
	}
	
	public void addVoteCategory( VoteCategory category){
		voteCategories.add(category);
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

	/**
	 * @return the admin
	 */
	public UserAccount getAdmin() {
		return admin;
	}

	/**
	 * @param admin the admin to set
	 */
	public void setAdmin(UserAccount admin) {
		this.admin = admin;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("id", id)
				.append("name", name)
				.append("description", description)
				.append("admin", admin != null ? admin.getDisplayName() : "")
				.append("eventUsers", ToStringHelper.toString(translateEventUsers(eventUsers)))
				.append("voteCategories", ToStringHelper.toString(voteCategories))
				.toString();		
	}	
	
	/*
	 * Used for toString Method and used for testing as well.
	 */
	private List<String> translateEventUsers( List<UserAccount> eventUsers){
		List<String> stringList = new ArrayList<String>();
		
		for( UserAccount userAccount: eventUsers){
			stringList.add( new StringBuilder().append("User: ").append(userAccount.getDisplayName()).toString());
		}
		
		return stringList;
	}
	

	
}

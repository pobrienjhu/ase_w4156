package edu.columbia.ase.teamproject.persistence.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;

import edu.columbia.ase.teamproject.persistence.dao.util.ColumnLength;
import edu.columbia.ase.teamproject.persistence.domain.enumeration.EventType;

@Entity
@Table(name = "Event")
public class Event {
	
	@Transient
	private List<String>userEmails;

	private static final int MAX_NAME_LENGTH = 50;
	private static final int MAX_DESCRIPTION_LENGTH = 255;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "Id", nullable=false)
	private Long id;
	
	@Column(name="name", nullable = false)
	@ColumnLength(value = MAX_NAME_LENGTH)
	private String name;
	
	@Column(name="description")
	@ColumnLength(value = MAX_DESCRIPTION_LENGTH)
	private String description;


	@Column(name="startTime", nullable=false)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime startTime;
	
	@Column(name="endTime", nullable=false)
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
	private DateTime endTime;
	
	@Column(name="eventType", nullable=false)
	@Enumerated(EnumType.STRING)
	private EventType eventType;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name="User_Event",
    	joinColumns={@JoinColumn(name="eventId")},
    	inverseJoinColumns={@JoinColumn(name="userId")})
	private List<UserAccount> eventUsers;
	
	
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name="Admin_Event",
    	joinColumns={@JoinColumn(name="eventId")},
    	inverseJoinColumns={@JoinColumn(name="userId")})
	private List<UserAccount> adminUsers;
	
	@OneToMany(cascade = {CascadeType.ALL}, orphanRemoval=true, mappedBy="event")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<VoteCategory> voteCategories;
	
    @Version
    @Column(name="version")
    private Integer optimisticLockingVersion;
	
	// A no-arg constructor is required for Hibernate.
	private Event() { 
		super(); 
		adminUsers = new ArrayList<UserAccount>();
		eventUsers = new ArrayList<UserAccount>();
		voteCategories = new ArrayList<VoteCategory>();
	}

	/**
	 * @param admin if null, MUST be set before persisting to database.
	 */
	public Event(@Nullable List<String>userEmails,@Nullable UserAccount admin, String name, String description,
			EventType eventType, DateTime eventStart,
			DateTime eventEnd, List<VoteCategory> voteCategories) {
		this();
		Preconditions.checkArgument(name.length() < MAX_NAME_LENGTH);
		Preconditions.checkArgument(description.length() < MAX_DESCRIPTION_LENGTH);
		if (admin != null) { 
			adminUsers.add(admin);
		}
		this.eventType = Preconditions.checkNotNull(eventType);
		this.startTime = Preconditions.checkNotNull(eventStart);
		this.endTime = Preconditions.checkNotNull(eventEnd);
		this.name = name;
		this.description = description;
		for (VoteCategory category : voteCategories) {
			addVoteCategory(category);
		}
		this.userEmails = userEmails;
	}

	/**
	 * @param admin if null, MUST be set before persisting to database.
	 */
	public Event(@Nullable List<String> userEmails, @Nullable UserAccount admin, String name, String description,
			EventType eventType, DateTime eventStart,
			DateTime eventEnd) {
		this(userEmails,admin, name, description, eventType, eventStart, eventEnd,
				Collections.<VoteCategory> emptyList());
	}
	

	
	public void addAdminUser(UserAccount userAccount){
		Preconditions.checkNotNull(userAccount);
		adminUsers.add(userAccount);
	}
	
	public void addAllAdminUser(Collection<UserAccount> userAccounts){
		Preconditions.checkNotNull(userAccounts);
		adminUsers.addAll(userAccounts);
	}

	public void addEventUser(UserAccount userAccount){
		Preconditions.checkNotNull(userAccount);
		eventUsers.add(userAccount);
	}
	
	public void addAllEventUser(Collection<UserAccount> userAccounts){
		Preconditions.checkNotNull(userAccounts);
		eventUsers.addAll(userAccounts);
	}
	
	public void addVoteCategory(VoteCategory category){
		Preconditions.checkNotNull(category);
		voteCategories.add(category);
		category.setEvent(this);
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		Preconditions.checkNotNull(eventType);
		this.eventType = eventType;
	}

	public List<VoteCategory> getVoteCategories() {
		return voteCategories;
	}

	public void setVoteCategories(List<VoteCategory> voteCategories) {
		Preconditions.checkNotNull(voteCategories);
		this.voteCategories = voteCategories;
	}

	public Integer getOptimisticLockingVersion() {
		return optimisticLockingVersion;
	}

	public void setOptimisticLockingVersion(Integer version) {
		this.optimisticLockingVersion = version;
	}

	/**
	 * @return the eventUsers
	 */
	public List<UserAccount> getEventUsers() {
		return eventUsers;
	}

	/**
	 * @return the adminUsers
	 */
	public List<UserAccount> getAdminUsers() {
		return adminUsers;
	}

	/**
	 * @return the id, or null if no ID set (e.g. this event hasn't been persisted).
	 */
	@Nullable
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

	public DateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(DateTime startTime) {
		this.startTime = startTime;
	}

	public DateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(DateTime endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("id", id)
				.append("name", name)
				.append("description", description)
				.append("startTime", startTime)
				.append("endTime", endTime)
				.append("eventType", eventType)
				.append("eventUsers", Joiner.on("\n").join(translateEventUsers(eventUsers)))
				.append("adminUsers", Joiner.on("\n").join(translateEventUsers(adminUsers)))
				.append("voteCategories", Joiner.on("\n").join(voteCategories))
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
	
	public List<String> getUserEmails()
	{
		return userEmails;
	}
	public void setUserEmails(List<String>userEmails)
	{
		this.userEmails=userEmails;
	}
	

}

package edu.columbia.ase.teamproject.persistence.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import com.google.common.base.Joiner;

import edu.columbia.ase.teamproject.persistence.dao.util.ColumnLength;
import edu.columbia.ase.teamproject.persistence.domain.enumeration.EventType;

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
	
	@Column(name="name", nullable = false)
	@ColumnLength(value = 50)
	private String name;
	
	@Column(name="description")
	@ColumnLength(value = 255)
	private String description;
	
	@Column(name="startTime")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime startTime;
	
	@Column(name="endTime")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime endTime;
	
	@Column(name="eventType", nullable=false)
	@Enumerated(EnumType.STRING)
	private EventType eventType;
	
	@ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch=FetchType.LAZY)
	@JoinTable(name="User_Event",
    	joinColumns={@JoinColumn(name="eventId")},
    	inverseJoinColumns={@JoinColumn(name="userId")})
	private List<UserAccount> eventUsers;
	
	@OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch=FetchType.LAZY, mappedBy="id")
	private List<VoteCategory> voteCategories;
	
    @Version
    @Column(name="version")
    private Integer optimisticLockingVersion;
	
	// A no-arg constructor is required for Hibernate.
	private Event() { 
		super(); 
		eventUsers = new ArrayList<UserAccount>();
		voteCategories = new ArrayList<VoteCategory>();
	}

	public Event(UserAccount admin, String name, String description, EventType eventType) {
		this();
		// TODO(pames): determine which fields should be required / non-null, and
		// add Precondition checks for them.
		this.admin = admin;
		this.name = name;
		this.description = description;
		this.eventType = eventType;
	}

	public void addEventUser(UserAccount userAccount){
		eventUsers.add(userAccount);
	}
	
	public void addAllEventUser(Collection<UserAccount> userAccounts){
		eventUsers.addAll(userAccounts);
	}
	
	public void addVoteCategory(VoteCategory category){
		voteCategories.add(category);
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}

	public List<VoteCategory> getVoteCategories() {
		return voteCategories;
	}

	public void setVoteCategories(List<VoteCategory> voteCategories) {
		this.voteCategories = voteCategories;
	}

	public Integer getOptimisticLockingVersion() {
		return optimisticLockingVersion;
	}

	public void setOptimisticLockingVersion(Integer version) {
		this.optimisticLockingVersion = version;
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

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
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
}

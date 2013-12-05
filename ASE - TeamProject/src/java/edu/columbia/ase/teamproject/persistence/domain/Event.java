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

// TODO: Auto-generated Javadoc
/**
 * The Class Event.
 */
@Entity
@Table(name = "Event")
public class Event {

    /** The user emails. */
    @Transient
    private List<String> userEmails;

    /** The Constant MAX_NAME_LENGTH. */
    private static final int MAX_NAME_LENGTH = 50;

    /** The Constant MAX_DESCRIPTION_LENGTH. */
    private static final int MAX_DESCRIPTION_LENGTH = 255;

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    /** The name. */
    @Column(name = "name", nullable = false)
    @ColumnLength(value = MAX_NAME_LENGTH)
    private String name;

    /** The description. */
    @Column(name = "description")
    @ColumnLength(value = MAX_DESCRIPTION_LENGTH)
    private String description;

    /** The start time. */
    @Column(name = "startTime", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime startTime;

    /** The end time. */
    @Column(name = "endTime", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime endTime;

    /** The event type. */
    @Column(name = "eventType", nullable = false)
    @Enumerated(EnumType.STRING)
    private EventType eventType;

    /** The event users. */
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "User_Event", joinColumns = { @JoinColumn(name = "eventId") }, inverseJoinColumns = { @JoinColumn(name = "userId") })
    private List<UserAccount> eventUsers;

    /** The admin users. */
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinTable(name = "Admin_Event", joinColumns = { @JoinColumn(name = "eventId") }, inverseJoinColumns = { @JoinColumn(name = "userId") })
    private List<UserAccount> adminUsers;

    /** The vote categories. */
    @OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true, mappedBy = "event")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<VoteCategory> voteCategories;

    /** The optimistic locking version. */
    @Version
    @Column(name = "version")
    private Integer optimisticLockingVersion;

    // A no-arg constructor is required for Hibernate.
    /**
     * Instantiates a new event.
     */
    private Event() {
        super();
        adminUsers = new ArrayList<UserAccount>();
        eventUsers = new ArrayList<UserAccount>();
        voteCategories = new ArrayList<VoteCategory>();
    }

    /**
     * Instantiates a new event.
     * 
     * @param userEmails
     *            the user emails
     * @param admin
     *            if null, MUST be set before persisting to database.
     * @param name
     *            the name
     * @param description
     *            the description
     * @param eventType
     *            the event type
     * @param eventStart
     *            the event start
     * @param eventEnd
     *            the event end
     * @param voteCategories
     *            the vote categories
     */
    public Event(@Nullable List<String> userEmails, @Nullable UserAccount admin, String name,
            String description, EventType eventType, DateTime eventStart, DateTime eventEnd,
            List<VoteCategory> voteCategories) {
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
     * Instantiates a new event.
     * 
     * @param userEmails
     *            the user emails
     * @param admin
     *            if null, MUST be set before persisting to database.
     * @param name
     *            the name
     * @param description
     *            the description
     * @param eventType
     *            the event type
     * @param eventStart
     *            the event start
     * @param eventEnd
     *            the event end
     */
    public Event(@Nullable List<String> userEmails, @Nullable UserAccount admin, String name,
            String description, EventType eventType, DateTime eventStart, DateTime eventEnd) {
        this(userEmails, admin, name, description, eventType, eventStart, eventEnd, Collections
                .<VoteCategory> emptyList());
    }

    /**
     * Adds the admin user.
     * 
     * @param userAccount
     *            the user account
     */
    public void addAdminUser(UserAccount userAccount) {
        Preconditions.checkNotNull(userAccount);
        adminUsers.add(userAccount);
    }

    /**
     * Adds the all admin user.
     * 
     * @param userAccounts
     *            the user accounts
     */
    public void addAllAdminUser(Collection<UserAccount> userAccounts) {
        Preconditions.checkNotNull(userAccounts);
        adminUsers.addAll(userAccounts);
    }

    /**
     * Adds the event user.
     * 
     * @param userAccount
     *            the user account
     */
    public void addEventUser(UserAccount userAccount) {
        Preconditions.checkNotNull(userAccount);
        eventUsers.add(userAccount);
    }

    /**
     * Adds the all event user.
     * 
     * @param userAccounts
     *            the user accounts
     */
    public void addAllEventUser(Collection<UserAccount> userAccounts) {
        Preconditions.checkNotNull(userAccounts);
        eventUsers.addAll(userAccounts);
    }

    /**
     * Adds the vote category.
     * 
     * @param category
     *            the category
     */
    public void addVoteCategory(VoteCategory category) {
        Preconditions.checkNotNull(category);
        voteCategories.add(category);
        category.setEvent(this);
    }

    /**
     * Gets the event type.
     * 
     * @return the event type
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * Sets the event type.
     * 
     * @param eventType
     *            the new event type
     */
    public void setEventType(EventType eventType) {
        Preconditions.checkNotNull(eventType);
        this.eventType = eventType;
    }

    /**
     * Gets the vote categories.
     * 
     * @return the vote categories
     */
    public List<VoteCategory> getVoteCategories() {
        return voteCategories;
    }

    /**
     * Sets the vote categories.
     * 
     * @param voteCategories
     *            the new vote categories
     */
    public void setVoteCategories(List<VoteCategory> voteCategories) {
        Preconditions.checkNotNull(voteCategories);
        this.voteCategories = voteCategories;
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

    /**
     * Gets the event users.
     * 
     * @return the eventUsers
     */
    public List<UserAccount> getEventUsers() {
        return eventUsers;
    }

    /**
     * Gets the admin users.
     * 
     * @return the adminUsers
     */
    public List<UserAccount> getAdminUsers() {
        return adminUsers;
    }

    /**
     * Gets the id.
     * 
     * @return the id, or null if no ID set (e.g. this event hasn't been
     *         persisted).
     */
    @Nullable
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
     * Gets the name.
     * 
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * 
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the description.
     * 
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description.
     * 
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the start time.
     * 
     * @return the start time
     */
    public DateTime getStartTime() {
        return startTime;
    }

    /**
     * Sets the start time.
     * 
     * @param startTime
     *            the new start time
     */
    public void setStartTime(DateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the end time.
     * 
     * @return the end time
     */
    public DateTime getEndTime() {
        return endTime;
    }

    /**
     * Sets the end time.
     * 
     * @param endTime
     *            the new end time
     */
    public void setEndTime(DateTime endTime) {
        this.endTime = endTime;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("id", id)
                .append("name", name).append("description", description)
                .append("startTime", startTime).append("endTime", endTime)
                .append("eventType", eventType)
                .append("eventUsers", Joiner.on("\n").join(translateEventUsers(eventUsers)))
                .append("adminUsers", Joiner.on("\n").join(translateEventUsers(adminUsers)))
                .append("voteCategories", Joiner.on("\n").join(voteCategories)).toString();
    }

    /*
     * Used for toString Method and used for testing as well.
     */
    /**
     * Translate event users.
     * 
     * @param eventUsers
     *            the event users
     * @return the list
     */
    private List<String> translateEventUsers(List<UserAccount> eventUsers) {
        List<String> stringList = new ArrayList<String>();

        for (UserAccount userAccount : eventUsers) {
            stringList.add(new StringBuilder().append("User: ")
                    .append(userAccount.getDisplayName()).toString());
        }

        return stringList;
    }

    /**
     * Gets the user emails.
     * 
     * @return the user emails
     */
    public List<String> getUserEmails() {
        return userEmails;
    }

    /**
     * Sets the user emails.
     * 
     * @param userEmails
     *            the new user emails
     */
    public void setUserEmails(List<String> userEmails) {
        this.userEmails = userEmails;
    }

}

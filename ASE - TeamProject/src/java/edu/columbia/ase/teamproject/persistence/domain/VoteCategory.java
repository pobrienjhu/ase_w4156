package edu.columbia.ase.teamproject.persistence.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;

import edu.columbia.ase.teamproject.persistence.dao.util.ColumnLength;

// TODO: Auto-generated Javadoc
/**
 * The Class VoteCategory.
 */
@Entity
@Table(name = "VoteCategory")
public class VoteCategory {

    /** The Constant MAX_NAME_LENGTH. */
    private static final int MAX_NAME_LENGTH = 50;

    /** The Constant MAX_DESCRIPTION_LENGTH. */
    private static final int MAX_DESCRIPTION_LENGTH = 255;

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    /** The event. */
    @ManyToOne(targetEntity = Event.class)
    // , fetch = FetchType.LAZY)
    @JoinColumn(name = "eventId")
    private Event event;

    /** The category name. */
    @Column(name = "categoryName")
    @ColumnLength(value = MAX_NAME_LENGTH)
    private String categoryName;

    /** The description. */
    @Column(name = "description")
    @ColumnLength(value = MAX_DESCRIPTION_LENGTH)
    private String description;

    /** The vote options. */
    @OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true, mappedBy = "voteCategory")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<VoteOption> voteOptions;

    /** The optimistic locking version. */
    @Version
    private Integer optimisticLockingVersion;

    // A no-arg constructor is required for Hibernate.
    /**
     * Instantiates a new vote category.
     */
    private VoteCategory() {
        voteOptions = new ArrayList<VoteOption>();
    }

    /**
     * Instantiates a new vote category.
     * 
     * @param event
     *            the event
     * @param categoryName
     *            the category name
     * @param description
     *            the description
     */
    public VoteCategory(Event event, String categoryName, String description) {
        this(categoryName, description);
        this.event = Preconditions.checkNotNull(event);
    }

    /**
     * Instantiates a new vote category.
     * 
     * @param categoryName
     *            the category name
     * @param description
     *            the description
     */
    public VoteCategory(String categoryName, String description) {
        this();
        Preconditions.checkArgument(categoryName.length() < MAX_NAME_LENGTH);
        Preconditions.checkArgument(description.length() < MAX_DESCRIPTION_LENGTH);
        this.categoryName = categoryName;
        this.description = description;
    }

    /**
     * Adds the voting option.
     * 
     * @param voteOption
     *            the vote option
     */
    public void addVotingOption(VoteOption voteOption) {
        voteOptions.add(voteOption);
        voteOption.setVoteCategory(this);
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
     * Gets the event.
     * 
     * @return the event
     */
    public Event getEvent() {
        return event;
    }

    /**
     * Sets the event.
     * 
     * @param event
     *            the event to set
     */
    public void setEvent(Event event) {
        this.event = Preconditions.checkNotNull(event);
    }

    /**
     * Gets the category name.
     * 
     * @return the categoryName
     */
    public String getCategoryName() {
        return categoryName;
    }

    /**
     * Sets the category name.
     * 
     * @param categoryName
     *            the categoryName to set
     */
    public void setCategoryName(String categoryName) {
        Preconditions.checkArgument(categoryName.length() < MAX_NAME_LENGTH);
        this.categoryName = categoryName;
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
        Preconditions.checkArgument(description.length() < MAX_DESCRIPTION_LENGTH);
        this.description = description;
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
     * Gets the vote options.
     * 
     * @return the vote options
     */
    public List<VoteOption> getVoteOptions() {
        return voteOptions;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("id", id)
                .append("categoryName", categoryName).append("event", event.getName())
                .append("description", description)
                .append("voteOptions", Joiner.on("\n").join(voteOptions)).toString();
    }
}

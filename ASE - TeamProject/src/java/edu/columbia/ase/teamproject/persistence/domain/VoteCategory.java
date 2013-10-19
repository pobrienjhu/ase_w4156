package edu.columbia.ase.teamproject.persistence.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;

import edu.columbia.ase.teamproject.persistence.dao.util.ColumnLength;

@Entity
@Table(name = "VoteCategory")
public class VoteCategory {

	private static final int MAX_NAME_LENGTH = 50;
	private static final int MAX_DESCRIPTION_LENGTH = 255;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "Id", nullable=false)
	private Long id;
	
	@ManyToOne(targetEntity = Event.class) //, fetch = FetchType.LAZY)
    @JoinColumn(name="eventId")
	private Event event;
	
	@Column(name="categoryName")
	@ColumnLength(value = MAX_NAME_LENGTH)
	private String categoryName;
	
	@Column(name="description")
	@ColumnLength(value = MAX_DESCRIPTION_LENGTH)
	private String description;
	
	@OneToMany(cascade = {CascadeType.ALL}, fetch=FetchType.LAZY, orphanRemoval=true, mappedBy="voteCategory")
	private List<VoteOption> voteOptions;
	
    @Version
    private Integer optimisticLockingVersion;
	
	// A no-arg constructor is required for Hibernate.
	private VoteCategory() { 
		voteOptions = new ArrayList<VoteOption>();
	}

	public VoteCategory(Event event, String categoryName, String description) {
		this(categoryName, description);
		this.event = Preconditions.checkNotNull(event);
	}
	
	public VoteCategory(String categoryName, String description) {
		this();
		Preconditions.checkArgument(categoryName.length() < MAX_NAME_LENGTH);
		Preconditions.checkArgument(description.length() < MAX_DESCRIPTION_LENGTH);
		this.categoryName = categoryName;
		this.description = description;
	}

	public void addVotingOption(VoteOption voteOption){
		voteOptions.add(voteOption);
		voteOption.setVoteCategory(this);
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
	 * @return the event
	 */
	public Event getEvent() {
		return event;
	}

	/**
	 * @param event the event to set
	 */
	public void setEvent(Event event) {
		this.event = Preconditions.checkNotNull(event);
	}

	/**
	 * @return the categoryName
	 */
	public String getCategoryName() {
		return categoryName;
	}

	/**
	 * @param categoryName the categoryName to set
	 */
	public void setCategoryName(String categoryName) {
		Preconditions.checkArgument(categoryName.length() < MAX_NAME_LENGTH);
		this.categoryName = categoryName;
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
		Preconditions.checkArgument(description.length() < MAX_DESCRIPTION_LENGTH);
		this.description = description;
	}
	
	public Integer getOptimisticLockingVersion() {
		return optimisticLockingVersion;
	}

	public void setOptimisticLockingVersion(Integer version) {
		this.optimisticLockingVersion = version;
	}

	public List<VoteOption> getVoteOptions() {
		return voteOptions;
	}

	@Override
	public String toString() {
		
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("id", id)
				.append("categoryName", categoryName)
				.append("event", event.getName())
				.append("description", description)
				.append("voteOptions", Joiner.on("\n").join(voteOptions))
				.toString();		
	}	
}

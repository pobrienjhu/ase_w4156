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

import com.google.common.base.Preconditions;

import edu.columbia.ase.teamproject.persistence.dao.util.ColumnLength;

@Entity
@Table(name = "VoteOption")
public class VoteOption {

	private static final int MAX_NAME_LENGTH = 50;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "Id", nullable=false)
	private Long id;
	
	@ManyToOne(targetEntity = VoteCategory.class) //cascade={CascadeType.MERGE})
    @JoinColumn(name="voteCategoryId")
	private VoteCategory voteCategory;
	
	@Column(name="optionName")
	@ColumnLength(value = MAX_NAME_LENGTH)
	private String optionName;

	@OneToMany(cascade = {CascadeType.ALL}, fetch=FetchType.LAZY, orphanRemoval=true, mappedBy="id")
	private List<Vote> votes;
	
    @Version
    private Integer optimisticLockingVersion;
	
	// A no-arg constructor is required for Hibernate.
	private VoteOption() {
		super();
		votes = new ArrayList<Vote>();
	}

	public VoteOption(VoteCategory voteCategory, String optionName) {
		this(optionName);
		this.voteCategory = Preconditions.checkNotNull(voteCategory);
	}
	
	public VoteOption(String optionName) {
		this();
		Preconditions.checkArgument(optionName.length() < MAX_NAME_LENGTH);
		this.optionName = optionName;
	}	
	
	/**
	 * @return the votes
	 */
	public List<Vote> getVotes() {
		return votes;
	}


	/**
	 * @param votes the votes to set
	 */
	public void setVotes(List<Vote> votes) {
		this.votes = votes;
	}


	public void addVote(Vote vote){
		Preconditions.checkNotNull(vote);
		votes.add(vote);
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
	 * @return the voteCategory
	 */
	public VoteCategory getVoteCategory() {
		return voteCategory;
	}

	/**
	 * @param voteCategory the voteCategory to set
	 */
	public void setVoteCategory(VoteCategory voteCategory) {
		this.voteCategory = Preconditions.checkNotNull(voteCategory);
	}

	/**
	 * @return the optionName
	 */
	public String getOptionName() {
		return optionName;
	}

	/**
	 * @param optionName the optionName to set
	 */
	public void setOptionName(String optionName) {
		Preconditions.checkArgument(optionName.length() < MAX_NAME_LENGTH);
		this.optionName = optionName;
	}
	
	public Integer getOptimisticLockingVersion() {
		return optimisticLockingVersion;
	}

	public void setOptimisticLockingVersion(Integer version) {
		this.optimisticLockingVersion = version;
	}
	
	
	@Override
	public String toString() {
		
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("id", id)
				.append("optionName", optionName)
				.append("category", voteCategory.getCategoryName())
				.toString();		
	}
	
	
}

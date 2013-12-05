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

import com.google.common.base.Preconditions;

import edu.columbia.ase.teamproject.persistence.dao.util.ColumnLength;

// TODO: Auto-generated Javadoc
/**
 * The Class VoteOption.
 */
@Entity
@Table(name = "VoteOption")
public class VoteOption {

    /** The Constant MAX_NAME_LENGTH. */
    private static final int MAX_NAME_LENGTH = 50;

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    /** The vote category. */
    @ManyToOne(targetEntity = VoteCategory.class)
    // cascade={CascadeType.MERGE})
    @JoinColumn(name = "voteCategoryId")
    private VoteCategory voteCategory;

    /** The option name. */
    @Column(name = "optionName")
    @ColumnLength(value = MAX_NAME_LENGTH)
    private String optionName;

    /** The votes. */
    @OneToMany(/* cascade = {CascadeType.ALL} */cascade = { CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.REMOVE }, orphanRemoval = true, mappedBy = "voteOption")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Vote> votes;

    /** The optimistic locking version. */
    @Version
    private Integer optimisticLockingVersion;

    // A no-arg constructor is required for Hibernate.
    /**
     * Instantiates a new vote option.
     */
    public VoteOption() {
        super();
        votes = new ArrayList<Vote>();
    }

    /**
     * Instantiates a new vote option.
     * 
     * @param voteCategory
     *            the vote category
     * @param optionName
     *            the option name
     */
    public VoteOption(VoteCategory voteCategory, String optionName) {
        this(optionName);
        this.voteCategory = Preconditions.checkNotNull(voteCategory);
    }

    /**
     * Instantiates a new vote option.
     * 
     * @param optionName
     *            the option name
     */
    public VoteOption(String optionName) {
        this();
        Preconditions.checkArgument(optionName.length() < MAX_NAME_LENGTH);
        this.optionName = optionName;
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
     * Sets the votes.
     * 
     * @param votes
     *            the votes to set
     */
    public void setVotes(List<Vote> votes) {
        this.votes = votes;
    }

    /**
     * Adds the vote.
     * 
     * @param vote
     *            the vote
     */
    public void addVote(Vote vote) {
        Preconditions.checkNotNull(vote);
        votes.add(vote);
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
     * Gets the vote category.
     * 
     * @return the voteCategory
     */
    public VoteCategory getVoteCategory() {
        return voteCategory;
    }

    /**
     * Sets the vote category.
     * 
     * @param voteCategory
     *            the voteCategory to set
     */
    public void setVoteCategory(VoteCategory voteCategory) {
        this.voteCategory = Preconditions.checkNotNull(voteCategory);
    }

    /**
     * Gets the option name.
     * 
     * @return the optionName
     */
    public String getOptionName() {
        return optionName;
    }

    /**
     * Sets the option name.
     * 
     * @param optionName
     *            the optionName to set
     */
    public void setOptionName(String optionName) {
        Preconditions.checkArgument(optionName.length() < MAX_NAME_LENGTH);
        this.optionName = optionName;
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

        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("id", id)
                .append("optionName", optionName)
                .append("category", voteCategory.getCategoryName()).toString();
    }

}

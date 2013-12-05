package edu.columbia.ase.teamproject.persistence.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

// TODO: Auto-generated Javadoc
/**
 * The Class Vote.
 */
@Entity
@Table(name = "Vote")
public class Vote {

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false)
    private Long id;

    /** The vote time. */
    @Column(name = "voteTime")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
    private LocalDateTime voteTime;

    /** The vote option. */
    @ManyToOne(targetEntity = VoteOption.class)
    /*
     * (cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
     * /*(cascade={CascadeType.ALL})
     */
    @JoinColumn(name = "voteOptionId")
    private VoteOption voteOption;

    /** The user account. */
    @ManyToOne(targetEntity = UserAccount.class)
    /* (cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}) *//*
                                                                                    * (
                                                                                    * cascade
                                                                                    * =
                                                                                    * {
                                                                                    * CascadeType
                                                                                    * .
                                                                                    * ALL
                                                                                    * }
                                                                                    * )
                                                                                    */
    @JoinColumn(name = "userId")
    private UserAccount userAccount;

    /** The optimistic locking version. */
    @Version
    @Column(name = "version")
    private Integer optimisticLockingVersion;

    /** The vote category id. */
    @Column(name = "voteCategoryId")
    private Long voteCategoryId;

    /**
     * Instantiates a new vote.
     */
    public Vote() {
        super();
    }

    /**
     * Instantiates a new vote.
     * 
     * @param voteOption
     *            the vote option
     * @param userAccount
     *            the user account
     */
    public Vote(VoteOption voteOption, UserAccount userAccount) {
        this(voteOption, userAccount, new LocalDateTime());
    }

    /**
     * Instantiates a new vote.
     * 
     * @param voteOption
     *            the vote option
     * @param userAccount
     *            the user account
     * @param voteTime
     *            the vote time
     */
    public Vote(VoteOption voteOption, UserAccount userAccount, LocalDateTime voteTime) {
        this();
        this.voteOption = voteOption;
        this.userAccount = userAccount;
        this.voteTime = voteTime;
        this.voteCategoryId = this.voteOption.getVoteCategory().getId();
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
     * Gets the vote option.
     * 
     * @return the voteOption
     */
    public VoteOption getVoteOption() {
        return voteOption;
    }

    /**
     * Sets the vote option.
     * 
     * @param voteOption
     *            the voteOption to set
     */
    public void setVoteOption(VoteOption voteOption) {
        this.voteOption = voteOption;
    }

    /**
     * Gets the user account.
     * 
     * @return the userAccount
     */
    public UserAccount getUserAccount() {
        return userAccount;
    }

    /**
     * Sets the user account.
     * 
     * @param userAccount
     *            the userAccount to set
     */
    public void setUserAccount(UserAccount userAccount) {
        this.userAccount = userAccount;
    }

    /**
     * Gets the vote time.
     * 
     * @return the vote time
     */
    public LocalDateTime getVoteTime() {
        return voteTime;
    }

    /**
     * Sets the vote time.
     * 
     * @param voteTime
     *            the new vote time
     */
    public void setVoteTime(LocalDateTime voteTime) {
        this.voteTime = voteTime;
    }

    /**
     * Gets the vote category id.
     * 
     * @return the VoteCategoryId
     */
    public Long getVoteCategoryId() {
        return this.voteCategoryId;
    }

    /**
     * Sets the vote category id.
     * 
     * @param id
     *            the VoteCategoryId to set
     */
    public void setVoteCategoryId(Long id) {
        this.voteCategoryId = id;
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
     * @param optimisticLockingVersion
     *            the new optimistic locking version
     */
    public void setOptimisticLockingVersion(Integer optimisticLockingVersion) {
        this.optimisticLockingVersion = optimisticLockingVersion;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {

        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append("id", id)
                .append("voteOption", voteOption).append("voteTime", voteTime)
                .append("userAccount", userAccount.getUsername()).toString();
    }

}

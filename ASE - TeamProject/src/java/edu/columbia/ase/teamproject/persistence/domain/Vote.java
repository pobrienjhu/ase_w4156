package edu.columbia.ase.teamproject.persistence.domain;

import javax.persistence.CascadeType;
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

@Entity
@Table(name = "Vote")
public class Vote {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "Id", nullable=false)
	private Long id;
	
	@Column(name="voteTime")
	@Type(type="org.jadira.usertype.dateandtime.joda.PersistentLocalDateTime")
	private LocalDateTime voteTime;
	
	@ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="voteOptionId")
	private VoteOption voteOption;
	
	@ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="userId")
	private UserAccount userAccount;

	
	public Vote() {
		super();
	}

	public Vote(VoteOption voteOption, UserAccount userAccount) {
		this(voteOption, userAccount, new LocalDateTime());
	}
	
	public Vote(VoteOption voteOption, UserAccount userAccount, LocalDateTime voteTime) {
		this();
		this.voteOption = voteOption;
		this.userAccount = userAccount;
		this.voteTime = voteTime;
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
	 * @return the voteOption
	 */
	public VoteOption getVoteOption() {
		return voteOption;
	}

	/**
	 * @param voteOption the voteOption to set
	 */
	public void setVoteOption(VoteOption voteOption) {
		this.voteOption = voteOption;
	}

	/**
	 * @return the userAccount
	 */
	public UserAccount getUserAccount() {
		return userAccount;
	}

	/**
	 * @param userAccount the userAccount to set
	 */
	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	
	public LocalDateTime getVoteTime() {
		return voteTime;
	}

	public void setVoteTime(LocalDateTime voteTime) {
		this.voteTime = voteTime;
	}
	
	@Override
	public String toString() {
		
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("id", id)
				.append("voteOption", voteOption)
				.append("voteTime", voteTime)
				.append("userAccount", userAccount.getUsername() )
				.toString();		
	}
	

}

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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "Vote")
public class Vote {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "Id", nullable=false)
	private Long id;
	
	
	@ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="voteOptionId")
	private VoteOption voteOption;
	
	@ManyToOne(cascade={CascadeType.ALL})
    @JoinColumn(name="userId")
	private UserAccount userAccount;

	private Vote() {
		super();
		// TODO Auto-generated constructor stub
	}

	private Vote(VoteOption voteOption, UserAccount userAccount) {
		super();
		this.voteOption = voteOption;
		this.userAccount = userAccount;
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
	
	
	@Override
	public String toString() {
		
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("id", id)
				.append("voteOption", voteOption)
				.append("userAccount", userAccount.getUsername() )
				.toString();		
	}
	

}

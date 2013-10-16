package edu.columbia.ase.teamproject.persistence.dao;

import org.springframework.security.core.userdetails.UserDetails;

import edu.columbia.ase.teamproject.persistence.dao.generic.GenericDao;
import edu.columbia.ase.teamproject.persistence.domain.UserAccount;
import edu.columbia.ase.teamproject.persistence.domain.enumeration.AccountType;

public interface UserAccountDao extends GenericDao<UserAccount, Long> {

	public UserAccount findAccountByNameAndType(String username,
			AccountType type);

	public UserAccount findAccountByUserDetails(UserDetails userDetails);

	public long getNumberOfUsers();

	public UserAccount findAccountByName(String username);
}

package edu.columbia.ase.teamproject.persistence.dao;

import edu.columbia.ase.teamproject.persistence.domain.UserAccount;
import edu.columbia.ase.teamproject.persistence.domain.enumeration.AccountType;

import edu.columbia.ase.teamproject.persistence.dao.generic.GenericDao;

public interface UserAccountDao extends GenericDao<UserAccount, Long> {

	public UserAccount findAccountByNameAndType(String username,
			AccountType type);

	public long getNumberOfUsers();
}

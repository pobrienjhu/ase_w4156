package edu.columbia.ase.teamproject.persistence.dao;

import org.springframework.security.core.userdetails.UserDetails;

import edu.columbia.ase.teamproject.persistence.dao.generic.GenericDao;
import edu.columbia.ase.teamproject.persistence.domain.UserAccount;
import edu.columbia.ase.teamproject.persistence.domain.enumeration.AccountType;

// TODO: Auto-generated Javadoc
/**
 * The Interface UserAccountDao.
 */
public interface UserAccountDao extends GenericDao<UserAccount, Long> {

	/**
	 * Find account by name and type.
	 *
	 * @param username the username
	 * @param type the type
	 * @return the user account
	 */
	public UserAccount findAccountByNameAndType(String username,
			AccountType type);

	/**
	 * Find account by user details.
	 *
	 * @param userDetails the user details
	 * @return the user account
	 */
	public UserAccount findAccountByUserDetails(UserDetails userDetails);

	/**
	 * Gets the number of users.
	 *
	 * @return the number of users
	 */
	public long getNumberOfUsers();

	/**
	 * Find account by email.
	 *
	 * @param email the email
	 * @return the user account
	 */
	public UserAccount findAccountByEmail(String email);
}

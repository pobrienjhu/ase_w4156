package edu.columbia.ase.teamproject.persistence.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;

import edu.columbia.ase.teamproject.persistence.dao.HibernateDao;
import edu.columbia.ase.teamproject.persistence.dao.UserAccountDao;
import edu.columbia.ase.teamproject.persistence.domain.UserAccount;
import edu.columbia.ase.teamproject.persistence.domain.UserAccount.AccountType;

@Transactional(propagation = Propagation.REQUIRED)
public class UserAccountDaoImpl extends HibernateDao<UserAccount, Long>
	implements UserAccountDao {

	private static final Logger logger =
			LoggerFactory.getLogger(UserAccountDaoImpl.class);

	@Override
	public UserAccount findAccountByNameAndType(String username,
			AccountType type) {
		Preconditions.checkNotNull(username);
		Preconditions.checkNotNull(type);

		Criteria criteria = currentSession().createCriteria(daoType)
				.add(Restrictions.eq("username", username))
				.add(Restrictions.eq("accountType", type));

		@SuppressWarnings("unchecked")
		List<UserAccount> accounts = (List<UserAccount>) criteria.list();

		if (accounts.isEmpty()) {
			return null;
		} else if (accounts.size() == 1) {
			return accounts.get(0);
		}
		logger.warn("findAccountByNameAndType({0}, {1}) returned {2} results", 
				username, type, accounts.size()); 
		return null;
	}

	@Override
	public long getNumberOfUsers() {
		Criteria criteria = currentSession().createCriteria(daoType)
				.setProjection(Projections.rowCount());

		@SuppressWarnings("unchecked")
		Number count = (Number) criteria.uniqueResult(); 
		return count.longValue();
	}
}
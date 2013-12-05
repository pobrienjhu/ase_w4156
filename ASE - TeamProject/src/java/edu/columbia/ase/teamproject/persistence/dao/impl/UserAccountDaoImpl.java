package edu.columbia.ase.teamproject.persistence.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;

import edu.columbia.ase.teamproject.persistence.dao.UserAccountDao;
import edu.columbia.ase.teamproject.persistence.dao.generic.HibernateDao;
import edu.columbia.ase.teamproject.persistence.domain.UserAccount;
import edu.columbia.ase.teamproject.persistence.domain.enumeration.AccountType;
import edu.columbia.ase.teamproject.security.Permission;

// TODO: Auto-generated Javadoc
/**
 * The Class UserAccountDaoImpl.
 */
@Transactional(propagation = Propagation.REQUIRED)
public class UserAccountDaoImpl extends HibernateDao<UserAccount, Long> implements UserAccountDao {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(UserAccountDaoImpl.class);

    /*
     * (non-Javadoc)
     *
     * @see edu.columbia.ase.teamproject.persistence.dao.UserAccountDao#
     * findAccountByNameAndType(java.lang.String,
     * edu.columbia.ase.teamproject.persistence.domain.enumeration.AccountType)
     */
    @Override
    public UserAccount findAccountByNameAndType(final String username, final AccountType type) {
        Preconditions.checkNotNull(username);
        Preconditions.checkNotNull(type);

        final Criteria criteria = currentSession().createCriteria(daoType)
                .add(Restrictions.eq("username", username))
                .add(Restrictions.eq("accountType", type));

        return (UserAccount) criteria.uniqueResult();
    }

    /*
     * (non-Javadoc)
     *
     * @see edu.columbia.ase.teamproject.persistence.dao.UserAccountDao#
     * findAccountByUserDetails
     * (org.springframework.security.core.userdetails.UserDetails)
     */
    @Override
    public UserAccount findAccountByUserDetails(final UserDetails userDetails) {
        Preconditions.checkNotNull(userDetails);
        AccountType type = null;
        for (final GrantedAuthority authority : userDetails.getAuthorities()) {
            if (Permission.OPENID.toString().equals(authority.getAuthority())) {
                type = AccountType.OPENID;
                break;
            } else if (Permission.LOCAL.toString().equals(authority.getAuthority())) {
                type = AccountType.LOCAL;
            }
        }

        return findAccountByNameAndType(userDetails.getUsername(), type);
    }

    /*
     * (non-Javadoc)
     *
     * @see edu.columbia.ase.teamproject.persistence.dao.UserAccountDao#
     * findAccountByEmail(java.lang.String)
     */
    @Override
    public UserAccount findAccountByEmail(final String email) {
        try {
            Preconditions.checkArgument(!email.isEmpty());
        } catch (final Exception e) {
            logger.warn("findAccountByEmail exception", e);
            return null;
        }

        final Criteria criteria = currentSession().createCriteria(daoType)
                .add(Restrictions.eq("email", email))
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

        return (UserAccount) criteria.uniqueResult();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * edu.columbia.ase.teamproject.persistence.dao.UserAccountDao#getNumberOfUsers
     * ()
     */
    @Override
    public long getNumberOfUsers() {
        final Criteria criteria = currentSession().createCriteria(daoType).setProjection(
                Projections.rowCount());

        final Number count = (Number) criteria.uniqueResult();
        return count.longValue();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * edu.columbia.ase.teamproject.persistence.dao.generic.HibernateDao#list()
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<UserAccount> list() {
        return currentSession().createCriteria(UserAccount.class)
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
    }

}

package edu.columbia.ase.teamproject.persistence.dao.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.columbia.ase.teamproject.persistence.dao.VoteOptionDao;
import edu.columbia.ase.teamproject.persistence.dao.generic.HibernateDao;
import edu.columbia.ase.teamproject.persistence.domain.VoteOption;

/**
 * The Class VoteOptionDaoImpl.
 */
@Transactional(propagation = Propagation.REQUIRED)
public class VoteOptionDaoImpl extends HibernateDao<VoteOption, Long> implements VoteOptionDao {

}

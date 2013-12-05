package edu.columbia.ase.teamproject.persistence.dao.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.columbia.ase.teamproject.persistence.dao.VoteDao;
import edu.columbia.ase.teamproject.persistence.dao.generic.HibernateDao;
import edu.columbia.ase.teamproject.persistence.domain.Vote;

/**
 * The Class VoteDaoImpl.
 */
@Transactional(propagation = Propagation.REQUIRED)
public class VoteDaoImpl extends HibernateDao<Vote, Long> implements VoteDao {

}
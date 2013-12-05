package edu.columbia.ase.teamproject.persistence.dao.impl;


import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.columbia.ase.teamproject.persistence.dao.VoteCategoryDao;
import edu.columbia.ase.teamproject.persistence.dao.generic.HibernateDao;
import edu.columbia.ase.teamproject.persistence.domain.VoteCategory;

/**
 * The Class VoteCategoryDaoImpl.
 */
@Transactional(propagation = Propagation.REQUIRED)
public class VoteCategoryDaoImpl extends HibernateDao<VoteCategory, Long> implements VoteCategoryDao {

	
}

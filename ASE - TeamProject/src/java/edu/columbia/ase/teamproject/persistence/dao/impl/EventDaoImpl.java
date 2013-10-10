package edu.columbia.ase.teamproject.persistence.dao.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.columbia.ase.teamproject.persistence.dao.EventDao;
import edu.columbia.ase.teamproject.persistence.dao.generic.HibernateDao;
import edu.columbia.ase.teamproject.persistence.domain.Event;

@Transactional(propagation = Propagation.REQUIRED)
public class EventDaoImpl extends HibernateDao<Event, Long> implements EventDao {

	
}

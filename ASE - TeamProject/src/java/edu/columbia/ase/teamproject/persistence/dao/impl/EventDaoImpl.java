package edu.columbia.ase.teamproject.persistence.dao.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.joda.time.DateTime;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.columbia.ase.teamproject.persistence.dao.EventDao;
import edu.columbia.ase.teamproject.persistence.dao.generic.HibernateDao;
import edu.columbia.ase.teamproject.persistence.domain.Event;

@Transactional(propagation = Propagation.REQUIRED)
public class EventDaoImpl extends HibernateDao<Event, Long> implements EventDao {

	@Override
	public Collection<Event> getAllPublicEvents(DateTime start, DateTime end){
		return new ArrayList<Event>();
	}
	
}

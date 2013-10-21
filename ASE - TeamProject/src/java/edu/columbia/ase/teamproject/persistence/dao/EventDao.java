package edu.columbia.ase.teamproject.persistence.dao;

import java.util.Collection;

import org.joda.time.DateTime;

import edu.columbia.ase.teamproject.persistence.dao.generic.GenericDao;
import edu.columbia.ase.teamproject.persistence.domain.Event;

public interface EventDao extends GenericDao<Event, Long> {
	
	public Collection<Event> getAllPublicEvents(DateTime start, DateTime end);
	
}

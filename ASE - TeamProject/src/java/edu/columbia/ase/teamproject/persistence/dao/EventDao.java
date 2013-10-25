package edu.columbia.ase.teamproject.persistence.dao;

import java.util.Collection;

import org.joda.time.DateTime;

import edu.columbia.ase.teamproject.persistence.dao.generic.GenericDao;
import edu.columbia.ase.teamproject.persistence.domain.Event;

public interface EventDao extends GenericDao<Event, Long> {
	
	public Collection<Event> getAllActivePublicEvents(DateTime currentTime);

	public Collection<Event> getAllActivePrivateEventsForUserId(DateTime currentTime, Long userId);
	
	public Collection<Event> getAllCompletedPublicEvents(DateTime currentTime);

	public Collection<Event> getAllCompletedPrivateEventsForUserId(DateTime currentTime, Long userId);
	
	public Collection<Event>  getAllActiveAdminEventsForUserId(DateTime currentTime, Long userId);
	
	public Collection<Event>  getAllCompletedAdminEventsForUserId(DateTime currentTime, Long userId);

	public Collection<Event>  getAllFutureAdminEventsForUserId(DateTime currentTime, Long userId);
	
}

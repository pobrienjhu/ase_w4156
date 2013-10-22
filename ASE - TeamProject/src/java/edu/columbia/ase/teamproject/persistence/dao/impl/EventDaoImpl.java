package edu.columbia.ase.teamproject.persistence.dao.impl;

import java.util.Collection;

import org.joda.time.DateTime;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.columbia.ase.teamproject.persistence.dao.EventDao;
import edu.columbia.ase.teamproject.persistence.dao.generic.HibernateDao;
import edu.columbia.ase.teamproject.persistence.domain.Event;
import edu.columbia.ase.teamproject.persistence.domain.enumeration.EventType;

@Transactional(propagation = Propagation.REQUIRED)
public class EventDaoImpl extends HibernateDao<Event, Long> implements EventDao {

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Event> getAllPublicEvents(DateTime currentTime){
		
		return currentSession().createQuery(
				"From Event e "+
				"Where e.eventType = :et "+
				"and :ct between e.startTime and e.endTime  ")
				.setParameter("et", EventType.PUBLIC )
				.setParameter("ct", currentTime ).list();				
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Event> getAllPrivateEventsForUserId(DateTime currentTime,
			Long userId) {
		
		return currentSession().createQuery(
				"From Event e "+
				"inner join e.eventUsers eventUsers "+ 
				"Where e.eventType = :et "+
				"and eventUsers.id = :uId "+
				"and :ct between e.startTime and e.endTime ")
				.setParameter("uId", userId )
				.setParameter("et", EventType.PRIVATE )
				.setParameter("ct", currentTime ).list();	
		
	}
	
	
}

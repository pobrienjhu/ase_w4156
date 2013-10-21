/**
 * 
 */
package edu.columbia.ase.teamproject.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.base.Preconditions;

import edu.columbia.ase.teamproject.persistence.dao.EventDao;
import edu.columbia.ase.teamproject.persistence.dao.UserAccountDao;
import edu.columbia.ase.teamproject.persistence.domain.Event;
import edu.columbia.ase.teamproject.persistence.domain.UserAccount;
import edu.columbia.ase.teamproject.persistence.domain.VoteCategory;
import edu.columbia.ase.teamproject.persistence.domain.enumeration.EventType;

/**
 * @author aiman
 *
 */
public class EventService {
	private static final Logger logger =
			LoggerFactory.getLogger(EventService.class);

	@Autowired
	UserAccountDao userDao;
	
	@Autowired
	EventDao eventDao;

	/*
	 * try to retrieve all the public events. 
	 * If there is an exception, log it and return and empty ArrayList
	 */
	public Collection<Event> getAllPublicEvents(DateTime start, DateTime end){
		try {
			return eventDao.getAllPublicEvents(start, end);
		} catch (Exception e) {
			logger.error("Unable to retrieve public events. Root Cause ("+e.getLocalizedMessage()+")");
		}
		return new ArrayList<Event>();
	}
	
	
	public Event createEvent(UserAccount requestor, String name, String description, EventType eventType,
			DateTime start, DateTime end)
	{
		Preconditions.checkNotNull(requestor);

		logger.info("Creating event " + name);
		Event event = new Event(requestor, name, description, eventType,
				start, end);

		logger.info("Event " + name + " created");
		
		return eventDao.add(event);
	}
	
	public Event createEvent(UserAccount requestor, String name, String description, EventType eventType,
			DateTime start, DateTime end, List<VoteCategory> voteCategories)
	{
		
		Event event = this.createEvent(requestor, name, description, eventType, start, end);
		for (VoteCategory v : voteCategories)
		{
			v.setEvent(event);
		}
		event.setVoteCategories(voteCategories);
		
		return eventDao.add(event);
	}	

	public Event lookupEvent(UserAccount requestor, Long id) {
		Preconditions.checkNotNull(requestor);
		Preconditions.checkNotNull(id);

		Event event = eventDao.find(id);
		if (event == null) {
			return null;
		}

		switch (event.getEventType()) {
		case PUBLIC:
			return event;
		case PRIVATE:
			// TODO(pames): add tests for this
			if (event.getAdminUsers().contains(requestor) ||
				event.getEventUsers().contains(requestor)) {
				return event;
			}
		}
		return null;
	}
}

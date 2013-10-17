/**
 * 
 */
package edu.columbia.ase.teamproject.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.google.common.base.Preconditions;

import edu.columbia.ase.teamproject.persistence.dao.EventDao;
import edu.columbia.ase.teamproject.persistence.dao.UserAccountDao;
import edu.columbia.ase.teamproject.persistence.domain.Event;
import edu.columbia.ase.teamproject.persistence.domain.UserAccount;
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

	public Event createEvent(UserAccount creator, String name, String description, EventType eventType)
	throws UsernameNotFoundException
	{
		Preconditions.checkNotNull(creator);
		logger.info("Creating event " + name);
		Event event = new Event(creator, name, description, eventType);

		logger.info("Event " + name + " created");
		
		return eventDao.add(event);
	}

}

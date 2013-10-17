/**
 * 
 */
package edu.columbia.ase.teamproject.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.columbia.ase.teamproject.persistence.dao.EventDao;
import edu.columbia.ase.teamproject.persistence.dao.UserAccountDao;
import edu.columbia.ase.teamproject.persistence.domain.Event;
import edu.columbia.ase.teamproject.persistence.domain.UserAccount;
import edu.columbia.ase.teamproject.persistence.domain.enumeration.AccountType;
import edu.columbia.ase.teamproject.persistence.domain.enumeration.EventType;
import edu.columbia.ase.teamproject.security.OpenIdAuthenticationTokenConsumer;

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
	
	public Event createEvent(String username, String name, String description, EventType eventType)
	throws UsernameNotFoundException
	{
		
		logger.info("Creating event " + name);
		
		// Validate account
		UserAccount acc = userDao.findAccountByName(username);
		
		if (acc == null)
		{
			logger.info("Did not find username: " + username);
			throw new UsernameNotFoundException("No account with name " + username + " found");
		}
		
		Event event = new Event(acc, name, description, eventType);

		return eventDao.add(event);
	}
	
	

}

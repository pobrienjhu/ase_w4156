/**
 * 
 */
package edu.columbia.ase.teamproject.event;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.columbia.ase.teamproject.persistence.dao.EventDao;
import edu.columbia.ase.teamproject.persistence.dao.UserAccountDao;
import edu.columbia.ase.teamproject.persistence.domain.Event;
import edu.columbia.ase.teamproject.persistence.domain.UserAccount;
import edu.columbia.ase.teamproject.persistence.domain.enumeration.AccountType;
import edu.columbia.ase.teamproject.persistence.domain.enumeration.EventType;
import edu.columbia.ase.teamproject.security.Permission;
import edu.columbia.ase.teamproject.services.EventService;

/**
 * @author aiman
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class EventServiceTest {

	
	 @Autowired
	 private UserAccountDao userAccountDao;

	 @Autowired
	 private EventDao eventDao;
	 
	 @Autowired
	 private EventService eventService;

	 @Before
	 public void setUp() {
		 UserAccount user = new UserAccount(AccountType.LOCAL, "user",
				 "displayName", "password",
				 Arrays.asList(new Permission[]{Permission.USER}));
		 userAccountDao.add(user);
	 }

	/**
	 * Test method for {@link edu.columbia.ase.teamproject.services.EventService#createEvent(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testSuccessfulCreateEvent() {
		Event e = eventService.createEvent("user", "Event Name", "Event Description", EventType.PRIVATE);
		assertTrue(eventDao.list().size() == 1);
		assertTrue(eventDao.find(e.getId()) != null);
	}

}

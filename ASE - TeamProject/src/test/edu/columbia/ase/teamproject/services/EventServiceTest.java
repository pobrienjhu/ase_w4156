/**
 * 
 */
package edu.columbia.ase.teamproject.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableList;

import edu.columbia.ase.teamproject.persistence.dao.EventDao;
import edu.columbia.ase.teamproject.persistence.dao.UserAccountDao;
import edu.columbia.ase.teamproject.persistence.domain.Event;
import edu.columbia.ase.teamproject.persistence.domain.UserAccount;
import edu.columbia.ase.teamproject.persistence.domain.VoteCategory;
import edu.columbia.ase.teamproject.persistence.domain.VoteOption;
import edu.columbia.ase.teamproject.persistence.domain.enumeration.AccountType;
import edu.columbia.ase.teamproject.persistence.domain.enumeration.EventType;
import edu.columbia.ase.teamproject.security.Permission;

/**
 * @author aiman
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@Transactional
public class EventServiceTest extends AbstractTransactionalJUnit4SpringContextTests {

	
	 @Autowired
	 private UserAccountDao userAccountDao;

	 @Autowired
	 private EventDao eventDao;
	 
	 @Autowired
	 private EventService eventService;

	 @Autowired
	 private SessionFactory sessionFactory;

	 private UserAccount userAccount; 

	 @Before
	 public void setUp() {
		 UserAccount user = new UserAccount(AccountType.LOCAL, "user",
				 "displayName", "password",
				 Arrays.asList(new Permission[]{Permission.USER}));
		 userAccount = userAccountDao.add(user);
	 }

	/**
	 * Test method for {@link EventService#createEvent(UserDetails, String, String, EventType)}.
	 */
	@Test
	public void testSuccessfulCreateEvent() {
		Event e = eventService.createEvent(userAccount, "Test Event Name",
				"Event Description", EventType.PRIVATE, DateTime.now(),
				DateTime.now().plus(Duration.standardDays(1)));

		long eventId = e.getId();
		List<Event> events = eventDao.list();
		assertTrue(events.size() == 1);

		Event fetchedEvent = eventDao.find(eventId);
		// TODO: when Event implements equals(), replace w/this
		//assertEquals(fetchedEvent, e);
		assertNotNull(fetchedEvent);

		// Make a change to a referenced property to imitate a client modifying the serialized state.
		fetchedEvent.getAdmin().setPassword("bogus");
		// Legitimately change a property of the event.
		fetchedEvent.setDescription("new description");

		Session session = sessionFactory.getCurrentSession();
		session.save(fetchedEvent);

		// Retrieve the most recent copy from the database.
		UserAccount fetchedUser = userAccountDao.findAccountByNameAndType("user",
				AccountType.LOCAL);

		fetchedEvent = eventDao.find(eventId);

		// This change should be permitted.
		assertTrue(fetchedEvent.getDescription().equals("new description"));

		// This should not be permitted, or else anyone can overwrite any record in our database
		assertFalse(fetchedUser.getPassword().equals("bogus"));
	}

	@Test
	public void testCreateEventWithVotingCategories() {
		Event e = eventService.createEvent(userAccount, "Test Event Name",
				"Event Description", EventType.PRIVATE, DateTime.now(),
				DateTime.now().plus(Duration.standardDays(1)));

		VoteCategory firstCategory = new VoteCategory(e, "First Category",
				"First Category Description");
		VoteOption firstCategoryFirstOption =
				new VoteOption(firstCategory, "Option 1-1");
		VoteOption firstCategorySecondOption =
				new VoteOption(firstCategory, "Option 1-2");
		VoteOption firstCategoryThirdOption =
				new VoteOption(firstCategory, "Option 1-3");
		firstCategory.addVotingOption(firstCategoryFirstOption);
		firstCategory.addVotingOption(firstCategorySecondOption);
		firstCategory.addVotingOption(firstCategoryThirdOption);

		VoteCategory secondCategory = new VoteCategory(e, "Second Category",
				"First Category Description");
		VoteOption secondCategoryFirstOption =
				new VoteOption(firstCategory, "Option 2-1");
		VoteOption secondCategorySecondOption =
				new VoteOption(firstCategory, "Option 2-2");
		secondCategory.addVotingOption(secondCategoryFirstOption);
		secondCategory.addVotingOption(secondCategorySecondOption);

		e.setVoteCategories(ImmutableList.<VoteCategory>of(firstCategory,
				secondCategory));

		// HACK: this should probably be an eventService 'update' method.
		// TODO(pames/aiman): determine if there should be a createEvent() API
		// which also takes a List<VoteCategory> so these can be passed in on
		// initial creation.
		sessionFactory.getCurrentSession().save(e);

		Event fetchedEvent = eventDao.find(e.getId()); 
		assertEquals(2, fetchedEvent.getVoteCategories().size());
		// TODO(pames): confirm that order will be preserved for vote
		// categories.  Doesn't seem like it necessarily would be.
		assertEquals(3, fetchedEvent.getVoteCategories().get(0).getVoteOptions().size());
		assertEquals(2, fetchedEvent.getVoteCategories().get(1).getVoteOptions().size());

		// Verify that these elements were indeed transitively persisted and have IDs.
		assertNotNull(fetchedEvent.getVoteCategories().get(0).getId());
		assertNotNull(fetchedEvent.getVoteCategories().get(0).getVoteOptions().get(0).getId());
	}
}

/**
 *
 */
package edu.columbia.ase.teamproject.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
import edu.columbia.ase.teamproject.services.exceptions.ValidationException;

/**
 * @author aiman
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
@Transactional
public class EventServiceTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	@Autowired
	private UserAccountDao userAccountDao;

	@Autowired
	private EventDao eventDao;

	@Autowired
	private EventService eventService;

	@Autowired
	private SessionFactory sessionFactory;

	private UserAccount userAccount;

	private List<VoteCategory> createBogusCategories() {
		VoteCategory vc1 = new VoteCategory("category 1",
				"category 1 description");
		VoteCategory vc2 = new VoteCategory("category 2",
				"category 2 description");
		vc1.addVotingOption(new VoteOption(vc1, "1-1"));
		vc1.addVotingOption(new VoteOption(vc1, "1-2"));
		vc2.addVotingOption(new VoteOption(vc2, "2-1"));
		vc2.addVotingOption(new VoteOption(vc2, "2-2"));
		return ImmutableList.<VoteCategory> of(vc1, vc2);
	}

	@Before
	public void setUp() {
		UserAccount user = new UserAccount(AccountType.LOCAL, "user",
				"displayName", "password", "user@example.com",
				Arrays.asList(new Permission[] { Permission.USER }));
		userAccount = userAccountDao.add(user);
	}

	/**
	 * Test method for
	 * {@link EventService#createEvent(UserDetails, String, String, EventType)}.
	 *
	 * @throws ValidationException
	 */
	@Test
	public void testSuccessfulCreateEvent() throws ValidationException {
		Event e = eventService.createEvent(userAccount, "Test Event Name",
				"Event Description", EventType.PRIVATE, DateTime.now(),
				DateTime.now().plus(Duration.standardDays(1)),
				createBogusCategories(), new ArrayList<String>());

		long eventId = e.getId();
		List<Event> events = eventDao.list();
		assertTrue(events.size() == 1);

		Event fetchedEvent = eventDao.find(eventId);
		// TODO: when Event implements equals(), replace w/this
		// assertEquals(fetchedEvent, e);
		assertEquals("Test Event Name", fetchedEvent.getName());
		assertEquals("Event Description", fetchedEvent.getDescription());
		assertEquals(EventType.PRIVATE, fetchedEvent.getEventType());
	}

	@Test
	public void testUpdateEvent() throws ValidationException {
		Event e = eventService.createEvent(userAccount, "Test Event Name",
				"Event Description", EventType.PRIVATE,
				DateTime.now().plus(Duration.standardDays(1)), DateTime.now()
						.plus(Duration.standardDays(2)),
				createBogusCategories(), new ArrayList<String>());
		long eventId = e.getId();

		UserAccount deserializedUserAccount = new UserAccount(
				AccountType.LOCAL, "user", "bogus displayName", "bogus",
				"user@example.com",
				Arrays.asList(new Permission[] { Permission.USER }));
		deserializedUserAccount.setId(userAccount.getId());
		Event deserializedEvent = new Event(null, deserializedUserAccount,
				"new event name", "new description", EventType.PUBLIC, DateTime
						.now().plus(Duration.standardDays(2)), DateTime.now()
						.plus(Duration.standardDays(3)),
				Collections.<VoteCategory> emptyList());
		deserializedEvent.setId(eventId);
		deserializedEvent.addEventUser(deserializedUserAccount);

		eventService.updateEvent(userAccount, eventId, deserializedEvent);

		// Retrieve the most recent copy from the database.
		UserAccount fetchedUser = userAccountDao.findAccountByNameAndType(
				"user", AccountType.LOCAL);

		Event fetchedEvent = eventDao.find(eventId);

		// This change should be permitted.
		assertTrue(fetchedEvent.getDescription().equals("new description"));
		assertEquals(1, fetchedEvent.getEventUsers().size());

		// This should not be permitted, or else anyone can overwrite any record
		// in our database
		assertFalse(fetchedUser.getPassword().equals("bogus"));
	}

	@Test
	public void testCreateEventWithVotingCategories()
			throws ValidationException {

		VoteCategory firstCategory = new VoteCategory("First Category",
				"First Category Description");
		VoteOption firstCategoryFirstOption = new VoteOption(firstCategory,
				"Option 1-1");
		VoteOption firstCategorySecondOption = new VoteOption(firstCategory,
				"Option 1-2");
		VoteOption firstCategoryThirdOption = new VoteOption(firstCategory,
				"Option 1-3");
		firstCategory.addVotingOption(firstCategoryFirstOption);
		firstCategory.addVotingOption(firstCategorySecondOption);
		firstCategory.addVotingOption(firstCategoryThirdOption);

		VoteCategory secondCategory = new VoteCategory("Second Category",
				"Second Category Description");
		VoteOption secondCategoryFirstOption = new VoteOption(firstCategory,
				"Option 2-1");
		VoteOption secondCategorySecondOption = new VoteOption(firstCategory,
				"Option 2-2");
		secondCategory.addVotingOption(secondCategoryFirstOption);
		secondCategory.addVotingOption(secondCategorySecondOption);

		Event e = eventService.createEvent(userAccount, "Votable Event Name",
				"Event Description", EventType.PRIVATE, DateTime.now(),
				DateTime.now().plus(Duration.standardDays(1)),
				ImmutableList.<VoteCategory> of(firstCategory, secondCategory),
				new ArrayList<String>());

		sessionFactory.getCurrentSession().save(e);

		Event fetchedEvent = eventDao.find(e.getId());
		assertEquals(2, fetchedEvent.getVoteCategories().size());
		// TODO(pames): confirm that order will be preserved for vote
		// categories. Doesn't seem like it necessarily would be.
		assertEquals(3, fetchedEvent.getVoteCategories().get(0)
				.getVoteOptions().size());
		assertEquals(2, fetchedEvent.getVoteCategories().get(1)
				.getVoteOptions().size());

		// Verify that these elements were indeed transitively persisted and
		// have IDs.
		assertNotNull(fetchedEvent.getVoteCategories().get(0).getId());
		assertNotNull(fetchedEvent.getVoteCategories().get(0).getVoteOptions()
				.get(0).getId());
	}

	@Test
	public void testEventAdminCanUpdateEvent() {
		UserAccount userA = new UserAccount(AccountType.LOCAL, "foo", "foo",
				null, "userA@example.com", Collections.<Permission> emptyList());
		UserAccount userB = new UserAccount(AccountType.LOCAL, "bar", "bar",
				null, "userB@example.com", Collections.<Permission> emptyList());

		assertFalse(userA.equals(userB));

		assertTrue(eventService.userCanUpdateEvent(userA, null));

		Event testEvent = new Event(null, userA, "event name", "description",
				EventType.PRIVATE, DateTime.now().plus(
						Duration.standardMinutes(1)), DateTime.now().plus(
						Duration.standardDays(1)));
		testEvent = eventDao.add(testEvent);

		assertTrue(eventService.userCanUpdateEvent(userA, testEvent.getId()));
		assertFalse(eventService.userCanUpdateEvent(userB, testEvent.getId()));

		testEvent.addAdminUser(userB);
		eventDao.add(testEvent);
		assertTrue(eventService.userCanUpdateEvent(userB, testEvent.getId()));
	}

	@Test
	public void testEventCannotBeUpdatedAfterStarting() {
		UserAccount userA = new UserAccount(AccountType.LOCAL, "foo", "foo",
				null, "userA@example.com", Collections.<Permission> emptyList());
		Event testEvent = new Event(null, userA, "event name", "description",
				EventType.PRIVATE, DateTime.now(), DateTime.now().plus(
						Duration.standardDays(1)));
		testEvent = eventDao.add(testEvent);

		assertFalse(eventService.userCanUpdateEvent(userA, testEvent.getId()));

	}

	@Test
	public void testValidateEventWithInvalidCategory()
			throws ValidationException {
		UserAccount admin = new UserAccount(AccountType.LOCAL, "foo", "foo",
				null, "admin@example.com", Collections.<Permission> emptyList());

		VoteCategory firstCategory = new VoteCategory("Category 1",
				"Description");
		firstCategory.addVotingOption(new VoteOption("first option name"));
		firstCategory.addVotingOption(new VoteOption("second option name"));

		Event firstEvent = eventService.createEvent(admin, "event name",
				"description", EventType.PRIVATE, DateTime.now(), DateTime
						.now().plus(Duration.standardDays(1)), ImmutableList
						.<VoteCategory> of(firstCategory),
				new ArrayList<String>());

		Event secondEvent = new Event(null, admin, "event name", "description",
				EventType.PRIVATE, DateTime.now(), DateTime.now().plus(
						Duration.standardDays(1)));
		VoteCategory replacementFirstCategory = new VoteCategory(
				"Fake Category", "Description");
		replacementFirstCategory.setId(firstEvent.getVoteCategories().get(0)
				.getId());
		secondEvent.addVoteCategory(replacementFirstCategory);

		try {
			eventService.validateEvent(secondEvent);
			fail("should not validate");
		} catch (IllegalStateException ex) {
			assertTrue(ex.getMessage().contains("existing voteCategory"));
			assertTrue(ex.getMessage().contains("event mismatch"));
			// expected
		}
	}

	@Test
	public void testValidateEventWithInvalidOption() throws ValidationException {
		UserAccount admin = new UserAccount(AccountType.LOCAL, "foo", "foo",
				null, "admin@example.com", Collections.<Permission> emptyList());

		VoteCategory firstCategory = new VoteCategory("Category 1",
				"Description");
		firstCategory.addVotingOption(new VoteOption("first option name"));
		firstCategory.addVotingOption(new VoteOption("second option name"));

		Event firstEvent = eventService.createEvent(admin, "event name",
				"description", EventType.PRIVATE, DateTime.now(), DateTime
						.now().plus(Duration.standardDays(1)), ImmutableList
						.<VoteCategory> of(firstCategory),
				new ArrayList<String>());

		Event secondEvent = new Event(null, admin, "event name", "description",
				EventType.PRIVATE, DateTime.now(), DateTime.now().plus(
						Duration.standardDays(1)));
		VoteCategory secondCategory = new VoteCategory("category", "desc");
		VoteOption replacementVoteOption = new VoteOption("bogus");
		replacementVoteOption.setId(firstEvent.getVoteCategories().get(0)
				.getVoteOptions().get(0).getId());
		secondCategory.addVotingOption(replacementVoteOption);

		secondEvent.addVoteCategory(secondCategory);

		try {
			eventService.validateEvent(secondEvent);
			fail("should not validate");
		} catch (IllegalStateException ex) {
			assertTrue(ex.getMessage().contains("existing option"));
			assertTrue(ex.getMessage().contains("category mismatch"));
			// expected
		}
	}
}

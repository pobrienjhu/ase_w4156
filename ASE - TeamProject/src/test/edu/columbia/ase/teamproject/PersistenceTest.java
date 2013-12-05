package edu.columbia.ase.teamproject;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.gson.Gson;

import edu.columbia.ase.teamproject.persistence.dao.EventDao;
import edu.columbia.ase.teamproject.persistence.dao.UserAccountDao;
import edu.columbia.ase.teamproject.persistence.dao.VoteCategoryDao;
import edu.columbia.ase.teamproject.persistence.dao.VoteDao;
import edu.columbia.ase.teamproject.persistence.dao.VoteOptionDao;
import edu.columbia.ase.teamproject.persistence.domain.Event;
import edu.columbia.ase.teamproject.persistence.domain.UserAccount;
import edu.columbia.ase.teamproject.persistence.domain.Vote;
import edu.columbia.ase.teamproject.persistence.domain.VoteCategory;
import edu.columbia.ase.teamproject.persistence.domain.VoteOption;
import edu.columbia.ase.teamproject.persistence.domain.enumeration.AccountType;
import edu.columbia.ase.teamproject.persistence.domain.enumeration.EventType;
import edu.columbia.ase.teamproject.security.Permission;
import edu.columbia.ase.teamproject.util.GsonProvider;
import edu.columbia.ase.teamproject.view.NavigationMenuEntry;
import edu.columbia.ase.teamproject.view.NavigationMenuSection;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-db.xml")
public class PersistenceTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	// static {
	// System.setProperty("dataFile", "test-data.sql"); }

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private UserAccountDao userAccountDao;

	@Autowired
	private EventDao eventDao;

	@Autowired
	private VoteCategoryDao voteCategoryDao;

	@Autowired
	private VoteOptionDao voteOptionDao;

	@Autowired
	private VoteDao voteDao;

	Gson gson;

	private long eventId;
	private long userId;
	private long voId;

	@Before
	public void setUp() {
		gson = new GsonProvider().provideGson();
		/*
		 * Add users
		 */

		UserAccount user1 = new UserAccount(AccountType.LOCAL, "user1",
				"User1 Account", "password", "user1@example.com",
				Arrays.asList(new Permission[] { Permission.USER }));

		UserAccount user2 = new UserAccount(AccountType.LOCAL, "user2",
				"User2 Account", "password", "user2@example.com",
				Arrays.asList(new Permission[] { Permission.USER }));

		UserAccount admin = new UserAccount(AccountType.LOCAL, "admin",
				"Admin Account", "password", "user3@example.com",
				Arrays.asList(new Permission[] { Permission.USER }));

		user1 = userAccountDao.add(user1);

		user2 = userAccountDao.add(user2);
		admin = userAccountDao.add(admin);
		userId = user1.getId();
		/*
		 * Add Events
		 */
		Event event = new Event(null, admin, "Event Name", "Event Description",
				EventType.PUBLIC, DateTime.now(), DateTime.now().plus(
						Duration.standardDays(1)));

		event.addEventUser(user1);
		event.addEventUser(user2);

		event = eventDao.add(event);
		eventId = event.getId();
		eventDao.flush();

		/*
		 * Add voting categories
		 */

		VoteCategory category1 = new VoteCategory("category1", "Category 1");
		VoteCategory category2 = new VoteCategory("category2", "Category 2");

		category1.addVotingOption(new VoteOption("option 1a"));
		// voId = category1.getVoteOptions().get(0).getId();
		category1.addVotingOption(new VoteOption("option 1b"));
		category1.addVotingOption(new VoteOption("option 1c"));

		category2.addVotingOption(new VoteOption("option 2a"));
		category2.addVotingOption(new VoteOption("option 2b"));
		category2.addVotingOption(new VoteOption("option 2c"));

		event.addVoteCategory(category1);
		event.addVoteCategory(category2);

		event = eventDao.update(event);

		for (VoteCategory category : event.getVoteCategories()) {
			category.addVotingOption(new VoteOption("option 4"));
		}

		event = eventDao.update(event);

		System.out.println(event);

	}

	@AfterClass
	public static void tearDown() {
		// System.clearProperty("dataFile");
	}

	@Test
	public void testHibernateConfiguration() {
		// Did Spring container instantiated and prepared sessionFactory
		assertNotNull(sessionFactory);
	}

	@Test
	public void testUserAccountDao() {
		// Did Spring container instantiated and prepared personsDao
		assertNotNull(userAccountDao);
	}

	@Test
	public void testUserAccountDaoQuery() {
		// Is personsDao ready for operations
		List<UserAccount> userAccountList = userAccountDao.list();

		System.out.println("Looking for list of all persons in the db...");

		// personsList.size()
		assertNotNull(userAccountList);
		assertFalse(userAccountList.size() == 0);

		for (UserAccount userAccount : userAccountList) {
			System.out.println(userAccount);
		}
	}

	@Test
	public void testUserAccountsDaoInsert() {
		// Is personsDao ready for operations
		List<Permission> permissions = new ArrayList<Permission>();
		permissions.add(Permission.ADMIN);
		UserAccount patrick = new UserAccount(AccountType.LOCAL,
				"patrick.obrien.8474@gmail.com", "Patrick OBrien", "password",
				"patrick.obrien.8474@gmail.com", permissions);

		userAccountDao.add(patrick);

	}

	@Test
	public void testEventDao() {
		// Did Spring container instantiated and prepared eventDao
		assertNotNull(eventDao);
	}

	@Test
	public void testEventDaoQuery() {
		// Is personsDao ready for operations
		List<Event> eventList = eventDao.list();

		System.out.println("Looking for list of all events in the db...");

		// personsList.size()
		assertNotNull(eventList);
		assertFalse(eventList.size() == 0);

		for (Event event : eventList) {
			System.out.println(event);
		}
	}

	@Test
	public void testEventDaoInsert() {

		List<UserAccount> userAccountList = userAccountDao.list();

		UserAccount admin = userAccountList.get(0);

		System.out.println(admin);

		Event event = new Event(null, admin, "Write Test Event",
				"Testing description", EventType.PUBLIC, DateTime.now(),
				DateTime.now().plus(Duration.standardDays(1)));

		System.out.println(event);

		eventDao.add(event);

	}

	@Test
	public void testVoteCategorytDao() {
		// Did Spring container instantiated and prepared eventDao
		assertNotNull(voteCategoryDao);
	}

	/*
	 * This test now fails because there is no VoteCategory pre-seeded in the
	 * setUp() method. Attempts to do so result in the following SQL error:
	 * java.sql.SQLIntegrityConstraintViolationException: integrity constraint
	 * violation: foreign key no parent; [...] table: VOTECATEGORY
	 */
	@Test
	public void testVoteCategoryDaoQuery() {
		// Is personsDao ready for operations
		List<VoteCategory> voteCategoryList = voteCategoryDao.list();

		System.out
				.println("Looking for list of all vote categories in the db...");

		// personsList.size()
		assertNotNull(voteCategoryList);
		assertFalse(voteCategoryList.size() == 0);

		for (VoteCategory voteCategory : voteCategoryList) {
			System.out.println(voteCategory);
		}
	}

	@Test
	public void testResults() {
		Event event = eventDao.find(eventId);

		List<NavigationMenuSection> nms = new ArrayList<NavigationMenuSection>();
		for (VoteCategory v : event.getVoteCategories()) {

			NavigationMenuSection voteCatSection = new NavigationMenuSection(
					v.getDescription());
			for (VoteOption vo : v.getVoteOptions()) {
				voteCatSection.addEntry(new NavigationMenuEntry(v
						.getCategoryName(), Integer.toString(vo.getVotes()
						.size()), vo.getOptionName()));
			}
			nms.add(voteCatSection);
		}
		// System.out.println(nms.size());
		System.out.println(gson.toJson(nms));

	}

	@Test
	public void testVoteDao() {
		Event e = eventDao.find(eventId);

		UserAccount user = userAccountDao.find(userId);
		System.out.println(userId);

		VoteOption vo = voteOptionDao.find(e.getVoteCategories().get(0)
				.getVoteOptions().get(0).getId());
		System.out.println(vo.getVoteCategory().getEvent().getId());

		Vote v = new Vote(vo, user);
		v.setUserAccount(user);
		v.setVoteOption(vo);
		voteDao.add(v);

		System.out.println("size" + voteDao.list().size());

		for (Vote v2 : voteDao.list()) {
			voteDao.remove(v2);
		}

		System.out.println("size" + voteDao.list().size());
		/*
		 * for(Vote v2 : vo.getVotes()){ System.out.println("test");
		 * if(v2.getVoteOption
		 * ().getVoteCategory()==v.getVoteOption().getVoteCategory() &&
		 * v2.getUserAccount()==user) voteDao.remove(v2); }
		 */
		// e.getVoteCategories().get(0).getVoteOptions().get(0).addVote(v);
		// vo.addVote(v);

		// eventDao.update(e);
		// System.out.println("size" +
		// e.getVoteCategories().get(0).getVoteOptions().get(0).getVotes().size());

		// System.out.println("size" + voteDao.list().size());
		/*
		 *
		 * assertTrue(vo.getVotes().size()>=1);
		 */
	}

}

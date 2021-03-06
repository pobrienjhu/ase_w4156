package edu.columbia.ase.teamproject;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import edu.columbia.ase.teamproject.persistence.dao.EventDao;
import edu.columbia.ase.teamproject.persistence.dao.UserAccountDao;
import edu.columbia.ase.teamproject.persistence.dao.VoteCategoryDao;
import edu.columbia.ase.teamproject.persistence.domain.Event;
import edu.columbia.ase.teamproject.persistence.domain.UserAccount;
import edu.columbia.ase.teamproject.persistence.domain.enumeration.AccountType;
import edu.columbia.ase.teamproject.persistence.domain.enumeration.EventType;
import edu.columbia.ase.teamproject.security.Permission;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-db.xml")
@TransactionConfiguration(defaultRollback = true)
@Transactional
public class PersistenceQueryTest extends AbstractTransactionalJUnit4SpringContextTests {

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

    @Before
    public void setUp() {
        /*
         * Add users
         */

        UserAccount user1 = new UserAccount(AccountType.LOCAL, "user1", "User1 Account",
                "password", "user1@example.com",
                Arrays.asList(new Permission[] { Permission.USER }));

        UserAccount user2 = new UserAccount(AccountType.LOCAL, "user2", "User2 Account",
                "password", "user2@example.com",
                Arrays.asList(new Permission[] { Permission.USER }));

        UserAccount admin = new UserAccount(AccountType.LOCAL, "admin", "Admin Account",
                "password", "admin@example.com",
                Arrays.asList(new Permission[] { Permission.USER }));

        user1 = userAccountDao.add(user1);
        user2 = userAccountDao.add(user2);
        admin = userAccountDao.add(admin);

        /*
         * Add Events
         */
        Event event1 = new Event(null, admin, "Event Name 1", "Event Description",
                EventType.PUBLIC, DateTime.now(), DateTime.now().plus(Duration.standardDays(1)));

        Event event2 = new Event(null, admin, "Event Name 2", "Event Description",
                EventType.PUBLIC, DateTime.now(), DateTime.now().plus(Duration.standardDays(1)));

        Event event3 = new Event(null, admin, "Event Name 3", "Event Description",
                EventType.PUBLIC, DateTime.now(), DateTime.now().plus(Duration.standardDays(1)));

        Event event4 = new Event(null, admin, "Event Name 4", "Event Description",
                EventType.PRIVATE, DateTime.now(), DateTime.now().plus(Duration.standardDays(1)));

        event4.addEventUser(user1);
        event4.addEventUser(user2);

        Event event5 = new Event(null, admin, "Event Name 5", "Event Description",
                EventType.PUBLIC, DateTime.now().plus(Duration.standardHours(1)), DateTime.now()
                        .plus(Duration.standardDays(1)));

        Event event6 = new Event(null, admin, "Event Name 6", "Event Description",
                EventType.PRIVATE, DateTime.now().minus(Duration.standardDays(2)), DateTime.now()
                        .minus(Duration.standardDays(1)));

        Event event7 = new Event(null, admin, "Event Name 7", "Event Description",
                EventType.PUBLIC, DateTime.now().minus(Duration.standardDays(2)), DateTime.now()
                        .minus(Duration.standardDays(1)));

        event6.addEventUser(user1);
        event6.addEventUser(user2);

        eventDao.add(event1);
        eventDao.add(event2);
        eventDao.add(event3);
        eventDao.add(event4);
        eventDao.add(event5);
        eventDao.add(event6);
        eventDao.add(event7);

    }

    @AfterClass
    public static void tearDown() {
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
    public void testEventDao() {
        // Did Spring container instantiated and prepared eventDao
        assertNotNull(eventDao);
    }

    @Test
    public void testEventDaoQuery() {
        // Is personsDao ready for operations
        List<Event> eventList = eventDao.list();

        System.out.println("Looking for list of all events in the db...");

        assertNotNull(eventList);
        assertFalse(eventList.size() == 0);

        for (Event event : eventList) {
            System.out.println(event);
        }
    }

    @Test
    public void testEventDaoQueryActivePublicEvents() {

        Collection<Event> eventList = eventDao.getAllActivePublicEvents(new DateTime());

        System.out.println("Looking for list of all events in the db...");

        // personsList.size()
        assertNotNull(eventList);
        assertFalse(eventList.size() == 0);

        for (Event event : eventList) {
            System.out.println(event);
        }
    }

    @Test
    public void testEventDaoQueryCompletedPublicEvents() {

        Collection<Event> eventList = eventDao.getAllCompletedPublicEvents(new DateTime());

        System.out.println("Looking for list of all events in the db...");

        // personsList.size()
        assertNotNull(eventList);
        assertFalse(eventList.size() == 0);

        for (Event event : eventList) {
            System.out.println(event);
        }
    }

    @Test
    public void testEventDaoQueryActivePrivateEventsForUser() {

        List<UserAccount> userList = userAccountDao.list();

        for (UserAccount userAccount : userList) {

            Collection<Event> eventList = eventDao.getAllActivePrivateEventsForUserId(
                    new DateTime(), userAccount.getId());

            System.out.println("Looking for list of all events in the db...");

            if (StringUtils.equals(userAccount.getUsername(), "admin")) {
                assertNotNull(eventList);
                assertTrue(eventList.size() == 0);
            } else {
                assertNotNull(eventList);
                assertFalse(eventList.size() == 0);

                for (Event event : eventList) {
                    System.out.println(event);
                }
            }
        }
    }

    @Test
    public void testEventDaoQueryActiveAdminEventsForUser() {

        List<UserAccount> userList = userAccountDao.list();

        for (UserAccount userAccount : userList) {

            Collection<Event> eventList = eventDao.getAllActiveAdminEventsForUserId(new DateTime(),
                    userAccount.getId());

            System.out.println("Looking for list of all events in the db...");

            if (StringUtils.equals(userAccount.getUsername(), "admin")) {
                assertNotNull(eventList);
                assertFalse(eventList.size() == 0);

                for (Event event : eventList) {
                    System.out.println(event);
                }
            } else {
                assertNotNull(eventList);
                assertTrue(eventList.size() == 0);

            }
        }
    }

    @Test
    public void testEventDaoQueryCompletedAdminEventsForUser() {

        List<UserAccount> userList = userAccountDao.list();

        for (UserAccount userAccount : userList) {

            Collection<Event> eventList = eventDao.getAllCompletedAdminEventsForUserId(
                    new DateTime(), userAccount.getId());

            System.out.println("Looking for list of all events in the db...");

            if (StringUtils.equals(userAccount.getUsername(), "admin")) {
                assertNotNull(eventList);
                assertFalse(eventList.size() == 0);

                for (Event event : eventList) {
                    System.out.println(event);
                }
            } else {
                assertNotNull(eventList);
                assertTrue(eventList.size() == 0);
            }
        }
    }

    @Test
    public void testEventDaoQueryFutureAdminEventsForUser() {

        List<UserAccount> userList = userAccountDao.list();

        for (UserAccount userAccount : userList) {

            Collection<Event> eventList = eventDao.getAllFutureAdminEventsForUserId(new DateTime(),
                    userAccount.getId());

            System.out.println("Looking for list of all events in the db...");

            if (StringUtils.equals(userAccount.getUsername(), "admin")) {
                assertNotNull(eventList);
                assertFalse(eventList.size() == 0);

                for (Event event : eventList) {
                    System.out.println(event);
                }
            } else {
                assertNotNull(eventList);
                assertTrue(eventList.size() == 0);
            }
        }
    }

    @Test
    public void testEventDaoQueryCompletedPrivateEventsForUser() {

        List<UserAccount> userList = userAccountDao.list();

        for (UserAccount userAccount : userList) {

            Collection<Event> eventList = eventDao.getAllCompletedPrivateEventsForUserId(
                    new DateTime(), userAccount.getId());

            System.out.println("Looking for list of all events in the db...");

            if (StringUtils.equals(userAccount.getUsername(), "admin")) {
                assertNotNull(eventList);
                assertTrue(eventList.size() == 0);
            } else {
                assertNotNull(eventList);
                assertFalse(eventList.size() == 0);

                for (Event event : eventList) {
                    System.out.println(event);
                }
            }
        }
    }

}

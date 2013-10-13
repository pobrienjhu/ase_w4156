package edu.columbia.ase.teamproject;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.SessionFactory;
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
import edu.columbia.ase.teamproject.persistence.domain.VoteCategory;
import edu.columbia.ase.teamproject.persistence.domain.enumeration.AccountType;
import edu.columbia.ase.teamproject.security.Permission;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-db.xml")
@TransactionConfiguration(defaultRollback = true)
@Transactional 
public class PersistenceTest extends AbstractTransactionalJUnit4SpringContextTests{

    static {
        System.setProperty("dataFile", "test-data.sql");
    }
	
	 @Autowired
	 private SessionFactory sessionFactory;
	 
	 @Autowired
	 private UserAccountDao userAccountDao;
	 
	 @Autowired
	 private EventDao eventDao;
	 
	 @Autowired
	 VoteCategoryDao voteCategoryDao;

	 @Before
	 public void setUp() {
		 UserAccount user = new UserAccount(AccountType.LOCAL, "user",
				 "displayName", "password",
				 Arrays.asList(new Permission[]{Permission.USER}));
		 userAccountDao.add(user);
		 Event event = new Event(user, "Event Name", "Event Description");
		 eventDao.add(event);
	 }

	 @AfterClass
	 public void  tearDown(){
	        System.clearProperty("dataFile");
	 }
	 
	 @Test
	 public void testHibernateConfiguration() {
	  // Did Spring container instantiated and prepared sessionFactory
	  assertNotNull (sessionFactory); 
	 }
	 
	 @Test
	 public void testUserAccountDao(){
		  // Did Spring container instantiated and prepared personsDao
		  assertNotNull (userAccountDao); 
	 }
	 
	 
	 @Test
	 public void testUserAccountDaoQuery(){
		// Is personsDao ready for operations	 
		 List<UserAccount> userAccountList = userAccountDao.list();
		 
		 System.out.println("Looking for list of all persons in the db...");
		 
		 //personsList.size()
		 assertNotNull(userAccountList);
		 assertFalse( userAccountList.size() == 0 ) ;
		 
		 for(UserAccount userAccount: userAccountList ){
			 System.out.println(userAccount);
		 }
	 }
	 
	 @Test
	 public void testUserAccountsDaoInsert(){
		 // Is personsDao ready for operations	 
		List<Permission> permissions = new ArrayList<Permission>();
		permissions.add(Permission.ADMIN);
		UserAccount patrick = new UserAccount(AccountType.LOCAL, "patrick.obrien.8474@gmail.com", "Patrick OBrien", "password", permissions);
		
		userAccountDao.add(patrick); 
		
	 }
	 
	 
	 @Test
	 public void testEventDao(){
		  // Did Spring container instantiated and prepared eventDao
		  assertNotNull (eventDao); 
	 }
	
	 @Test
	 public void testEventDaoQuery(){
		// Is personsDao ready for operations	 
		 List<Event> eventList = eventDao.list();
		 
		 System.out.println("Looking for list of all events in the db...");
		 
		 //personsList.size()
		 assertNotNull(eventList);
		 assertFalse( eventList.size() == 0 ) ;
		 
		 for(Event event: eventList ){
			 System.out.println(event);
		 }
	 }
	 
	 @Test
	 public void testEventDaoInsert(){
		 
		 List<UserAccount> userAccountList = userAccountDao.list();
		 
		 UserAccount admin = userAccountList.get(0);
		
		 System.out.println(admin);
		 
		 Event event = new Event(admin, "Write Test Event", "Testing description");
		 
		 System.out.println(event);
		 
		 eventDao.add(event);
		 
	 }
	 
	 
	 @Test
	 public void testVoteCategorytDao(){
		  // Did Spring container instantiated and prepared eventDao
		  assertNotNull (voteCategoryDao); 
	 }
	
	 /*
	  * This test now fails because there is no VoteCategory pre-seeded in the
	  * setUp() method.  Attempts to do so result in the following SQL error:
	  * java.sql.SQLIntegrityConstraintViolationException: integrity constraint violation: foreign key no parent; [...] table: VOTECATEGORY
	  */
	 @Test
	 public void testVoteCategoryDaoQuery(){
		// Is personsDao ready for operations	 
		 List<VoteCategory> voteCategoryList = voteCategoryDao.list();
		 
		 System.out.println("Looking for list of all vote categories in the db...");
		 
		 //personsList.size()
		 assertNotNull(voteCategoryList);
		 assertFalse( voteCategoryList.size() == 0 ) ;
		 
		 for(VoteCategory voteCategory: voteCategoryList ){
			 System.out.println(voteCategory);
		 }
	 }
	 
	 
	 
}

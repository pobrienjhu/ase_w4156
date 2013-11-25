package edu.columbia.ase.teamproject;

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
import edu.columbia.ase.teamproject.services.VoteService;
import edu.columbia.ase.teamproject.util.GsonProvider;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class VoteServiceWhiteBoxTest extends AbstractTransactionalJUnit4SpringContextTests{
	
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
	 
	 @Autowired
	 VoteService voteService;
	 
	 Gson gson;
	 
	 private long eventId;
	 private long userId;
	 
	 @Before
	 public void setUp() {
			gson = new GsonProvider().provideGson();
		 /*
		  * Add users
		  */
		 
		 UserAccount user1 = new UserAccount(AccountType.LOCAL, "user1",
				 "User1 Account", "password", "user1@example.com",
				 Arrays.asList(new Permission[]{Permission.USER}));
		 
		 UserAccount user2 = new UserAccount(AccountType.LOCAL, "user2",
				 "User2 Account", "password", "user2@example.com",
				 Arrays.asList(new Permission[]{Permission.USER}));
		 
		 UserAccount admin = new UserAccount(AccountType.LOCAL, "admin",
				 "Admin Account", "password", "user3@example.com",
				 Arrays.asList(new Permission[]{Permission.USER}));
		 
		 user1 = userAccountDao.add(user1);
		
		 user2 = userAccountDao.add(user2);		 
		 admin = userAccountDao.add(admin);
		 userId = user1.getId();
		 /*
		  * Add Events
		  */
		 Event event = new Event(null,admin, "Event Name", "Event Description",
				 EventType.PUBLIC, DateTime.now(),
				 DateTime.now().plus(Duration.standardDays(1)));
		 
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
		 //voId = category1.getVoteOptions().get(0).getId();
		 category1.addVotingOption(new VoteOption("option 1b"));
		 category1.addVotingOption(new VoteOption("option 1c"));

		 category2.addVotingOption(new VoteOption("option 2a"));
		 category2.addVotingOption(new VoteOption("option 2b"));
		 category2.addVotingOption(new VoteOption("option 2c"));
		 
		 event.addVoteCategory(category1);
		 event.addVoteCategory(category2);
		 
		 event = eventDao.update(event);
		
		 for(VoteCategory category: event.getVoteCategories() ){
			 category.addVotingOption(new VoteOption("option 4"));
		 }

		 event = eventDao.update(event);
		 
		 System.out.println(event);

		 eventDao.flush();

		 
	 }

	 @AfterClass
	 public static void tearDown(){
	        //System.clearProperty("dataFile");
	 }
	 
	 
	 /*
	  * 
	  * The setup creates a single event Event Name
	  * with two categories category 1 and category 2
	  * 
	  * category 1 has options 
	  *  option 1a
	  *  option 2a
	  *  option 3a
	  *  option 4 (added)
	  *  
	  * category 2 has options 
	  *  option 2a
	  *  option 2b
	  *  option 2c 
	  *  option 4 (added)
	  * 
	  */

	 @Test
	 public void testVerifyVotes()
	 {

		 Event event = eventDao.find(eventId);
		 
		 List<Long>voteList = new ArrayList<Long>();
		 
		 voteList.add(new Long(1));
		 voteList.add(new Long(2));
		 
		 boolean exceptionThrown = false;
		 
		 try {
			voteService.verifyVotes(event, voteList);
		 } catch (Exception e) {
			 exceptionThrown = true;
		 }
		 		
		 assert(exceptionThrown == false);
	 }	 
	 
	 @Test
	 public void testFailVerifyVotes()
	 {

		 Event event = eventDao.find(eventId);
		 
		 List<Long>voteList = new ArrayList<Long>();
		 
		 voteList.add(new Long(1));
		 
		 boolean exceptionThrown = false;
		 
		 try {
			voteService.verifyVotes(event, voteList);
		 } catch (Exception e) {
			 exceptionThrown = true;
		 }
		 		
		 assert(exceptionThrown);
	 }
	 	 
	 
	 @Test
	 public void testAddVotes()
	 {
		 UserAccount user = userAccountDao.find(userId);

		 List<Long>voteList = new ArrayList<Long>();
		 
		 voteList.add(new Long(1));
		 voteList.add(new Long(2));
			
		 boolean exceptionThrown = false;
		 
		 try {
			voteService.addVotes(voteList, user);
		} catch (Exception e) {
			 exceptionThrown = true;
		}
		 
		 assert(exceptionThrown==false);
	 
	 }

	 @Test
	 public void testAddSecondVotes()
	 {
		 UserAccount user = userAccountDao.find(userId);

		 List<Long>voteList = new ArrayList<Long>();
		 
		 voteList.add(new Long(1));
		 voteList.add(new Long(2));
		
		 
		 boolean exceptionThrown = false;
		 
		 try {
			voteService.addVotes(voteList, user);
		} catch (Exception e) {
			 exceptionThrown = true;
		}
		 
		 assert(exceptionThrown==false);
	 	
		 user = userAccountDao.find(userId);
		 
		 List<Long>secondVoteList = new ArrayList<Long>();
		 
		 secondVoteList.add(new Long(1));
		 secondVoteList.add(new Long(2));

		 exceptionThrown = false;
		 
		 try {
			voteService.addVotes(secondVoteList, user);
		} catch (Exception e) {
			 exceptionThrown = true;
		}
		 
		 assert(exceptionThrown==false);		 
		 
	 }	 
	 
	 @Test
	 public void testFailAddVotes()
	 {

		 UserAccount user = userAccountDao.find(userId);
		 
		 List<Long>voteList = new ArrayList<Long>();
		 
		 voteList.add(new Long(1));
		 
		 boolean exceptionThrown = false;
		 
		 try {
			voteService.addVotes(voteList, user);
		 } catch (Exception e) {
			 exceptionThrown = true;
		 }
		 		
		 assert(exceptionThrown);
	 }

	 @Test
	 public void testFailAddVotes2()
	 {

		 UserAccount user = userAccountDao.find(userId);
		 
		 List<Long>voteList = new ArrayList<Long>();
		 
		 voteList.add(new Long(25));
		 
		 boolean exceptionThrown = false;
		 
		 try {
			voteService.addVotes(voteList, user);
		 } catch (Exception e) {
			 exceptionThrown = true;
		 }
		 		
		 assert(exceptionThrown);
	 }	 
	 
	 @Test
	 public void testResults()
	 {
		 Event event = eventDao.find(eventId);
			
		 System.out.println(voteService.getResults(event));	 		
	 }

	 
	 
	 
	 
}

package edu.columbia.ase.teamproject.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.SessionFactory;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import edu.columbia.ase.teamproject.persistence.dao.EventDao;
import edu.columbia.ase.teamproject.persistence.dao.UserAccountDao;
import edu.columbia.ase.teamproject.persistence.dao.VoteCategoryDao;
import edu.columbia.ase.teamproject.persistence.dao.VoteDao;
import edu.columbia.ase.teamproject.persistence.dao.VoteOptionDao;
import edu.columbia.ase.teamproject.persistence.domain.Event;
import edu.columbia.ase.teamproject.persistence.domain.UserAccount;
import edu.columbia.ase.teamproject.persistence.domain.VoteCategory;
import edu.columbia.ase.teamproject.persistence.domain.VoteOption;
import edu.columbia.ase.teamproject.persistence.domain.enumeration.AccountType;
import edu.columbia.ase.teamproject.persistence.domain.enumeration.EventType;
import edu.columbia.ase.teamproject.security.Permission;
import edu.columbia.ase.teamproject.services.exceptions.ValidationException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class VoteServiceBlackBoxTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    private EventDao eventDao;

    @Autowired
    private EventService eventService;

    @Autowired
    private UserAccountDao userAccountDao;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private VoteService voteService;

    @Autowired
    private VoteDao voteDao;

    @Autowired
    private VoteCategoryDao voteCategoryDao;

    @Autowired
    private VoteOptionDao voteOptionDao;

    private UserAccount voter;
    private UserAccount admin;
    private Event firstEvent;
    private Event secondEvent;
    private Event privateEvent;

    private List<VoteCategory> createBogusCategories() {
        VoteCategory vc1 = new VoteCategory("category 1", "category 1 description");
        VoteCategory vc2 = new VoteCategory("category 2", "category 2 description");
        vc1.addVotingOption(new VoteOption(vc1, "1-1"));
        vc1.addVotingOption(new VoteOption(vc1, "1-2"));
        vc2.addVotingOption(new VoteOption(vc2, "2-1"));
        vc2.addVotingOption(new VoteOption(vc2, "2-2"));
        return ImmutableList.<VoteCategory> of(vc1, vc2);
    }

    @Before
    public void setUp() throws ValidationException {
        UserAccount user = new UserAccount(AccountType.LOCAL, "user", "displayName", "password",
                "user@example.com", Arrays.asList(new Permission[] { Permission.USER }));
        voter = userAccountDao.add(user);
        
        UserAccount admin1 = new UserAccount(AccountType.LOCAL, "user2", "displayName", "password",
                "admin@example.com", Arrays.asList(new Permission[] { Permission.ADMIN }));
        admin = userAccountDao.add(admin1);

        firstEvent = eventService.createEvent(voter, "FirstEvent Name", "Event Description",
                EventType.PUBLIC, DateTime.now(), DateTime.now().plus(Duration.standardDays(1)),
                createBogusCategories(), new ArrayList<String>());

        secondEvent = eventService.createEvent(voter, "SecondEvent Name", "Event Description",
                EventType.PUBLIC, DateTime.now(), DateTime.now().plus(Duration.standardDays(1)),
                createBogusCategories(), new ArrayList<String>());
        
        privateEvent = eventService.createEvent(admin, "PrivateEvent Name", "Event Description",
                EventType.PRIVATE, DateTime.now(), DateTime.now().plus(Duration.standardDays(1)),
                createBogusCategories(), new ArrayList<String>());
        
    }
    
    @Test
    public void testVoteEventDoesntExist(){
    	Event e = new Event(null,null,"","",EventType.PUBLIC, DateTime.now(), DateTime.now());
    	e.setId((long)10000);
    	
    	 List<Long> voteList = Lists.newArrayList();
    	  voteList.add((long) 1);
    	  try {
              voteService.verifyVotes(e, voteList);
              fail("should not succeed");
          } catch (Exception e1) {
              assertTrue(e1.getMessage() != null);
          }
    	
    }
    
    @Test
    public void testVerifyAddVoteRecordsVote() throws Exception {
    	//test firstEvent, secondEvent
        List<Long> voteList = Lists.newArrayList();
        voteList.add(firstEvent.getVoteCategories().get(0).getVoteOptions().get(0).getId());
        voteList.add(firstEvent.getVoteCategories().get(1).getVoteOptions().get(1).getId());
        voteService.addVotes(firstEvent, voteList, voter);

        // eventDao.update(secondEvent);

        VoteOption vo1o1 = voteOptionDao.find(firstEvent.getVoteCategories().get(0)
                .getVoteOptions().get(0).getId());
        VoteOption vo2o1 = voteOptionDao.find(firstEvent.getVoteCategories().get(1)
                .getVoteOptions().get(1).getId());

        assertTrue(vo1o1.getVotes().size() == 1);
        assertTrue(vo2o1.getVotes().size() == 1);
    }
    
    @Test
    public void testVerifyVotesRejectsMissingCategory() {
        assertFalse(1 == firstEvent.getVoteCategories().size());
        List<Long> voteList = Lists.newArrayList();
        voteList.add(firstEvent.getVoteCategories().get(0).getVoteOptions().get(0).getId());
        try {
            voteService.verifyVotes(firstEvent, voteList);
            fail("should not succeed");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Invalid voting"));
        }
    }

    @Test
    public void testRepeatCategoriesRejectsAll(){
    	  List<Long> voteList = Lists.newArrayList();
    	  //repeat all categories or one category
          voteList.add(firstEvent.getVoteCategories().get(0).getVoteOptions().get(0).getId());
          voteList.add(firstEvent.getVoteCategories().get(1).getVoteOptions().get(1).getId());
          voteList.add(firstEvent.getVoteCategories().get(0).getVoteOptions().get(1).getId());
          voteList.add(firstEvent.getVoteCategories().get(1).getVoteOptions().get(0).getId());
   	
			  try {
					voteService.addVotes(firstEvent, voteList, voter);
		            fail("should not succeed");
		        } catch (Exception e) {
		            assertTrue(e.getMessage().contains("Invalid voting"));
		        }
		  
    }
    
    @Test
    public void testVerifyAddVoteReplacesExistingVote() throws Exception {
        List<Long> voteList = Lists.newArrayList();
        voteList.add(firstEvent.getVoteCategories().get(0).getVoteOptions().get(0).getId());
        voteList.add(firstEvent.getVoteCategories().get(1).getVoteOptions().get(0).getId());
        voteService.addVotes(firstEvent, voteList, voter);
        // eventDao.update(firstEvent);

        VoteOption vo1o1 = voteOptionDao.find(firstEvent.getVoteCategories().get(0)
                .getVoteOptions().get(0).getId());
        VoteOption vo2o1 = voteOptionDao.find(firstEvent.getVoteCategories().get(1)
                .getVoteOptions().get(0).getId());

        assertTrue(vo1o1.getVotes().size() == 1);
        assertTrue(vo2o1.getVotes().size() == 1);

        voteList.clear();

        voteList.add(firstEvent.getVoteCategories().get(0).getVoteOptions().get(1).getId());
        voteList.add(firstEvent.getVoteCategories().get(1).getVoteOptions().get(1).getId());

        voteService.addVotes(firstEvent, voteList, voter);

        vo1o1 = voteOptionDao.find(firstEvent.getVoteCategories().get(0).getVoteOptions().get(0)
                .getId());
        vo2o1 = voteOptionDao.find(firstEvent.getVoteCategories().get(1).getVoteOptions().get(0)
                .getId());
        VoteOption vo1o2 = voteOptionDao.find(firstEvent.getVoteCategories().get(0)
                .getVoteOptions().get(1).getId());
        VoteOption vo2o2 = voteOptionDao.find(firstEvent.getVoteCategories().get(1)
                .getVoteOptions().get(1).getId());

        assertTrue(vo1o1.getVotes().size() == 0);
        assertTrue(vo2o1.getVotes().size() == 0);
        assertTrue(vo1o2.getVotes().size() == 1);
        assertTrue(vo2o2.getVotes().size() == 1);
    }
    
    @Test
    public void testInvalidUserPrivateEvent() throws Exception
    {
    	
    	try{
        List<Long> voteList = Lists.newArrayList();
        voteList.add(privateEvent.getVoteCategories().get(0).getVoteOptions().get(0).getId());
        voteList.add(privateEvent.getVoteCategories().get(1).getVoteOptions().get(1).getId());
        
        voteService.addVotes(privateEvent, voteList, voter);     

        VoteOption vo1o1 = voteOptionDao.find(privateEvent.getVoteCategories().get(0)
                .getVoteOptions().get(0).getId());
        VoteOption vo2o1 = voteOptionDao.find(privateEvent.getVoteCategories().get(1)
                .getVoteOptions().get(1).getId());      

        assertTrue(vo1o1.getVotes().size() == 1);
        assertTrue(vo2o1.getVotes().size() == 1);
    	
	    } catch (Exception e) {
	        assertTrue(e.getMessage().contains("Invalid event for user"));
	    }
	}
        
    @Test
    public void testValidUserPrivateEvent() throws Exception
    {
    	privateEvent.addEventUser(voter);
    	
        List<Long> voteList = Lists.newArrayList();
        voteList.add(privateEvent.getVoteCategories().get(0).getVoteOptions().get(0).getId());
        voteList.add(privateEvent.getVoteCategories().get(1).getVoteOptions().get(1).getId());
        
        voteService.addVotes(privateEvent, voteList, voter);     

        VoteOption vo1o1 = voteOptionDao.find(privateEvent.getVoteCategories().get(0)
                .getVoteOptions().get(0).getId());
        VoteOption vo2o1 = voteOptionDao.find(privateEvent.getVoteCategories().get(1)
                .getVoteOptions().get(1).getId());      

        assertTrue(vo1o1.getVotes().size() == 1);
        assertTrue(vo2o1.getVotes().size() == 1);
    	
	   
	}	
    
    
    
    @Test
    public void testVerifyVotesRejectsVotesForOtherEvent() {
        assertEquals(2, firstEvent.getVoteCategories().size());

        List<Long> voteList = Lists.newArrayList();
        voteList.add(firstEvent.getVoteCategories().get(0).getVoteOptions().get(0).getId());
        voteList.add(secondEvent.getVoteCategories().get(0).getVoteOptions().get(0).getId());
        try {
            voteService.verifyVotes(firstEvent, voteList);
            fail("should not succeed");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Must vote in all categories"));
        }
    }

  

    @Test
    public void testVerifyVotes() {
        assertEquals(2, firstEvent.getVoteCategories().size());

        List<Long> voteList = Lists.newArrayList();
        voteList.add(firstEvent.getVoteCategories().get(0).getVoteOptions().get(0).getId());
        voteList.add(firstEvent.getVoteCategories().get(1).getVoteOptions().get(0).getId());

        try {
            voteService.verifyVotes(firstEvent, voteList);
            return;
        } catch (Exception e) {
            // ignore
        }
        fail("should not succeed");
    }

   

  

    @Test
    public void testGetResults() {
        // TODO: determine an appropriate test for this.
        fail();
    }
}

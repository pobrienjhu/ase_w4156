package edu.columbia.ase.teamproject.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import edu.columbia.ase.teamproject.persistence.dao.UserAccountDao;
import edu.columbia.ase.teamproject.persistence.dao.VoteDao;
import edu.columbia.ase.teamproject.persistence.dao.VoteOptionDao;
import edu.columbia.ase.teamproject.persistence.domain.Event;
import edu.columbia.ase.teamproject.persistence.domain.UserAccount;
import edu.columbia.ase.teamproject.persistence.domain.Vote;
import edu.columbia.ase.teamproject.persistence.domain.VoteCategory;
import edu.columbia.ase.teamproject.persistence.domain.VoteOption;
import edu.columbia.ase.teamproject.services.EventService;
import edu.columbia.ase.teamproject.services.VoteService;
import edu.columbia.ase.teamproject.util.GsonProvider;
import edu.columbia.ase.teamproject.view.NavigationMenuEntry;
import edu.columbia.ase.teamproject.view.NavigationMenuSection;

@Controller
@RequestMapping("/app/voteEvent.do")
public final class VoteController {

	@Autowired
	EventService eventService;
	
	@Autowired
	VoteService voteService;

	@Autowired
	GsonProvider gsonProvider;
	
	@Autowired
	private UserAccountDao userAccountDao;
	
	@Autowired
	private VoteDao voteDao;
	
	@Autowired
	private VoteOptionDao voteOptionDao;


	private static final Logger logger = LoggerFactory
			.getLogger(VoteController.class);

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView doGet(HttpSession session, HttpServletRequest request) {
		logger.info("GET /app/voteEvent.do");

		Map<String, Object> model = ControllerHelper.createBaseModel(session);

		
	
		

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().
				getAuthentication().getPrincipal();
		UserAccount user = userAccountDao.findAccountByUserDetails(userDetails);
	
		Event event = eventService.lookupEvent(user,
				Long.valueOf(request.getParameter("event")));
		
		model.put("_eventName", event.getName());
		model.put("_eventTitle", event.getDescription());
		model.put("_eventId",event.getId().toString());
		
		
		
		if(event.getEndTime().isBeforeNow()) //voting window ended
		{
			model.put("title", "Voting Results");
			
			model.put("_results",voteService.getResults(event));
			
			return new ModelAndView("soy:edu.columbia.ase.vote.voteResults", model);
			
		}
		else
		{
			model.put("title", "Event Voting");
			
			@SuppressWarnings("unchecked")
			List<NavigationMenuSection> nms =
					(List<NavigationMenuSection>) model.get("_vote");
			
			for(VoteCategory v : event.getVoteCategories() ){
	
				NavigationMenuSection voteCatSection =
						new NavigationMenuSection(v.getCategoryName() + " - " + v.getDescription());
				for(VoteOption vo : v.getVoteOptions()){
					voteCatSection.addEntry(
							new NavigationMenuEntry(v.getCategoryName(), vo.getId().toString(),vo.getOptionName()));
				}
				nms.add(voteCatSection);
			}
	
			return new ModelAndView("soy:edu.columbia.ase.vote.voteEvent", model);
		}
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public String doPost(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws Exception 
	{
		logger.info("POST /app/voteEvent.do");
		
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().
				getAuthentication().getPrincipal();
		UserAccount user = userAccountDao.findAccountByUserDetails(userDetails);
	
		 StringTokenizer st = new StringTokenizer( IOUtils.toString(request.getInputStream()));
		 List<Long>voteList = new ArrayList<Long>();
		 
		 Event event = eventService.lookupEvent(user,Long.parseLong(st.nextToken()));
		 if(event == null || event.getEndTime().isBeforeNow()){
			 throw new Exception("Invalid Event!");			 
		 }
		 
		 while(st.hasMoreTokens()){			
			voteList.add(Long.parseLong(st.nextToken()));	
		 }
		 
		 try{
				
			voteService.verifyVotes(event, voteList);	
		 }		 
		 catch(Exception e){
	
			throw new Exception(e.getMessage()); //not sending message by itself
			 //response.sendError(400, e.getMessage()); //not working properly
			
			
		 }
		 
		 
		 try {
			voteService.addVotes(voteList, user);
		} catch (Exception e) {
			logger.error("Unable to add vote. Root Cause ("+e+")");
			return  "Unable to add votes!";			
		}
	
	 
		//for(int i = 0; i < voteList.size(); i++){	
		//	 voteService.addVote( voteList.get(i),user);				
		// }
		

		return  "Your vote has been submitted!";

	}



}

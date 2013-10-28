package edu.columbia.ase.teamproject.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;

import edu.columbia.ase.teamproject.persistence.dao.VoteDao;
import edu.columbia.ase.teamproject.persistence.dao.VoteOptionDao;
import edu.columbia.ase.teamproject.persistence.domain.Event;
import edu.columbia.ase.teamproject.persistence.domain.UserAccount;
import edu.columbia.ase.teamproject.persistence.domain.Vote;
import edu.columbia.ase.teamproject.persistence.domain.VoteCategory;
import edu.columbia.ase.teamproject.persistence.domain.VoteOption;
import edu.columbia.ase.teamproject.util.GsonProvider;
import edu.columbia.ase.teamproject.view.NavigationMenuEntry;
import edu.columbia.ase.teamproject.view.NavigationMenuSection;

public class VoteService {
	
	@Autowired
	VoteOptionDao voteOptionDao;
	
	@Autowired
	VoteDao voteDao;
	
	@Autowired
	GsonProvider gsonProvider;
	
	public void addVote(long id,UserAccount user)
	{
		
		VoteOption vo = voteOptionDao.find(id);
		
		//first remove any votes in the same category by the same user
		for(Vote v2 : voteDao.list()){ 
			if(v2.getVoteOption().getVoteCategory().getId()==vo.getVoteCategory().getId() && v2.getUserAccount().getId()==user.getId()){
			
				voteDao.remove(v2);
							
			}
							
		}
	
		Vote v = new Vote(vo,user); 
		//try{ 
			voteDao.add(v);
		//}
	//	catch(Exception e)
		//{}
		
	}
	
	public String getResults(Event event)
	{
				
		List<NavigationMenuSection> nms= new ArrayList<NavigationMenuSection>();
		for(VoteCategory v : event.getVoteCategories() ){

			NavigationMenuSection voteCatSection =
					new NavigationMenuSection(v.getDescription());
			for(VoteOption vo : v.getVoteOptions()){
				voteCatSection.addEntry(
						new NavigationMenuEntry(v.getCategoryName(),Integer.toString(vo.getVotes().size()),vo.getOptionName()));
			}
			nms.add(voteCatSection);
		}
		Gson gson = gsonProvider.provideGson();
		return (gson.toJson(nms));
			
	}
	

}

package edu.columbia.ase.teamproject.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.annotations.VisibleForTesting;
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

	@VisibleForTesting
	@Transactional
	void verifyVotes(Event event,List<Long>voteList) throws Exception
	{
		if(event.getVoteCategories().size() != voteList.size() ||  event.getEndTime().isBeforeNow())
			throw new Exception("Invalid voting!");	
		//making sure user votes in each category
		boolean found;
		for(VoteCategory vc : event.getVoteCategories()){
			found = false;
			for(VoteOption vo : vc.getVoteOptions()){
				if(voteList.contains(vo.getId())){
					found = true;
				}
			}		
			if(!found){
				throw new Exception("Must vote in all categories!");				
			}
		}
	
	}
	
	@Transactional
	public void addVotes(Event event, List<Long>voteList, UserAccount user) throws Exception{
		verifyVotes(event, voteList);

		for(int i = 0; i < voteList.size(); i++){
			 addVote(voteList.get(i),user);
		 }
	}

	@VisibleForTesting
	@Transactional
	void addVote(long id,UserAccount user) throws Exception
	{
		
	
		VoteOption voteOption = voteOptionDao.find(id);
		
		if( voteOption == null ){
			throw new Exception("Invalid vote option id ("+id+")");
		}
		
	
		for(Vote v2 : user.getVotes()){
			if(v2.getVoteOption().getVoteCategory().getId() == voteOption.getVoteCategory().getId()){  // && v2.getUserAccount().getId()==user.getId()){

				v2.setVoteOption(voteOption);
				voteOption.addVote(v2);
				voteDao.update(v2);
				return;
			}
							
		}
	
		Vote v = new Vote(voteOption,user); 
		voteDao.add(v);
	
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

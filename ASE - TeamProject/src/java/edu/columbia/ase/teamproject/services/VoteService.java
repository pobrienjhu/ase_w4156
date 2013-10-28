package edu.columbia.ase.teamproject.services;

import org.springframework.beans.factory.annotation.Autowired;

import edu.columbia.ase.teamproject.persistence.dao.VoteDao;
import edu.columbia.ase.teamproject.persistence.dao.VoteOptionDao;
import edu.columbia.ase.teamproject.persistence.domain.Event;
import edu.columbia.ase.teamproject.persistence.domain.UserAccount;
import edu.columbia.ase.teamproject.persistence.domain.Vote;
import edu.columbia.ase.teamproject.persistence.domain.VoteCategory;
import edu.columbia.ase.teamproject.persistence.domain.VoteOption;

public class VoteService {
	
	@Autowired
	VoteOptionDao voteOptionDao;
	
	@Autowired
	VoteDao voteDao;
	
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
	
	public void getResults(Event e)
	{
		
		for(VoteCategory vc : e.getVoteCategories() ){	
			
			System.out.println(vc.getCategoryName());
			
			for(VoteOption vo : vc.getVoteOptions() ){
				
				System.out.println(vo.getOptionName() + " "+ vo.getVotes().size());
				
			
			}
				
		}
	}
	

}

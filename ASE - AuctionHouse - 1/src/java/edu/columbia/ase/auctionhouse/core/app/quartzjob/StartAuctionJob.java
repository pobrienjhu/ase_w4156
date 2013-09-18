package edu.columbia.ase.auctionhouse.core.app.quartzjob;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import edu.columbia.ase.auctionhouse.core.app.AuctionHouse;

public class StartAuctionJob implements Job {
	
	public StartAuctionJob() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		
	      JobDataMap dataMap = context.getJobDetail().getJobDataMap();
		
	      AuctionHouse auctionHouse = (AuctionHouse) dataMap.get("AuctionHouse");
	      Integer auctionId = (Integer) dataMap.get("AuctionId");	
	      
	      auctionHouse.startAuction(auctionId);
	}	

}

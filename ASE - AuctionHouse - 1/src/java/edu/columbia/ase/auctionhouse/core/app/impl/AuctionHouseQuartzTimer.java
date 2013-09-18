package edu.columbia.ase.auctionhouse.core.app.impl;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.columbia.ase.auctionhouse.core.app.AuctionHouse;
import edu.columbia.ase.auctionhouse.core.app.AuctionHouseDataSource;
import edu.columbia.ase.auctionhouse.core.app.AuctionHouseTimer;
import edu.columbia.ase.auctionhouse.core.app.quartzjob.EndAuctionJob;
import edu.columbia.ase.auctionhouse.core.app.quartzjob.StartAuctionJob;
import edu.columbia.ase.auctionhouse.core.data.Auction;

public class AuctionHouseQuartzTimer implements AuctionHouseTimer {

	private Logger log = LoggerFactory.getLogger(AuctionHouseQuartzTimer.class);
	
	private AuctionHouseDataSource auctionHouseDataSource;
	
	private AuctionHouse auctionHouse;
	
	private Scheduler scheduler;
 
	@Override
	public void startAuctionTimer() throws Exception {

		log.info("Staring Auction House Timer ...");
		
    	scheduler = new StdSchedulerFactory().getScheduler();
    	
		if( auctionHouseDataSource == null ){
			throw new Exception("AuctionHouseDataSource is not Set.");
		}
    	

		/*
		 * Start the scheduler thread
		 */
    	scheduler.start();

	}
	
	/*
	 * Validate the auction to make sure that the start and end dates are set 
	 * and that it doesn't end before it starts. 
	 */
	private void validateAuction(Auction auction) throws Exception{
		
		if( auction.getStartTime() == null ){
			throw new Exception( "Auction does not have a start time!" );
		}

		if( auction.getEndTime() == null ){
			throw new Exception( "Auction does not have an end time!" );
		}
		
		if( auction.getEndTime().before(auction.getStartTime())  ){
			throw new Exception( "Auction end time is before start time!" );
		}
		
		if( auction.getEndTime().before(new Date()) ){
			throw new Exception( "Auction end time is before the current time!" );
		}
		
		
	}
	
	private void scheduleJob( Class<? extends Job> jobClass, Auction auction, AuctionHouse auctionHouse, Date jobTime, Scheduler schedule) throws SchedulerException{
		
		Trigger trigger = TriggerBuilder.newTrigger().startAt(jobTime).build();
		
		JobDetail jobDetail = 
				JobBuilder.newJob(jobClass).build();

		jobDetail.getJobDataMap().put("AuctionId", new Integer( auction.getAuctionId() ));
		jobDetail.getJobDataMap().put("AuctionHouse", auctionHouse);

		schedule.scheduleJob(jobDetail, trigger);
		
	}
	
	
	@Override
	public void setAuctionHouseDataSource(AuctionHouseDataSource auctionHouseDataSource) {
		this.auctionHouseDataSource = auctionHouseDataSource;		
	}

	/*
	 * 
	 * Add the auction start and end jobs to the scheulder
	 * For a real production ready system we probably remove a start or end job if either one fails 
	 * to be created but for our purposes I think we can assume they will all be successful. 
	 * 
	 * (non-Javadoc)
	 * @see edu.columbia.ase.auctionhouse.core.app.AuctionHouseTimer#setAuctionHouse(edu.columbia.ase.auctionhouse.core.app.AuctionHouse)
	 */
	@Override
	public void setAuctionHouse(AuctionHouse auctionHouse) {
		this.auctionHouse = auctionHouse;
	}

	@Override
	public void addAuction(Auction auction) throws Exception {
		try {
			validateAuction(auction);
		} catch (Exception e) {
			throw new Exception ( "Unable to schedule auction. Root Cause ("+e.getMessage()+")");
		}
		
		try {
			scheduleJob(StartAuctionJob.class, auction, auctionHouse, auction.getStartTime(), scheduler);
			log.info("Scheduled auction "+auction.getAuctionId()+" start time for "+auction.getStartTime());
		} catch (Exception e) {
			log.warn("Unable to schedule auction start job "+e.getMessage()+")");
		}

		try {
			scheduleJob(EndAuctionJob.class, auction, auctionHouse, auction.getEndTime(), scheduler);
			log.info("Scheduled auction "+auction.getAuctionId()+" end time for "+auction.getEndTime());
		} catch (Exception e) {
			log.warn("Unable to schedule auction end job. root cause ("+e.getMessage()+")");
		}
	}



}

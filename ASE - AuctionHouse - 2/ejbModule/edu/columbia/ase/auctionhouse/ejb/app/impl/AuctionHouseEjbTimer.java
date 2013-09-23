package edu.columbia.ase.auctionhouse.ejb.app.impl;

import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Singleton;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.columbia.ase.auctionhouse.ejb.app.AuctionHouseDataSource;
import edu.columbia.ase.auctionhouse.ejb.app.AuctionHouseTimer;
import edu.columbia.ase.auctionhouse.ejb.app.timer.AuctionTimer;
import edu.columbia.ase.auctionhouse.ejb.app.timer.EndAuctionTimer;
import edu.columbia.ase.auctionhouse.ejb.app.timer.StartAuctionTimer;
import edu.columbia.ase.auctionhouse.ejb.data.Auction;

@Singleton (mappedName="edu.columbia.ase.auctionhouse.ejb.AuctionHoueTimer")
public class AuctionHouseEjbTimer implements AuctionHouseTimer {

	private Logger log = LoggerFactory.getLogger(AuctionHouseEjbTimer.class);
	
	// @Resource if this was a real data source
	@EJB 
	private AuctionHouseDataSource auctionHouseDataSource;
	
	@EJB 
	private StartAuctionTimer startAuctionTimer;
	
	@EJB 
	private EndAuctionTimer endAuctionTimer;
	
	//private @Resource SessionContext ctx;
	 
	@Override
	public void startAuctionTimer() throws Exception {

    	
		if( auctionHouseDataSource == null ){
			throw new Exception("AuctionHouseDataSource is not Set.");
		}
    	

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
	
	private void scheduleJob( AuctionTimer auctionTimer, Auction auction, Date jobTime){
		auctionTimer.scheduleAuctionEvent(jobTime, auction);
	}
	
	
	@Override
	public void setAuctionHouseDataSource(AuctionHouseDataSource auctionHouseDataSource) {
		this.auctionHouseDataSource = auctionHouseDataSource;		
	}


	@Override
	public void addAuction(Auction auction) throws Exception {
		try {
			validateAuction(auction);
		} catch (Exception e) {
			throw new Exception ( "Unable to schedule auction. Root Cause ("+e.getMessage()+")");
		}
		
		try {
			scheduleJob(startAuctionTimer, auction, auction.getStartTime());
			log.info("Scheduled auction "+auction.getAuctionId()+" start time for "+auction.getStartTime());
		} catch (Exception e) {
			log.warn("Unable to schedule auction start job "+e.getMessage()+")");
		}

		try {
			scheduleJob(endAuctionTimer, auction, auction.getEndTime());
			log.info("Scheduled auction "+auction.getAuctionId()+" end time for "+auction.getEndTime());
		} catch (Exception e) {
			log.warn("Unable to schedule auction end job. root cause ("+e.getMessage()+")");
		}
	}



}

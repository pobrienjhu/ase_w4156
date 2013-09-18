package edu.columbia.ase.auctionhouse.core.app.impl;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.columbia.ase.auctionhouse.core.app.AuctionHouse;
import edu.columbia.ase.auctionhouse.core.app.AuctionHouseDataSource;
import edu.columbia.ase.auctionhouse.core.app.Customer;
import edu.columbia.ase.auctionhouse.core.data.Auction;
import edu.columbia.ase.auctionhouse.core.data.AuctionBid;

public class AuctionHouseImpl extends Thread implements AuctionHouse {

	private Logger log = LoggerFactory.getLogger(AuctionHouseImpl.class);

	
	private AuctionHouseDataSource auctionHouseDataSource;	

	private Map<Integer, Auction> activeAuctionMap = new ConcurrentSkipListMap<Integer,Auction>();
	
	
	@Override
	public void startAuctionHouseServer() {
		
		this.start();
		
	}

	@Override
	public void run() {
		log.info("AuctionHouse Server started ... ");
		for(;;){}
	}
	

	@Override
	public void setAuctionHouseDataSource(AuctionHouseDataSource auctionHouseDataSource) {
		this.auctionHouseDataSource = auctionHouseDataSource;
	}

	@Override
	public void acceptBid(AuctionBid bid) throws Exception{
		log.info("Recieved Bid "+bid);
		
		if(bid == null ){
			log.error("Unable to process NULL bid!");
			throw new Exception("Bid is Null");
		}
		
		Integer auctionId = new Integer(bid.getAuctionId());
		
		if( ! activeAuctionMap.containsKey(auctionId) ){
			log.error("Unable to process bid. Auction "+auctionId+" is not an active auction! ");
			throw new Exception("Auction "+auctionId+" is not an active auction! ");

		}
		
		if(auctionHouseDataSource == null){
			log.warn("Auction House Data Source not set!");
			throw new Exception("Auction House Data Source not set!");
		}
		
		
		try {
			// Verify the customerId is valid first 
			auctionHouseDataSource.getCustomer(new Integer(bid.getCustomerId())); 
			Auction auction = activeAuctionMap.get(auctionId);		
			auction.acceptBid(bid);
		} catch (Exception e) {
			log.error("Unable to process bid. Root Cause ("+e+")");
			throw e;
		}				
		
	}

	@Override
	public Collection<Auction> getActiveAuctions() {
		return activeAuctionMap.values();
	}

	@Override
	public void startAuction(Integer auctionId) {
		log.info("start auction |"+auctionId+"| @ "+(new Date()) );
		
		if(auctionId == null){
			log.warn("Unable to start Auction for null auctionId!");
			return;
		}
		
		if(auctionHouseDataSource == null){
			log.warn("Unable to start Auction |"+auctionId+"| Auction House Data Source not set!");
			return;
		}
		
		if( activeAuctionMap.containsKey(auctionId) ){
			log.warn("Unable to start Auction |"+auctionId+"| Auction already active!");
			return;
		}
		
		
		try {
			Auction auction = auctionHouseDataSource.getAuction(auctionId);
			
			activeAuctionMap.put(auctionId, auction);
		} catch (Exception e) {
			log.warn("Unable to start Auction |"+auctionId+"|. Root Cause ("+e.getMessage()+")");

		}
		
		
		
	}

	@Override
	public void endAuction(Integer auctionId) {
		log.info("end auction |"+auctionId+"| @ "+(new Date()) );		
		
		if(auctionId == null){
			log.warn("Unable to end Auction for null auctionId!");
			return;
		}
		
		if(auctionHouseDataSource == null){
			log.warn("Unable to end Auction |"+auctionId+"| Auction House Data Source not set!");
			return;
		}
		
		if( ! activeAuctionMap.containsKey(auctionId) ){
			log.warn("Unable to end Auction |"+auctionId+"| Auction is not active!");
			return;
		}
		
		Auction auction = activeAuctionMap.get(auctionId);
		
		try {			
			activeAuctionMap.remove(auctionId);
		} catch (Exception e) {
			log.warn("Unable to end Auction |"+auctionId+"|. Root Cause ("+e.getMessage()+")");
		}
		
		try {
			AuctionBid bid = auction.getWinningBid();
			
			log.info("Auction "+auction.getAuctionId()+" won by customer "+bid.getCustomerId());
			
			try {
				Customer customer = auctionHouseDataSource.getCustomer(bid.getCustomerId());
				customer.auctionWon(auction);
			} catch (Exception e) {
				log.error("Unable to notify auction winner1 Root Cause ("+e.getMessage()+")");
			}
			
			
		} catch (Exception e) {
			log.warn("Unable to process winning bid. Root Cause ("+e.getMessage()+")");
		}
		
		
	}

	@Override
	public Integer registerCustomer(Customer customer) throws Exception {
		return auctionHouseDataSource.addCustomer(customer);
	}



}

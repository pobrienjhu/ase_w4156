package edu.columbia.ase.auctionhouse.ejb.app;

import java.util.Collection;
import java.util.List;

import edu.columbia.ase.auctionhouse.ejb.data.Auction;
import edu.columbia.ase.auctionhouse.ejb.data.AuctionBid;


public interface AuctionHouse {

	/*
	 * Server startup method
	 */
	public void startAuctionHouseServer();
	
	
	/*
	 * Dependency Injection methods
	 */	
	public void setAuctionHouseDataSource(AuctionHouseDataSource dataSource);
	
	
	/*
	 * Methods used for interaction while running.
	 */
	public Integer registerCustomer() throws Exception;
	
	public void acceptBid( AuctionBid bid) throws Exception;
	
	public Collection<Auction> getActiveAuctions();
	
	public void startAuction(Integer auctionId);
	
	public void endAuction(Integer auctionId);

}

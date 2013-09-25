package edu.columbia.ase.auctionhouse.core.app;

import java.util.Collection;


import edu.columbia.ase.auctionhouse.core.data.Auction;
import edu.columbia.ase.auctionhouse.core.data.AuctionBid;

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
	public Integer registerCustomer(Customer customer) throws Exception;
	
	public void acceptBid( AuctionBid bid) throws Exception;
	
	public Collection<Auction> getActiveAuctions();
	
	public void startAuction(Integer auctionId);
	
	public void endAuction(Integer auctionId);

}

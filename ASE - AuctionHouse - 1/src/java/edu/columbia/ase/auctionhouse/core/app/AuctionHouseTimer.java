package edu.columbia.ase.auctionhouse.core.app;

import edu.columbia.ase.auctionhouse.core.data.Auction;


public interface AuctionHouseTimer {
	
	public void setAuctionHouseDataSource(AuctionHouseDataSource auctionHouseDataSource);

	public void setAuctionHouse(AuctionHouse auctionHouse);
	
	public void startAuctionTimer() throws Exception;
	
	public void addAuction(Auction auction) throws Exception;
	
}

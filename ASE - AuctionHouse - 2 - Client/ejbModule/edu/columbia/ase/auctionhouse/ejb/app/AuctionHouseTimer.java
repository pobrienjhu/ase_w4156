package edu.columbia.ase.auctionhouse.ejb.app;

import javax.ejb.Local;

import edu.columbia.ase.auctionhouse.ejb.data.Auction;

@Local
public interface AuctionHouseTimer {
	
	public void setAuctionHouseDataSource(AuctionHouseDataSource auctionHouseDataSource);
	
	public void startAuctionTimer() throws Exception;
	
	public void addAuction(Auction auction) throws Exception;
	
}

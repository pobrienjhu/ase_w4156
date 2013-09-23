package edu.columbia.ase.auctionhouse.ejb.app;

import javax.ejb.Local;

import edu.columbia.ase.auctionhouse.ejb.data.Auction;

@Local
public interface AuctionHouseWinningPublisher {

	public void sendWinningMessage(int customerId, Auction auction) throws Exception;
	
}

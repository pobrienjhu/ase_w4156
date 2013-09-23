package edu.columbia.ase.auctionhouse.ejb.app.timer;

import java.util.Date;

import edu.columbia.ase.auctionhouse.ejb.data.Auction;

public interface AuctionTimer {

	public void scheduleAuctionEvent(Date time, Auction auction);
	
}

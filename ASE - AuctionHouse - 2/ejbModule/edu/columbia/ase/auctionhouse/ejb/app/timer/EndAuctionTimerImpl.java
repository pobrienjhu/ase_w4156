package edu.columbia.ase.auctionhouse.ejb.app.timer;

import java.util.Date;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Timeout;
import javax.ejb.Timer;
import javax.ejb.TimerService;

import edu.columbia.ase.auctionhouse.ejb.data.Auction;

import edu.columbia.ase.auctionhouse.ejb.app.AuctionHouseLocal;

@Singleton (mappedName="edu.columbia.ase.auctionhouse.ejb.app.timer.EndAuctionTimer")
public class EndAuctionTimerImpl implements EndAuctionTimer  {

	private @Resource TimerService timerService;
	
	@EJB 
	private AuctionHouseLocal auctionHouse;
	

	@Override
	public void scheduleAuctionEvent(Date endTime, Auction auction){
		timerService.createTimer(endTime, new Integer( auction.getAuctionId() ));
	}

	
	@Timeout
	public void handleEndAuction(Timer timer){
		Integer auctionId = (Integer) timer.getInfo();

		auctionHouse.endAuction(auctionId);		
	}
	
	
	
}

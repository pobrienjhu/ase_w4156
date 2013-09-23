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

@Singleton (mappedName="edu.columbia.ase.auctionhouse.ejb.app.timer.StartAuctionTimer")
public class StartAuctionTimerImpl implements StartAuctionTimer {

	
	private @Resource TimerService timerService;
	
	@EJB
	private AuctionHouseLocal auctionHouse;
	
	@Override
	public void scheduleAuctionEvent(Date time, Auction auction){
		timerService.createTimer(time, new Integer( auction.getAuctionId() ));
	}

	
	@Timeout
	public void handleStartAuction(Timer timer){
		Integer auctionId = (Integer) timer.getInfo();
		
		auctionHouse.startAuction(auctionId);		
	}
	
	
	
}

package edu.columbia.ase.auctionhouse.ejb.app.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.columbia.ase.auctionhouse.ejb.app.AuctionHouseWinningPublisher;
import edu.columbia.ase.auctionhouse.ejb.app.Customer;
import edu.columbia.ase.auctionhouse.ejb.data.Auction;

public class EjbCustomer implements Customer {

	private Logger log = LoggerFactory.getLogger(EjbCustomer.class);

	
	private AuctionHouseWinningPublisher auctionHouseWinningPublisher;
	private Integer customerId;
	
	
	
	public EjbCustomer(
			AuctionHouseWinningPublisher auctionHouseWinningPublisher,
			Integer customerId) {
		super();
		this.auctionHouseWinningPublisher = auctionHouseWinningPublisher;
		this.customerId = customerId;
	}


	@Override
	public void auctionWon(Auction auction) {
		try {
			auctionHouseWinningPublisher.sendWinningMessage(customerId, auction);
		} catch (Exception e) {
			log.error("Unable to send winning auction notification. Root Cause ("+e.getMessage()+")");
		}
	}


	@Override
	public void setId(int auctionId) {
		// TODO Auto-generated method stub

	}


}

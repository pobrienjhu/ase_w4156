package edu.columbia.ase.auctionhouse.corba;

import java.util.Collection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.columbia.ase.auctionhouse.corba.customer.CustomerCorbaImpl;
import edu.columbia.ase.auctionhouse.core.data.AuctionBid;

public class AuctionHouseCorbaImpl extends AuctionHousePOA {

	private Logger log = LoggerFactory.getLogger(AuctionHouseCorbaImpl.class);
	
	private edu.columbia.ase.auctionhouse.core.app.AuctionHouse auctionHouse;
	
	
	public AuctionHouseCorbaImpl(edu.columbia.ase.auctionhouse.core.app.AuctionHouse auctionHouse) {
		super();
		this.auctionHouse = auctionHouse;
	}


	@Override
	public Auction[] getActiveAuctions() {
		
		Collection<edu.columbia.ase.auctionhouse.core.data.Auction> activeAuctions = auctionHouse.getActiveAuctions();
		
		Auction[] auctions = new Auction[activeAuctions.size()];
		
		int auctionCount=0;
		for(edu.columbia.ase.auctionhouse.core.data.Auction activeAuction: activeAuctions ){
			auctions[auctionCount] = new AuctionImpl( 
						activeAuction.getAuctionId(), 
						activeAuction.getCurrentPrice(), 
						activeAuction.getItemCondition(), 
						activeAuction.getItem().getItemName() );
			auctionCount++;
		}
						
		return auctions;
	}

	@Override
	public void registerCustomer(Customer cust) throws InvalidRegistration {
		CustomerCorbaImpl customerCorbaImpl = new CustomerCorbaImpl(cust);
		try {
			Integer customerId = auctionHouse.registerCustomer(customerCorbaImpl);
			cust.setId(customerId.intValue());
		} catch (Exception e) {
			log.error("Error registering customer. Root Cause ("+e.getMessage()+")");
			throw new InvalidRegistration("Error registering customer. Root Cause ("+e.getMessage()+")");
		}		
	}
	
	@Override
	public void placeBid(int custId, int auctionId, double bidAmount) throws InvalidBid {
		
		try {
			AuctionBid bid = new AuctionBid(auctionId, custId, bidAmount, new Date());
			auctionHouse.acceptBid(bid);
		} catch (Exception e) {
			log.error("Invalid Bid. Root Cause ("+e.getMessage()+")");
			throw new InvalidBid("Invalid Bid. Root Cause ("+e.getMessage()+")");
		}
	}
	

	public edu.columbia.ase.auctionhouse.core.app.AuctionHouse getAuctionHouse() {
		return auctionHouse;
	}



	public void setAuctionHouse(edu.columbia.ase.auctionhouse.core.app.AuctionHouse auctionHouse) {
		this.auctionHouse = auctionHouse;
	}

}

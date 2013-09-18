package edu.columbia.ase.auctionhouse.corba.customer;

import edu.columbia.ase.auctionhouse.corba.AuctionImpl;
import edu.columbia.ase.auctionhouse.core.app.Customer;
import edu.columbia.ase.auctionhouse.core.data.Auction;

public class CustomerCorbaImpl implements Customer {
	
	private edu.columbia.ase.auctionhouse.corba.Customer customer;
 
	 
	public CustomerCorbaImpl(
			edu.columbia.ase.auctionhouse.corba.Customer customer) {
		super();
		this.customer = customer;
	}

	@Override
	public void auctionWon(Auction auct) {
		
		AuctionImpl auctionCorbaImpl = 
				new AuctionImpl(
						auct.getAuctionId(), 
						auct.getCurrentPrice(), 
						auct.getItemCondition(),
						auct.getItem().getItemName());
		
		customer.auctionWon(auctionCorbaImpl);
	}

	@Override
	public void setId(int custId) {
		customer.setId(custId);
	}


}

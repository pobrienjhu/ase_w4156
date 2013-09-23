package edu.columbia.ase.auctionhouse.ejb.app;

import edu.columbia.ase.auctionhouse.ejb.data.Auction;

public interface Customer {

	public void auctionWon( Auction auct  );
	
	public void setId( int custId );

}

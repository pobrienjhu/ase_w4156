package edu.columbia.ase.auctionhouse.core.app;

import edu.columbia.ase.auctionhouse.core.data.Auction;

public interface Customer {

	public void auctionWon( Auction auct  );
	
	public void setId( int custId );
	

}

package edu.columbia.ase.auctionhouse.corba;

public class AuctionImpl extends Auction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8156225590804730235L;

	
	
	public AuctionImpl() {

	}



	public AuctionImpl(
			int auctionId,
			double currentPrice,
			java.lang.String itemDescription,
			java.lang.String itemCondition ) {
		
		this.auctionId = auctionId;
		this.currentPrice = currentPrice;
		this.itemCondition = itemDescription;
		this.itemDescription = itemCondition;
	}

	
}

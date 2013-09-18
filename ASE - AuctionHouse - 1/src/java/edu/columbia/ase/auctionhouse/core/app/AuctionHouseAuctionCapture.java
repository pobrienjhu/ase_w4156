package edu.columbia.ase.auctionhouse.core.app;

import java.io.IOException;

import edu.columbia.ase.auctionhouse.core.data.Auction;
import edu.columbia.ase.auctionhouse.core.data.AuctionItem;


public interface AuctionHouseAuctionCapture {

	public void loadAuctions() throws IOException;
	
	public void setAuctionHouseDataSource(AuctionHouseDataSource auctionHouseDataSource);
	
	public void setAuctionHouseTimer(AuctionHouseTimer auctionHouseTimer);

	public void addAuction(Auction auction) throws Exception;
	
	public void addItem(AuctionItem item) throws Exception;
	
}

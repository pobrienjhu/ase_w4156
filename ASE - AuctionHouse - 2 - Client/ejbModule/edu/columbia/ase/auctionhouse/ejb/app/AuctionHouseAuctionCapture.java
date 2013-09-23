package edu.columbia.ase.auctionhouse.ejb.app;

import java.io.IOException;

import javax.ejb.Local;

import edu.columbia.ase.auctionhouse.ejb.data.Auction;
import edu.columbia.ase.auctionhouse.ejb.data.AuctionItem;

@Local
public interface AuctionHouseAuctionCapture {

	public void loadAuctions() throws IOException;
	
	public void setAuctionHouseDataSource(AuctionHouseDataSource auctionHouseDataSource);
	
	public void setAuctionHouseTimer(AuctionHouseTimer auctionHouseTimer);

	public void addAuction(Auction auction) throws Exception;
	
	public void addItem(AuctionItem item) throws Exception;
	
}

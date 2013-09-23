package edu.columbia.ase.auctionhouse.ejb.app;

import java.util.Collection;

import javax.ejb.Local;

import edu.columbia.ase.auctionhouse.ejb.data.Auction;
import edu.columbia.ase.auctionhouse.ejb.data.AuctionItem;

@Local
public interface AuctionHouseDataSource {

	public Auction getAuction(Integer auctionId) throws Exception;
	
	public Collection<Auction> getAllAuctions() throws Exception;
	
	public void addAuction(Auction auction) throws Exception;
	
	public void removeAuction(Integer auctionId) throws Exception;
	
	public AuctionItem getAuctionItem(Integer auctionItemId) throws Exception;
	
	public void addAuctionItem(AuctionItem auctionItem) throws Exception;
	
	public void removeAuctionItem(Integer auctionItemId) throws Exception;
	
	public Integer addCustomer() throws Exception;
	
	public Customer getCustomer(Integer customerId) throws Exception;
	
}

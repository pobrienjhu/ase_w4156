package edu.columbia.ase.auctionhouse.core.app;

import java.util.Collection;

import edu.columbia.ase.auctionhouse.core.data.Auction;
import edu.columbia.ase.auctionhouse.core.data.AuctionItem;

public interface AuctionHouseDataSource {

	public Auction getAuction(Integer auctionId) throws Exception;
	
	public Collection<Auction> getAllAuctions() throws Exception;
	
	public void addAuction(Auction auction) throws Exception;
	
	public void removeAuction(Integer auctionId) throws Exception;
	
	public AuctionItem getAuctionItem(Integer auctionItemId) throws Exception;
	
	public void addAuctionItem(AuctionItem auctionItem) throws Exception;
	
	public void removeAuctionItem(Integer auctionItemId) throws Exception;
	
	public Integer addCustomer(Customer customer) throws Exception;
	
	public Customer getCustomer(Integer customerId) throws Exception;
	
}

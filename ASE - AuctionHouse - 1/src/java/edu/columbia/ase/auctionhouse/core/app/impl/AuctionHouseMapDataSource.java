package edu.columbia.ase.auctionhouse.core.app.impl;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import edu.columbia.ase.auctionhouse.core.app.AuctionHouseDataSource;
import edu.columbia.ase.auctionhouse.core.app.Customer;
import edu.columbia.ase.auctionhouse.core.data.Auction;
import edu.columbia.ase.auctionhouse.core.data.AuctionItem;

public class AuctionHouseMapDataSource implements AuctionHouseDataSource {


	Map<Integer, Auction> auctionMap = new TreeMap<Integer, Auction>();
	
	Map<Integer, AuctionItem> auctionItemMap = new TreeMap<Integer, AuctionItem>();

	Map<Integer, Customer> customerMap = new TreeMap<Integer, Customer>();

	AtomicInteger maxCustomerId = new AtomicInteger(0);
	
	@Override
	public Auction getAuction(Integer auctionId) throws Exception {

		if( ! auctionMap.containsKey(auctionId)){
			throw new Exception("Unable to retrieve Auction with id |"+auctionId+"| does not exists");
		}
		
		return auctionMap.get(auctionId);
	}

	@Override
	public void addAuction(Auction auction) throws Exception {
		Integer auctionId = new Integer(auction.getAuctionId());
		
		if( auctionMap.containsKey(auctionId)){
			throw new Exception("Unable to add Auction with id |"+auctionId+"| already exists");
		}
		
		auctionMap.put(auctionId, auction);
	}

	@Override
	public void removeAuction(Integer auctionId) throws Exception {
		if( ! auctionMap.containsKey(auctionId)){
			throw new Exception("Unable to remove Auction with id |"+auctionId+"| does not exists");
		}
		
		auctionMap.remove(auctionId);
	}

	@Override
	public AuctionItem getAuctionItem(Integer auctionItemId) throws Exception {
		if( ! auctionItemMap.containsKey(auctionItemId)){
			throw new Exception("Unable to retrieve AuctionItem with id |"+auctionItemId+"| does not exists");
		}		
		
		return auctionItemMap.get(auctionItemId);
	}

	@Override
	public void addAuctionItem(AuctionItem auctionItem) throws Exception {
		Integer auctionItemId = new Integer(auctionItem.getItemId());
		
		if( auctionItemMap.containsKey(auctionItemId)){
			throw new Exception("Unable to add AuctionItem with id |"+auctionItemId+"| and name |"+auctionItem.getItemName()+"|. Item id already exists");
		}			
		
		auctionItemMap.put(auctionItemId, auctionItem);
	}

	@Override
	public void removeAuctionItem(Integer auctionItemId) throws Exception {
		
		if( ! auctionItemMap.containsKey(auctionItemId)){
			throw new Exception("Unable to remove AuctionItem with id |"+auctionItemId+"| does not exists");
		}	
		
		auctionItemMap.remove(auctionItemId);
	}

	@Override
	public Collection<Auction> getAllAuctions() throws Exception {
		return auctionMap.values();
	}

	@Override
	public Integer addCustomer(Customer customer) throws Exception {
		Integer custKey = new Integer(maxCustomerId.getAndIncrement());
		customerMap.put(custKey, customer);
		return custKey;
	}

	@Override
	public Customer getCustomer(Integer customerId) throws Exception {
		
		if( customerId == null){
			throw new Exception("customerId is NULL");
		}
		
		if(! customerMap.containsKey(customerId)){
			throw new Exception("Customer with id "+customerId+" does not exist!");
		}
		
		return customerMap.get(customerId);
	}


}

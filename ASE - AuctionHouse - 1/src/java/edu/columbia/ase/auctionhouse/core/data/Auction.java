package edu.columbia.ase.auctionhouse.core.data;

import java.io.Serializable;
import java.util.Date;
import java.util.concurrent.PriorityBlockingQueue;



public class Auction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -84349937604138944L;
	
	private int auctionId;

	private int auctionItemId;
	
	private AuctionItem item;
	
	private String itemCondition;
	
	private int startOffsetMinutes; 
	
	private int auctionLengthInMinutes;
	
	private Date startTime;
	
	private Date endTime;
	
	private double startPrice;
	
	private PriorityBlockingQueue<AuctionBid> bids;
	
	public Auction() {
		super();
		this.bids = new PriorityBlockingQueue<AuctionBid>();
	}	
	
	public Auction(int auctionId, AuctionItem item, Date startTime,
			Date endTime, double startPrice) {
		this();
		this.auctionId = auctionId;
		this.item = item;
		this.startTime = startTime;
		this.endTime = endTime;
		this.startPrice = startPrice;
	}
	
	/*
	 * Needed to instantiate the PriorityBockingQueue during derserialization
	 * if read in using xstream.
	 */
	private Object readResolve() {
			bids = new PriorityBlockingQueue<AuctionBid>();
		    return this;
	}
	
	public void acceptBid(AuctionBid bid) throws Exception{
		
		if( bid.getBidAmount() <= getCurrentPrice()){
			throw new Exception("Bid of "+bid.getBidAmount()+" is less then the current auction price of "+getCurrentPrice());
		}
		
		bids.add(bid);
	}
	
	public AuctionBid getWinningBid() throws Exception{
				
		if( ! hasBids() ){
			throw new Exception("No Bids have been placed on this item!");
		}
		
		return bids.peek();	
	}

	public boolean hasBids(){
		return ! bids.isEmpty();
	}
	
	public int getAuctionId() {
		return auctionId;
	}

	public void setAuctionId(int auctionId) {
		this.auctionId = auctionId;
	}

	public AuctionItem getItem() {
		return item;
	}

	public void setItem(AuctionItem item) {
		this.item = item;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public double getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(double startPrice) {
		this.startPrice = startPrice;
	}
	
	public double getCurrentPrice(){
		if( ! hasBids() ){
			return getStartPrice();
		}
		
		return bids.peek().getBidAmount();			
		
	}

	public int getAuctionItemId() {
		return auctionItemId;
	}

	public void setAuctionItemId(int auctionItemId) {
		this.auctionItemId = auctionItemId;
	}
	
	
	public String getItemCondition() {
		return itemCondition;
	}

	public void setItemCondition(String itemCondition) {
		this.itemCondition = itemCondition;
	}

	public int getStartOffsetMinutes() {
		return startOffsetMinutes;
	}

	public void setStartOffsetMinutes(int startOffsetMinutes) {
		this.startOffsetMinutes = startOffsetMinutes;
	}

	public int getAuctionLengthInMinutes() {
		return auctionLengthInMinutes;
	}

	public void setAuctionLengthInMinutes(int auctionLengthInMinutes) {
		this.auctionLengthInMinutes = auctionLengthInMinutes;
	}

	@Override
	public String toString() {
		return "Auction [auctionId=" + auctionId + ", auctionItemId="
				+ auctionItemId + ", startTime=" + startTime + ", endTime="
				+ endTime + ", startPrice=" + startPrice + "]";
	}
	
	
	
	

}

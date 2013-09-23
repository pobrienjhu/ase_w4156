package edu.columbia.ase.auctionhouse.ejb.data;

import java.io.Serializable;

public class WinningAuctionMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6572048279777840071L;
	
	
	private int winningCustomer;
	private int auctionId;
	private double winningPrice;
	
	
	public WinningAuctionMessage(int winningCustomer, int auctionId,
			double winningPrice) {
		super();
		this.winningCustomer = winningCustomer;
		this.auctionId = auctionId;
		this.winningPrice = winningPrice;
	}
	
	
	public int getWinningCustomer() {
		return winningCustomer;
	}
	public void setWinningCustomer(int winningCustomer) {
		this.winningCustomer = winningCustomer;
	}
	public int getAuctionId() {
		return auctionId;
	}
	public void setAuctionId(int auctionId) {
		this.auctionId = auctionId;
	}
	public double getWinningPrice() {
		return winningPrice;
	}
	public void setWinningPrice(double winningPrice) {
		this.winningPrice = winningPrice;
	}


	@Override
	public String toString() {
		return "WinningAuctionMessage [winningCustomer=" + winningCustomer
				+ ", auctionId=" + auctionId + ", winningPrice=" + winningPrice
				+ "]";
	}
	
	
	
	
}

package edu.columbia.ase.auctionhouse.core.data;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.CompareToBuilder;

@XmlRootElement(name="auctionBid")
public class AuctionBid implements Comparable<AuctionBid>, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2948454508965140306L;
	
	private int auctionId;
	private int customerId;
	private double bidAmount;
	private Date bidTime;
	
	
	
	public AuctionBid() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AuctionBid(int auctionId, int customerId, double bidAmount, Date bidTime) {
		super();
		this.auctionId = auctionId;
		this.customerId = customerId;
		this.bidAmount = bidAmount;
		this.bidTime = bidTime;
	}
	
	@XmlAttribute(name="auctionId")
	public int getAuctionId() {
		return auctionId;
	}
	
	public void setAuctionId(int auctionId) {
		this.auctionId = auctionId;
	}
	
	@XmlAttribute(name="customerId")
	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	@XmlAttribute(name="bidAmount")
	public double getBidAmount() {
		return bidAmount;
	}
	
	public void setBidAmount(double bidAmount) {
		this.bidAmount = bidAmount;
	}
	
	@XmlAttribute(name="bidTime")
	public Date getBidTime() {
		return bidTime;
	}

	public void setBidTime(Date bidTime) {
		this.bidTime = bidTime;
	}

	@Override
	public String toString() {
		return "AuctionBid [auctionId=" + auctionId + ", customerId="
				+ customerId + ", bidAmount=" + bidAmount + ", bidTime="
				+ bidTime + "]";
	}

	@Override
	public int compareTo(AuctionBid that) {
		return new CompareToBuilder().append(that.bidAmount, this.bidAmount).toComparison();
	}
	
	
}

package edu.columbia.ase.auctionhouse.core.app.impl;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

import edu.columbia.ase.auctionhouse.core.app.Customer;
import edu.columbia.ase.auctionhouse.core.data.Auction;

public class WebServiceCustomerImpl implements Customer {

	private String customerWebServiceUrl;
	
	
	
	public WebServiceCustomerImpl(String customerWebServiceUrl) {
		super();
		this.customerWebServiceUrl = customerWebServiceUrl;
	}

	@Override
	public void auctionWon(Auction auction) {
		
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client auctionHouseCustomer = dcf.createClient(customerWebServiceUrl);
		
		try {
			auctionHouseCustomer.invoke("auctionWon", auction.getAuctionId(), auction.getCurrentPrice());
		} catch (Exception e) {
			System.out.println("Unable to notify customer of winning status. Root Cause ("+e.getMessage()+")");
		}
	}

	@Override
	public void setId(int custId) {
		// TODO Auto-generated method stub

	}

	public String getCustomerWebServiceUrl() {
		return customerWebServiceUrl;
	}

	public void setCustomerWebServiceUrl(String customerWebServiceUrl) {
		this.customerWebServiceUrl = customerWebServiceUrl;
	}

	

}

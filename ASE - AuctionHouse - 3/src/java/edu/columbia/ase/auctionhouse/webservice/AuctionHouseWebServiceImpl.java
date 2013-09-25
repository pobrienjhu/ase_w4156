package edu.columbia.ase.auctionhouse.webservice;

import java.util.Collection;
import java.util.Date;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import edu.columbia.ase.auctionhouse.core.app.impl.WebServiceCustomerImpl;
import edu.columbia.ase.auctionhouse.core.data.Auction;
import edu.columbia.ase.auctionhouse.core.data.AuctionBid;

@Service("auctionHouseEndpoint")
@WebService (serviceName="AuctionHouse")
public class AuctionHouseWebServiceImpl {

	private Logger log = LoggerFactory.getLogger(AuctionHouseWebServiceImpl.class);
	
	private edu.columbia.ase.auctionhouse.core.app.AuctionHouse auctionHouse;
	
	public AuctionHouseWebServiceImpl() {
		super();
	}

	@WebMethod(operationName="getActiveAuctions")
	public Collection<Auction> getActiveAuctions() {
		return auctionHouse.getActiveAuctions();
	}

	@WebMethod(operationName="registerCustomer")
	public @WebResult(name="customerId") Integer registerCustomer(@WebParam(name="customerWsdlUrl") String customerWsdlUrl) throws Exception {
		
		log.info("Registering customer with url |"+customerWsdlUrl+"|");
		
		WebServiceCustomerImpl customer = new WebServiceCustomerImpl(customerWsdlUrl);
		
		try {
			return ( auctionHouse.registerCustomer(customer) );
		} catch (Exception e) {
			log.error("Error registering customer. Root Cause ("+e.getMessage()+")");
			throw new Exception("Error registering customer. Root Cause ("+e.getMessage()+")");
		}		
	}
	
	@WebMethod(operationName="placeBid")
	public void placeBid(@WebParam(name="customerId") int custId, @WebParam(name="auctionId") int auctionId, @WebParam(name="bidAmount") double bidAmount) throws Exception {
		
		try {
			AuctionBid bid = new AuctionBid(auctionId, custId, bidAmount, new Date());
			auctionHouse.acceptBid(bid);
		} catch (Exception e) {
			log.error("Invalid Bid. Root Cause ("+e.getMessage()+")");
			throw new Exception("Invalid Bid. Root Cause ("+e.getMessage()+")");
		}
	}
	
	@WebMethod(exclude=true)
	public edu.columbia.ase.auctionhouse.core.app.AuctionHouse getAuctionHouse() {
		return auctionHouse;
	}

	@WebMethod(exclude=true)
	public void setAuctionHouse(edu.columbia.ase.auctionhouse.core.app.AuctionHouse auctionHouse) {
		this.auctionHouse = auctionHouse;
	}

}

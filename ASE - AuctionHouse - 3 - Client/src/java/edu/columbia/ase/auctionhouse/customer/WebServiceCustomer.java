package edu.columbia.ase.auctionhouse.customer;

import java.util.ArrayList;
import java.util.Scanner;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.commons.lang3.StringUtils;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.endpoint.Client;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import edu.columbia.ase.auctionhouse.webservice.Auction;


@Service("auctionHouseCustomerEndpoint")
@WebService (serviceName="AuctionHouseCustomer")
public class WebServiceCustomer implements Runnable {

	private int customerId; 
	private Client auctionHouseClient;
	
	private String auctionHouseWebServiceURL;	
	private String customerURL;
	
	public WebServiceCustomer() {
		super();
	}

	@WebMethod(exclude=true)
	public void startCustomer(){
		
		try {
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			auctionHouseClient = dcf.createClient(auctionHouseWebServiceURL);
			
			Object[] customerIdObj = auctionHouseClient.invoke("registerCustomer", customerURL);
			System.out.println("customerIdObj is |"+customerIdObj[0]+"|");
			Integer customerId = (Integer) customerIdObj[0];
			
			this.customerId = customerId.intValue();
			
		} catch (Exception e) {
			System.out.println("Unable to connect to Auction House server |"+auctionHouseWebServiceURL+"|. Root Cause ("+e.getMessage()+")");
			return;
		}
		
		(new Thread(this)).start();
	}
	
	@WebMethod(operationName="auctionWon")
	public void auctionWon(@WebParam(name="auctionId") int auctionId, @WebParam(name="auctionPrice") double auctionPrice) {
		System.out.println("You have won auction "+auctionId+" for price "+auctionPrice);
	}

	@WebMethod(operationName="setId")	
	public void setId(@WebParam(name="customerId") int customerId) {
		System.out.println("Regestration complete! id "+customerId+" assigned!");
		this.customerId = customerId;
	}

	@WebMethod(exclude=true)
	private void getAuctionListings(){
		
		try {
			 
			Object[] auctions = auctionHouseClient.invoke("getActiveAuctions");
						
	        if( auctions.length == 0){
	        	System.out.println("Nothing for Sale yet!");
	        	return;
	        }
	        
	        if( auctions[0] instanceof ArrayList ){
	        	
	        	ArrayList auctionList = (ArrayList) auctions[0];

		        if( auctionList.size() == 0){
		        	System.out.println("Nothing for Sale yet!");
		        	return;
		        }
	        	
		        System.out.println("Current active auctions:");

		        for( int i=0; i < auctionList.size(); i++){
		        	
		        	Auction auction = (Auction) auctionList.get(i);
		        	
		        	System.out.println("	Auction |"+auction.getAuctionId()+"| for item |"+auction.getAuctionItemId()+"| condition: |"+auction.getItemCondition()+"| current price: |"+auction.getCurrentPrice()+"|");
		        }        	
	        }
	        
		} catch (Exception e) {
			System.out.println("Unable to show active auctions. Root Cause ("+e.getMessage()+")");
		}

	}
	
	@WebMethod(exclude=true)
	private void placeBid() throws Exception{
		Scanner scanner = new Scanner(System.in);

		System.out.println("");
		System.out.println("Enter the id of the auction you wish to bid on:");
		int auctionId= scanner.nextInt();
		System.out.println("Enter the price you wish to bid:");
		double bidAmount = scanner.nextDouble();
		
		//auctionHouse.placeBid(customerId, auctionId, bidAmount);
		auctionHouseClient.invoke("placeBid", customerId, auctionId, bidAmount);

		System.out.println("Bid of "+bidAmount+" accepted for auction "+auctionId);
	}
	
	@WebMethod(exclude=true)
	@Override
	public void run() {
		Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Auction House!");
                
        for(;;){
        	
        	System.out.println("Please choose an action:");
        	System.out.println("	1 - View available auctions.");
        	System.out.println("	2 - place a bid.");
        	String action = scanner.nextLine();
        	
        	if( StringUtils.equals(action, "1")){
        		getAuctionListings();
        	}
        	else if( StringUtils.equals(action, "2")){
        		try {
					placeBid();
				} catch (Exception e) {
					System.out.println("Unable to place bid. Root Cause ("+e.getMessage()+")");
				}
        	}
        	else{
        		System.out.println("Invalid action |"+action+"|");
        	}
    		System.out.println("");
        }
	}

	public String getAuctionHouseWebServiceURL() {
		return auctionHouseWebServiceURL;
	}

	public void setAuctionHouseWebServiceURL(String auctionHouseWebServiceURL) {
		this.auctionHouseWebServiceURL = auctionHouseWebServiceURL;
	}

	public String getCustomerURL() {
		return customerURL;
	}

	public void setCustomerURL(String customerURL) {
		this.customerURL = customerURL;
	}

	
	
	
}

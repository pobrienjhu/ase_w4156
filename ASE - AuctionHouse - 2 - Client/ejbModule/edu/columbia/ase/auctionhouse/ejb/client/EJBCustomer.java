package edu.columbia.ase.auctionhouse.ejb.client;

import java.util.Collection;
import java.util.Date;
import java.util.Scanner;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.NamingException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.lang3.StringUtils;

import edu.columbia.ase.auctionhouse.ejb.data.Auction;
import edu.columbia.ase.auctionhouse.ejb.data.AuctionBid;
import edu.columbia.ase.auctionhouse.ejb.data.WinningAuctionMessage;
import edu.columbia.ase.auctionhouse.ejb.app.AuctionHouseRemote;


public class EJBCustomer implements Runnable, MessageListener {

	private AuctionHouseRemote auctionHouse;
	private int customerId; 
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			AuctionHouseRemote auctionHouse = doLookup();
			
			Integer custId = auctionHouse.registerCustomer();
			
			EJBCustomer customer = new EJBCustomer(auctionHouse, custId.intValue());
			
			(new Thread(customer)).start();
		} catch (Exception e) {
			System.out.println("Unable to start EJB Customer. Root Cause ("+e.getMessage()+")");
		}
	}
	
	
	
	public EJBCustomer(AuctionHouseRemote auctionHouse, int customerId) {
		super();
		this.auctionHouse = auctionHouse;
		this.customerId = customerId;
		connectToMQ();
	}



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
        		getAuctionListings(auctionHouse);
        	}
        	else if( StringUtils.equals(action, "2")){
        		try {
					placeBid(auctionHouse);
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
	
	private void placeBid(AuctionHouseRemote auctionHouse) throws Exception{
			Scanner scanner = new Scanner(System.in);

			System.out.println("");
			System.out.println("Enter the id of the auction you wish to bid on:");
			int auctionId= scanner.nextInt();
			System.out.println("Enter the price you wish to bid:");
			double bidAmount = scanner.nextDouble();
			
			auctionHouse.acceptBid( new AuctionBid(auctionId, customerId, bidAmount, new Date()) );

			System.out.println("Bid of "+bidAmount+" accepted for auction "+auctionId);
		}


	private void getAuctionListings(AuctionHouseRemote auctionHouse ){
        Collection<Auction> auctions = auctionHouse.getActiveAuctions();
        
        if( auctions.size() == 0){
        	System.out.println("Nothing for Sale yet!");
        	return;
        }
        
        System.out.println("Current active auctions:");
        
        for( Auction auction: auctions){
        	System.out.println("	Auction |"+auction.getAuctionId()+"| for item |"+auction.getItem().getItemName()+"| condition: |"+auction.getItemCondition()+"| current price: |"+auction.getCurrentPrice()+"|");
        }
	}
		
	private static AuctionHouseRemote doLookup() {
			Context context = null;
			AuctionHouseRemote bean = null;
			try {
				// 1. Obtaining Context
				context = ClientUtility.getInitialContext();
				
				// 2. Generate JNDI Lookup name
				String lookupName = getLookupName();
				
				// 3. Lookup and cast
				bean = (AuctionHouseRemote) context.lookup(lookupName);
				
			} catch (NamingException e) {
				e.printStackTrace();
			}
			return bean;
		}
		
		
	private static String getLookupName() {
		
		/*
		The app name is the EAR name of the deployed EJB without .ear suffix.
		Since we haven't deployed the application as a .ear,
		the app name for us will be an empty string
		*/
		String appName = "ASE - AuctionHouse - 2 - EAR";
		
		/* The module name is the JAR name of the deployed EJB
		without the .jar suffix.
		*/
		String moduleName = "ASE_-_AuctionHouse_-_2";
		
		/*AS7 allows each deployment to have an (optional) distinct name.
		This can be an empty string if distinct name is not specified.
		*/
		String distinctName = "";
		
		// The EJB bean implementation class name
		//String beanName = HelloWorldBean.class.getSimpleName();		
		String beanName = "AuctionHouseImpl";
		
		// Fully qualified remote interface name
		final String interfaceName = AuctionHouseRemote.class.getName();
		
		// Create a look up string name
		String name = "ejb:" + appName + "/" + moduleName + "/" +
		distinctName + "/" + beanName + "!" + interfaceName;
		
		System.out.println("Looking up bean |"+name+"|");
				
		return name;
		
	}

	
	/**
	 * Apache MQ listener code
	 */
	
	private Connection connection;
    private Session session;
    private Topic topic;

    private String url = "tcp://localhost:61616";

    private void connectToMQ(){
    	try {
			ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(url);
			connection = factory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			topic = session.createTopic("aseAuctionHouse.auctionWonMessages");

			MessageConsumer consumer = session.createConsumer(topic);
			consumer.setMessageListener(this);

			connection.start();
		} catch (JMSException e) {
			System.out.println("Unable to connect to the MQ Server. Root Cause ("+e.getMessage()+")");
		}
    }
	
	/*
	 * (non-Javadoc)
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	@Override
	public void onMessage(Message msg) {
	
		try {
			if (msg instanceof ObjectMessage) {
				ObjectMessage objMsg = (ObjectMessage) msg;
				WinningAuctionMessage winningAuctionMsg  = (WinningAuctionMessage) objMsg.getObject();
				if (winningAuctionMsg.getWinningCustomer() == customerId) {
					System.out.println("You have won auction "+winningAuctionMsg.getAuctionId()+" for price "+winningAuctionMsg.getWinningPrice());
				}
			}
		} catch (JMSException e) {
			System.out.println("Unable to process winningAuctionMessage. Root Cause ("+e+")");
		}
		
	}
}

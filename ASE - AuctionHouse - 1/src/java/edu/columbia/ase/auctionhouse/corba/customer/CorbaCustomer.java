package edu.columbia.ase.auctionhouse.corba.customer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.omg.BiDirPolicy.BIDIRECTIONAL_POLICY_TYPE;
import org.omg.BiDirPolicy.BOTH;
import org.omg.BiDirPolicy.BidirectionalPolicyValueHelper;
import org.omg.CORBA.Any;
import org.omg.CORBA.Policy;
import org.omg.PortableServer.IdAssignmentPolicyValue;
import org.omg.PortableServer.ImplicitActivationPolicyValue;
import org.omg.PortableServer.LifespanPolicyValue;
import org.omg.PortableServer.POA;

import edu.columbia.ase.auctionhouse.corba.Auction;
import edu.columbia.ase.auctionhouse.corba.AuctionHouse;
import edu.columbia.ase.auctionhouse.corba.AuctionHouseHelper;
import edu.columbia.ase.auctionhouse.corba.Customer;
import edu.columbia.ase.auctionhouse.corba.CustomerHelper;
import edu.columbia.ase.auctionhouse.corba.CustomerPOA;


public class CorbaCustomer extends CustomerPOA implements Runnable {

	private AuctionHouse auctionHouse;
	private int customerId = 1; // for testing
	
	
	public CorbaCustomer(AuctionHouse auctionHouse) {
		super();
		this.auctionHouse = auctionHouse;
	}

	@Override
	public void auctionWon(Auction auct) {
		System.out.println("You have won auction "+auct.auctionId+" for price "+auct.currentPrice);
	}

	@Override
	public void setId(int customerId) {
		System.out.println("Regestration complete! id "+customerId+" assigned!");
		this.customerId = customerId;
	}

	private void getAuctionListings(AuctionHouse auctionHouse ){
        Auction[] auctions = auctionHouse.getActiveAuctions();
        
        if( auctions.length == 0){
        	System.out.println("Nothing for Sale yet!");
        	return;
        }
        
        System.out.println("Current active auctions:");
        
        for( int i=0; i < auctions.length; i++){
        	System.out.println("	Auction |"+auctions[i].auctionId+"| for item |"+auctions[i].itemDescription+"| condition: |"+auctions[i].itemCondition+"| current price: |"+auctions[i].currentPrice+"|");
        }
	}
	
	private void placeBid(AuctionHouse auctionHouse ) throws Exception{
		Scanner scanner = new Scanner(System.in);

		System.out.println("");
		System.out.println("Enter the id of the auction you wish to bid on:");
		int auctionId= scanner.nextInt();
		System.out.println("Enter the price you wish to bid:");
		double bidAmount = scanner.nextDouble();
		
		auctionHouse.placeBid(customerId, auctionId, bidAmount);

		System.out.println("Bid of "+bidAmount+" accepted for auction "+auctionId);
	}
	
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
        Properties props = new Properties ();
        props.put ("org.omg.PortableInterceptor.ORBInitializerClass.bidir_init",
                   "org.jacorb.orb.giop.BiDirConnectionInitializer");
		
        org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args,props);

        BufferedReader reader = new BufferedReader(new FileReader(args[0]));

        // resolve name to get a reference to our server
        AuctionHouse auctionHouse = AuctionHouseHelper.narrow(orb.string_to_object(reader.readLine()));
                
        CorbaCustomer customer = new CorbaCustomer(auctionHouse);
        
		Any any = orb.create_any ();
        BidirectionalPolicyValueHelper.insert (any, BOTH.value);

        POA root_poa = (POA) orb.resolve_initial_references ("RootPOA");

        Policy[] policies = new Policy[4];
        policies[0] = root_poa.create_lifespan_policy (LifespanPolicyValue.TRANSIENT);

        policies[1] = root_poa.create_id_assignment_policy (IdAssignmentPolicyValue.SYSTEM_ID);

        policies[2] = root_poa.create_implicit_activation_policy (ImplicitActivationPolicyValue.IMPLICIT_ACTIVATION);

        policies[3] = orb.create_policy (BIDIRECTIONAL_POLICY_TYPE.value, any);

        POA bidir_poa = root_poa.create_POA ("BiDirPOA",
                                             root_poa.the_POAManager (),
                                             policies);
        bidir_poa.the_POAManager ().activate ();
        
        Customer cust = CustomerHelper.narrow (bidir_poa.servant_to_reference (customer));
        
        try {
			auctionHouse.registerCustomer(cust);
		} catch (Exception e) {
			System.out.println("Sorry unable to register! Root Cause ("+e.getMessage()+")");
			return;
		}
        
        (new Thread(customer)).start();
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

}

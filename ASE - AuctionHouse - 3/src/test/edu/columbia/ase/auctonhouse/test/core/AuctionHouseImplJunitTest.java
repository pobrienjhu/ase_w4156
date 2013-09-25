package edu.columbia.ase.auctonhouse.test.core;

import java.io.IOException;

import org.junit.Test;

import edu.columbia.ase.auctionhouse.core.app.impl.AuctionHouseImpl;
import edu.columbia.ase.auctionhouse.core.app.impl.AuctionHouseJSONAuctionCapture;
import edu.columbia.ase.auctionhouse.core.app.impl.AuctionHouseMapDataSource;
import edu.columbia.ase.auctionhouse.core.app.impl.AuctionHouseQuartzTimer;


public class AuctionHouseImplJunitTest {

	
	@Test
	public void testAuctionHouseServer() throws IOException{
				
		AuctionHouseQuartzTimer auctionHouseTimer = new AuctionHouseQuartzTimer();
		AuctionHouseMapDataSource auctionHouseDataSource = new AuctionHouseMapDataSource();
		
		AuctionHouseJSONAuctionCapture loader = new AuctionHouseJSONAuctionCapture();
		
		loader.setItemsFileName("/resources/Items.json");
		
		loader.setAuctionsFileName("/resources/Auctions.json");
		
		loader.setAuctionHouseDataSource(auctionHouseDataSource);
		loader.setAuctionHouseTimer(auctionHouseTimer);
		
		AuctionHouseImpl auctionHouse = new AuctionHouseImpl();
		
		auctionHouse.setAuctionHouseDataSource(auctionHouseDataSource);
		
		auctionHouse.startAuctionHouseServer();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
				
		AuctionHouseImplJunitTest test = new AuctionHouseImplJunitTest();
		try {
			test.testAuctionHouseServer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}

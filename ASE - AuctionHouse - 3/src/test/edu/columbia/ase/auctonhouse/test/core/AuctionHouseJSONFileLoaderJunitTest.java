package edu.columbia.ase.auctonhouse.test.core;

import org.junit.Test;

import edu.columbia.ase.auctionhouse.core.app.impl.AuctionHouseJSONAuctionCapture;
import edu.columbia.ase.auctionhouse.core.app.impl.AuctionHouseMapDataSource;
import edu.columbia.ase.auctionhouse.core.app.impl.AuctionHouseQuartzTimer;

public class AuctionHouseJSONFileLoaderJunitTest {


	@Test
	public void testFileLoader() throws Exception{
		
		AuctionHouseQuartzTimer auctionHouseTimer = new AuctionHouseQuartzTimer();
		AuctionHouseMapDataSource auctionHouseDataSource = new AuctionHouseMapDataSource();
		
		AuctionHouseJSONAuctionCapture loader = new AuctionHouseJSONAuctionCapture();
		
		loader.setItemsFileName("/resources/Items.json");
		
		loader.setAuctionsFileName("/resources/Auctions.json");
		
		loader.setAuctionHouseDataSource(auctionHouseDataSource);
		loader.setAuctionHouseTimer(auctionHouseTimer);
		
		loader.loadAuctions();
				
	}
	
}

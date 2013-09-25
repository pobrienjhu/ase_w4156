package edu.columbia.ase.auctonhouse.test.core;

import java.util.Date;

import org.junit.Test;

import edu.columbia.ase.auctionhouse.core.app.impl.AuctionHouseMapDataSource;
import edu.columbia.ase.auctionhouse.core.data.Auction;
import edu.columbia.ase.auctionhouse.core.data.AuctionItem;

public class AuctionHouseMapDataSourceJUnitTest {

	@Test
	public void testMapDataSource() throws Exception{
		
		AuctionHouseMapDataSource mapDS = new AuctionHouseMapDataSource();
		
		mapDS.addAuction(new Auction(1,new AuctionItem(1, "Test Item"),new Date(), new Date(), 1.1));
		
		Auction auction = mapDS.getAuction(new Integer(1));
		
		System.out.println("Retrieved Auction |"+auction+"|");
	}
	
}

package edu.columbia.ase.auctionhouse.ejb.app;

import javax.ejb.Remote;

import edu.columbia.ase.auctionhouse.ejb.data.AuctionBid;

@Remote 
public interface AuctionHouseRemote extends AuctionHouse {

}

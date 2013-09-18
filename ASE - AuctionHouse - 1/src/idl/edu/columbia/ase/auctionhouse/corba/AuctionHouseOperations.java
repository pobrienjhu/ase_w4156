package edu.columbia.ase.auctionhouse.corba;


/**
 * Generated from IDL interface "AuctionHouse".
 *
 * @author JacORB IDL compiler V 3.2, 07-Dec-2012
 * @version generated at Aug 19, 2013 3:16:57 PM
 */

public interface AuctionHouseOperations
{
	/* constants */
	/* operations  */
	edu.columbia.ase.auctionhouse.corba.Auction[] getActiveAuctions();
	void registerCustomer(edu.columbia.ase.auctionhouse.corba.Customer cust) throws edu.columbia.ase.auctionhouse.corba.InvalidRegistration;
	void placeBid(int custId, int auctionId, double bidAmount) throws edu.columbia.ase.auctionhouse.corba.InvalidBid;
}

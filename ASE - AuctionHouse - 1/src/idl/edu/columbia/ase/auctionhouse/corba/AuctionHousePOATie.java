package edu.columbia.ase.auctionhouse.corba;

import org.omg.PortableServer.POA;

/**
 * Generated from IDL interface "AuctionHouse".
 *
 * @author JacORB IDL compiler V 3.2, 07-Dec-2012
 * @version generated at Aug 19, 2013 3:16:57 PM
 */

public class AuctionHousePOATie
	extends AuctionHousePOA
{
	private AuctionHouseOperations _delegate;

	private POA _poa;
	public AuctionHousePOATie(AuctionHouseOperations delegate)
	{
		_delegate = delegate;
	}
	public AuctionHousePOATie(AuctionHouseOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public edu.columbia.ase.auctionhouse.corba.AuctionHouse _this()
	{
		org.omg.CORBA.Object __o = _this_object() ;
		edu.columbia.ase.auctionhouse.corba.AuctionHouse __r = edu.columbia.ase.auctionhouse.corba.AuctionHouseHelper.narrow(__o);
		return __r;
	}
	public edu.columbia.ase.auctionhouse.corba.AuctionHouse _this(org.omg.CORBA.ORB orb)
	{
		org.omg.CORBA.Object __o = _this_object(orb) ;
		edu.columbia.ase.auctionhouse.corba.AuctionHouse __r = edu.columbia.ase.auctionhouse.corba.AuctionHouseHelper.narrow(__o);
		return __r;
	}
	public AuctionHouseOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(AuctionHouseOperations delegate)
	{
		_delegate = delegate;
	}
	public POA _default_POA()
	{
		if (_poa != null)
		{
			return _poa;
		}
		return super._default_POA();
	}
	public edu.columbia.ase.auctionhouse.corba.Auction[] getActiveAuctions()
	{
		return _delegate.getActiveAuctions();
	}

	public void registerCustomer(edu.columbia.ase.auctionhouse.corba.Customer cust) throws edu.columbia.ase.auctionhouse.corba.InvalidRegistration
	{
_delegate.registerCustomer(cust);
	}

	public void placeBid(int custId, int auctionId, double bidAmount) throws edu.columbia.ase.auctionhouse.corba.InvalidBid
	{
_delegate.placeBid(custId,auctionId,bidAmount);
	}

}

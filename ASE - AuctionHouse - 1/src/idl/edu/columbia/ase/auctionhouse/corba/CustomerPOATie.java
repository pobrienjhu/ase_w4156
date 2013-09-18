package edu.columbia.ase.auctionhouse.corba;

import org.omg.PortableServer.POA;

/**
 * Generated from IDL interface "Customer".
 *
 * @author JacORB IDL compiler V 3.2, 07-Dec-2012
 * @version generated at Aug 19, 2013 3:16:57 PM
 */

public class CustomerPOATie
	extends CustomerPOA
{
	private CustomerOperations _delegate;

	private POA _poa;
	public CustomerPOATie(CustomerOperations delegate)
	{
		_delegate = delegate;
	}
	public CustomerPOATie(CustomerOperations delegate, POA poa)
	{
		_delegate = delegate;
		_poa = poa;
	}
	public edu.columbia.ase.auctionhouse.corba.Customer _this()
	{
		org.omg.CORBA.Object __o = _this_object() ;
		edu.columbia.ase.auctionhouse.corba.Customer __r = edu.columbia.ase.auctionhouse.corba.CustomerHelper.narrow(__o);
		return __r;
	}
	public edu.columbia.ase.auctionhouse.corba.Customer _this(org.omg.CORBA.ORB orb)
	{
		org.omg.CORBA.Object __o = _this_object(orb) ;
		edu.columbia.ase.auctionhouse.corba.Customer __r = edu.columbia.ase.auctionhouse.corba.CustomerHelper.narrow(__o);
		return __r;
	}
	public CustomerOperations _delegate()
	{
		return _delegate;
	}
	public void _delegate(CustomerOperations delegate)
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
	public void setId(int custId)
	{
_delegate.setId(custId);
	}

	public void auctionWon(edu.columbia.ase.auctionhouse.corba.Auction auct)
	{
_delegate.auctionWon(auct);
	}

}

package edu.columbia.ase.auctionhouse.corba;


/**
 * Generated from IDL interface "AuctionHouse".
 *
 * @author JacORB IDL compiler V 3.2, 07-Dec-2012
 * @version generated at Aug 19, 2013 3:16:57 PM
 */

public abstract class AuctionHousePOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, edu.columbia.ase.auctionhouse.corba.AuctionHouseOperations
{
	static private final java.util.HashMap<String,Integer> m_opsHash = new java.util.HashMap<String,Integer>();
	static
	{
		m_opsHash.put ( "getActiveAuctions", Integer.valueOf(0));
		m_opsHash.put ( "registerCustomer", Integer.valueOf(1));
		m_opsHash.put ( "placeBid", Integer.valueOf(2));
	}
	private String[] ids = {"IDL:edu/columbia/ase/auctionhouse/corba/AuctionHouse:1.0"};
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
	public org.omg.CORBA.portable.OutputStream _invoke(String method, org.omg.CORBA.portable.InputStream _input, org.omg.CORBA.portable.ResponseHandler handler)
		throws org.omg.CORBA.SystemException
	{
		org.omg.CORBA.portable.OutputStream _out = null;
		// do something
		// quick lookup of operation
		java.lang.Integer opsIndex = (java.lang.Integer)m_opsHash.get ( method );
		if ( null == opsIndex )
			throw new org.omg.CORBA.BAD_OPERATION(method + " not found");
		switch ( opsIndex.intValue() )
		{
			case 0: // getActiveAuctions
			{
				_out = handler.createReply();
				edu.columbia.ase.auctionhouse.corba.activeAuctionSeqHelper.write(_out,getActiveAuctions());
				break;
			}
			case 1: // registerCustomer
			{
			try
			{
				edu.columbia.ase.auctionhouse.corba.Customer _arg0=edu.columbia.ase.auctionhouse.corba.CustomerHelper.read(_input);
				_out = handler.createReply();
				registerCustomer(_arg0);
			}
			catch(edu.columbia.ase.auctionhouse.corba.InvalidRegistration _ex0)
			{
				_out = handler.createExceptionReply();
				edu.columbia.ase.auctionhouse.corba.InvalidRegistrationHelper.write(_out, _ex0);
			}
				break;
			}
			case 2: // placeBid
			{
			try
			{
				int _arg0=_input.read_long();
				int _arg1=_input.read_long();
				double _arg2=_input.read_double();
				_out = handler.createReply();
				placeBid(_arg0,_arg1,_arg2);
			}
			catch(edu.columbia.ase.auctionhouse.corba.InvalidBid _ex0)
			{
				_out = handler.createExceptionReply();
				edu.columbia.ase.auctionhouse.corba.InvalidBidHelper.write(_out, _ex0);
			}
				break;
			}
		}
		return _out;
	}

	public String[] _all_interfaces(org.omg.PortableServer.POA poa, byte[] obj_id)
	{
		return ids;
	}
}

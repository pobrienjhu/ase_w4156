package edu.columbia.ase.auctionhouse.corba;


/**
 * Generated from IDL interface "Customer".
 *
 * @author JacORB IDL compiler V 3.2, 07-Dec-2012
 * @version generated at Aug 19, 2013 3:16:57 PM
 */

public abstract class CustomerPOA
	extends org.omg.PortableServer.Servant
	implements org.omg.CORBA.portable.InvokeHandler, edu.columbia.ase.auctionhouse.corba.CustomerOperations
{
	static private final java.util.HashMap<String,Integer> m_opsHash = new java.util.HashMap<String,Integer>();
	static
	{
		m_opsHash.put ( "setId", Integer.valueOf(0));
		m_opsHash.put ( "auctionWon", Integer.valueOf(1));
	}
	private String[] ids = {"IDL:edu/columbia/ase/auctionhouse/corba/Customer:1.0"};
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
			case 0: // setId
			{
				int _arg0=_input.read_long();
				_out = handler.createReply();
				setId(_arg0);
				break;
			}
			case 1: // auctionWon
			{
				edu.columbia.ase.auctionhouse.corba.Auction _arg0=(edu.columbia.ase.auctionhouse.corba.Auction)((org.omg.CORBA_2_3.portable.InputStream)_input).read_value ("IDL:edu/columbia/ase/auctionhouse/corba/Auction:1.0");
				_out = handler.createReply();
				auctionWon(_arg0);
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

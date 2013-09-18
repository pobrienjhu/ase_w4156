package edu.columbia.ase.auctionhouse.corba;

/**
 * Generated from IDL interface "AuctionHouse".
 *
 * @author JacORB IDL compiler V 3.2, 07-Dec-2012
 * @version generated at Aug 19, 2013 3:16:57 PM
 */

public final class AuctionHouseHolder	implements org.omg.CORBA.portable.Streamable{
	 public AuctionHouse value;
	public AuctionHouseHolder()
	{
	}
	public AuctionHouseHolder (final AuctionHouse initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return AuctionHouseHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = AuctionHouseHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		AuctionHouseHelper.write (_out,value);
	}
}

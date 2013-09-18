package edu.columbia.ase.auctionhouse.corba;

/**
 * Generated from IDL exception "InvalidBid".
 *
 * @author JacORB IDL compiler V 3.2, 07-Dec-2012
 * @version generated at Aug 19, 2013 3:16:57 PM
 */

public final class InvalidBidHolder
	implements org.omg.CORBA.portable.Streamable
{
	public edu.columbia.ase.auctionhouse.corba.InvalidBid value;

	public InvalidBidHolder ()
	{
	}
	public InvalidBidHolder(final edu.columbia.ase.auctionhouse.corba.InvalidBid initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return edu.columbia.ase.auctionhouse.corba.InvalidBidHelper.type ();
	}
	public void _read(final org.omg.CORBA.portable.InputStream _in)
	{
		value = edu.columbia.ase.auctionhouse.corba.InvalidBidHelper.read(_in);
	}
	public void _write(final org.omg.CORBA.portable.OutputStream _out)
	{
		edu.columbia.ase.auctionhouse.corba.InvalidBidHelper.write(_out, value);
	}
}

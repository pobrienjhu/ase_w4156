package edu.columbia.ase.auctionhouse.corba;

/**
 * Generated from IDL valuetype "Auction".
 *
 * @author JacORB IDL compiler V 3.2, 07-Dec-2012
 * @version generated at Aug 19, 2013 3:16:57 PM
 */

public final class AuctionHolder
	implements org.omg.CORBA.portable.Streamable
{
	public edu.columbia.ase.auctionhouse.corba.Auction value;
	public AuctionHolder () {}
	public AuctionHolder (final edu.columbia.ase.auctionhouse.corba.Auction initial)
	{
		value = initial;
	}
	public void _read (final org.omg.CORBA.portable.InputStream is)
	{
		value = edu.columbia.ase.auctionhouse.corba.AuctionHelper.read (is);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream os)
	{
		edu.columbia.ase.auctionhouse.corba.AuctionHelper.write (os, value);
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return value._type ();
	}
}

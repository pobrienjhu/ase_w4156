package edu.columbia.ase.auctionhouse.corba;

/**
 * Generated from IDL alias "activeAuctionSeq".
 *
 * @author JacORB IDL compiler V 3.2, 07-Dec-2012
 * @version generated at Aug 19, 2013 3:16:57 PM
 */

public final class activeAuctionSeqHolder
	implements org.omg.CORBA.portable.Streamable
{
	public edu.columbia.ase.auctionhouse.corba.Auction[] value;

	public activeAuctionSeqHolder ()
	{
	}
	public activeAuctionSeqHolder (final edu.columbia.ase.auctionhouse.corba.Auction[] initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type ()
	{
		return activeAuctionSeqHelper.type ();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = activeAuctionSeqHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream out)
	{
		activeAuctionSeqHelper.write (out,value);
	}
}

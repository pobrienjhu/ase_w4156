package edu.columbia.ase.auctionhouse.corba;

/**
 * Generated from IDL valuetype "Auction".
 *
 * @author JacORB IDL compiler V 3.2, 07-Dec-2012
 * @version generated at Aug 19, 2013 3:16:57 PM
 */

public abstract class Auction
	implements org.omg.CORBA.portable.StreamableValue
{
	/** Serial version UID. */
	private static final long serialVersionUID = 1L;
	private String[] _truncatable_ids = {"IDL:edu/columbia/ase/auctionhouse/corba/Auction:1.0"};
	public int auctionId;
	public double currentPrice;
	public java.lang.String itemDescription = "";
	public java.lang.String itemCondition = "";
	public void _write (org.omg.CORBA.portable.OutputStream os)
	{
		os.write_long(auctionId);
		os.write_double(currentPrice);
		java.lang.String tmpResult0 = itemDescription;
os.write_string( tmpResult0 );
		java.lang.String tmpResult1 = itemCondition;
os.write_string( tmpResult1 );
	}

	public void _read (final org.omg.CORBA.portable.InputStream os)
	{
		auctionId=os.read_long();
		currentPrice=os.read_double();
		itemDescription=os.read_string();
		itemCondition=os.read_string();
	}

	public String[] _truncatable_ids()
	{
		return _truncatable_ids;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return edu.columbia.ase.auctionhouse.corba.AuctionHelper.type();
	}
}

package edu.columbia.ase.auctionhouse.corba;

/**
 * Generated from IDL valuetype "Auction".
 *
 * @author JacORB IDL compiler V 3.2, 07-Dec-2012
 * @version generated at Aug 19, 2013 3:16:57 PM
 */

public abstract class AuctionHelper
{
	private volatile static org.omg.CORBA.TypeCode _type = null;
	public static void insert (org.omg.CORBA.Any a, edu.columbia.ase.auctionhouse.corba.Auction v)
	{
		a.insert_Value (v, v._type());
	}
	public static edu.columbia.ase.auctionhouse.corba.Auction extract (org.omg.CORBA.Any a)
	{
		return (edu.columbia.ase.auctionhouse.corba.Auction)a.extract_Value();
	}
	public static org.omg.CORBA.TypeCode type()
	{
		if (_type == null)
		{
			synchronized(AuctionHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_value_tc ("IDL:edu/columbia/ase/auctionhouse/corba/Auction:1.0", "Auction", (short)0, null, new org.omg.CORBA.ValueMember[] {new org.omg.CORBA.ValueMember ("auctionId", "IDL:*primitive*:1.0", "Auction", "1.0", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null, (short)1), new org.omg.CORBA.ValueMember ("currentPrice", "IDL:*primitive*:1.0", "Auction", "1.0", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(7)), null, (short)1), new org.omg.CORBA.ValueMember ("itemDescription", "IDL:itemDescription:1.0", "Auction", "1.0", org.omg.CORBA.ORB.init().create_string_tc(0), null, (short)1), new org.omg.CORBA.ValueMember ("itemCondition", "IDL:itemCondition:1.0", "Auction", "1.0", org.omg.CORBA.ORB.init().create_string_tc(0), null, (short)1)});
				}
			}
		}
		return _type;
	}

	public static String id()
	{
		return "IDL:edu/columbia/ase/auctionhouse/corba/Auction:1.0";
	}
	public static edu.columbia.ase.auctionhouse.corba.Auction read (org.omg.CORBA.portable.InputStream is)
	{
		return (edu.columbia.ase.auctionhouse.corba.Auction)((org.omg.CORBA_2_3.portable.InputStream)is).read_value ("IDL:edu/columbia/ase/auctionhouse/corba/Auction:1.0");
	}
	public static void write (org.omg.CORBA.portable.OutputStream os, edu.columbia.ase.auctionhouse.corba.Auction val)
	{
		((org.omg.CORBA_2_3.portable.OutputStream)os).write_value (val, "IDL:edu/columbia/ase/auctionhouse/corba/Auction:1.0");
	}
}

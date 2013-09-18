package edu.columbia.ase.auctionhouse.corba;


/**
 * Generated from IDL exception "InvalidBid".
 *
 * @author JacORB IDL compiler V 3.2, 07-Dec-2012
 * @version generated at Aug 19, 2013 3:16:57 PM
 */

public abstract class InvalidBidHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(InvalidBidHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_exception_tc(edu.columbia.ase.auctionhouse.corba.InvalidBidHelper.id(),"InvalidBid",new org.omg.CORBA.StructMember[0]);
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final edu.columbia.ase.auctionhouse.corba.InvalidBid s)
	{
		any.type(type());
		write( any.create_output_stream(),s);
	}

	public static edu.columbia.ase.auctionhouse.corba.InvalidBid extract (final org.omg.CORBA.Any any)
	{
		org.omg.CORBA.portable.InputStream in = any.create_input_stream();
		try
		{
			return read (in);
		}
		finally
		{
			try
			{
				in.close();
			}
			catch (java.io.IOException e)
			{
			throw new RuntimeException("Unexpected exception " + e.toString() );
			}
		}
	}

	public static String id()
	{
		return "IDL:edu/columbia/ase/auctionhouse/corba/InvalidBid:1.0";
	}
	public static edu.columbia.ase.auctionhouse.corba.InvalidBid read (final org.omg.CORBA.portable.InputStream in)
	{
		String id = in.read_string();
		if (!id.equals(id())) throw new org.omg.CORBA.MARSHAL("wrong id: " + id);
		final edu.columbia.ase.auctionhouse.corba.InvalidBid result = new edu.columbia.ase.auctionhouse.corba.InvalidBid(id);
		return result;
	}
	public static void write (final org.omg.CORBA.portable.OutputStream out, final edu.columbia.ase.auctionhouse.corba.InvalidBid s)
	{
		out.write_string(id());
	}
}

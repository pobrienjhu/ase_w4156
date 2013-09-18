package edu.columbia.ase.auctionhouse.corba;

/**
 * Generated from IDL alias "activeAuctionSeq".
 *
 * @author JacORB IDL compiler V 3.2, 07-Dec-2012
 * @version generated at Aug 19, 2013 3:16:57 PM
 */

public abstract class activeAuctionSeqHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;

	public static void insert (org.omg.CORBA.Any any, edu.columbia.ase.auctionhouse.corba.Auction[] s)
	{
		any.type (type ());
		write (any.create_output_stream (), s);
	}

	public static edu.columbia.ase.auctionhouse.corba.Auction[] extract (final org.omg.CORBA.Any any)
	{
		if ( any.type().kind() == org.omg.CORBA.TCKind.tk_null)
		{
			throw new org.omg.CORBA.BAD_OPERATION ("Can't extract from Any with null type.");
		}
		return read (any.create_input_stream ());
	}

	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(activeAuctionSeqHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_alias_tc(edu.columbia.ase.auctionhouse.corba.activeAuctionSeqHelper.id(), "activeAuctionSeq",org.omg.CORBA.ORB.init().create_sequence_tc(0, org.omg.CORBA.ORB.init().create_value_tc ("IDL:edu/columbia/ase/auctionhouse/corba/Auction:1.0", "Auction", (short)0, null, new org.omg.CORBA.ValueMember[] {new org.omg.CORBA.ValueMember ("auctionId", "IDL:*primitive*:1.0", "Auction", "1.0", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(3)), null, (short)1), new org.omg.CORBA.ValueMember ("currentPrice", "IDL:*primitive*:1.0", "Auction", "1.0", org.omg.CORBA.ORB.init().get_primitive_tc(org.omg.CORBA.TCKind.from_int(7)), null, (short)1), new org.omg.CORBA.ValueMember ("itemDescription", "IDL:itemDescription:1.0", "Auction", "1.0", org.omg.CORBA.ORB.init().create_string_tc(0), null, (short)1), new org.omg.CORBA.ValueMember ("itemCondition", "IDL:itemCondition:1.0", "Auction", "1.0", org.omg.CORBA.ORB.init().create_string_tc(0), null, (short)1)})));
				}
			}
		}
		return _type;
	}

	public static String id()
	{
		return "IDL:edu/columbia/ase/auctionhouse/corba/activeAuctionSeq:1.0";
	}
	public static edu.columbia.ase.auctionhouse.corba.Auction[] read (final org.omg.CORBA.portable.InputStream _in)
	{
		edu.columbia.ase.auctionhouse.corba.Auction[] _result;
		int _l_result0 = _in.read_long();
		try
		{
			 int x = _in.available();
			 if ( x > 0 && _l_result0 > x )
				{
					throw new org.omg.CORBA.MARSHAL("Sequence length too large. Only " + x + " available and trying to assign " + _l_result0);
				}
		}
		catch (java.io.IOException e)
		{
		}
		_result = new edu.columbia.ase.auctionhouse.corba.Auction[_l_result0];
		for (int i=0;i<_result.length;i++)
		{
			_result[i]=(edu.columbia.ase.auctionhouse.corba.Auction)((org.omg.CORBA_2_3.portable.InputStream)_in).read_value ("IDL:edu/columbia/ase/auctionhouse/corba/Auction:1.0");
		}

		return _result;
	}

	public static void write (final org.omg.CORBA.portable.OutputStream _out, edu.columbia.ase.auctionhouse.corba.Auction[] _s)
	{
		
		_out.write_long(_s.length);
		for (int i=0; i<_s.length;i++)
		{
			((org.omg.CORBA_2_3.portable.OutputStream)_out).write_value (_s[i], (String)null);
		}

	}
}

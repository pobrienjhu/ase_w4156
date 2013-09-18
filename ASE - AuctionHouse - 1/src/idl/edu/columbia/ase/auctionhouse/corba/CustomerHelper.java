package edu.columbia.ase.auctionhouse.corba;


/**
 * Generated from IDL interface "Customer".
 *
 * @author JacORB IDL compiler V 3.2, 07-Dec-2012
 * @version generated at Aug 19, 2013 3:16:57 PM
 */

public abstract class CustomerHelper
{
	private volatile static org.omg.CORBA.TypeCode _type;
	public static org.omg.CORBA.TypeCode type ()
	{
		if (_type == null)
		{
			synchronized(CustomerHelper.class)
			{
				if (_type == null)
				{
					_type = org.omg.CORBA.ORB.init().create_interface_tc("IDL:edu/columbia/ase/auctionhouse/corba/Customer:1.0", "Customer");
				}
			}
		}
		return _type;
	}

	public static void insert (final org.omg.CORBA.Any any, final edu.columbia.ase.auctionhouse.corba.Customer s)
	{
			any.insert_Object(s);
	}
	public static edu.columbia.ase.auctionhouse.corba.Customer extract(final org.omg.CORBA.Any any)
	{
		return narrow(any.extract_Object()) ;
	}
	public static String id()
	{
		return "IDL:edu/columbia/ase/auctionhouse/corba/Customer:1.0";
	}
	public static Customer read(final org.omg.CORBA.portable.InputStream in)
	{
		return narrow(in.read_Object(edu.columbia.ase.auctionhouse.corba._CustomerStub.class));
	}
	public static void write(final org.omg.CORBA.portable.OutputStream _out, final edu.columbia.ase.auctionhouse.corba.Customer s)
	{
		_out.write_Object(s);
	}
	public static edu.columbia.ase.auctionhouse.corba.Customer narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof edu.columbia.ase.auctionhouse.corba.Customer)
		{
			return (edu.columbia.ase.auctionhouse.corba.Customer)obj;
		}
		else if (obj._is_a("IDL:edu/columbia/ase/auctionhouse/corba/Customer:1.0"))
		{
			edu.columbia.ase.auctionhouse.corba._CustomerStub stub;
			stub = new edu.columbia.ase.auctionhouse.corba._CustomerStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
		else
		{
			throw new org.omg.CORBA.BAD_PARAM("Narrow failed");
		}
	}
	public static edu.columbia.ase.auctionhouse.corba.Customer unchecked_narrow(final org.omg.CORBA.Object obj)
	{
		if (obj == null)
		{
			return null;
		}
		else if (obj instanceof edu.columbia.ase.auctionhouse.corba.Customer)
		{
			return (edu.columbia.ase.auctionhouse.corba.Customer)obj;
		}
		else
		{
			edu.columbia.ase.auctionhouse.corba._CustomerStub stub;
			stub = new edu.columbia.ase.auctionhouse.corba._CustomerStub();
			stub._set_delegate(((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate());
			return stub;
		}
	}
}

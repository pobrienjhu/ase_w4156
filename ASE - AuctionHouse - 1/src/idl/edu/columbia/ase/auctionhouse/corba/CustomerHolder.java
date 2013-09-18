package edu.columbia.ase.auctionhouse.corba;

/**
 * Generated from IDL interface "Customer".
 *
 * @author JacORB IDL compiler V 3.2, 07-Dec-2012
 * @version generated at Aug 19, 2013 3:16:57 PM
 */

public final class CustomerHolder	implements org.omg.CORBA.portable.Streamable{
	 public Customer value;
	public CustomerHolder()
	{
	}
	public CustomerHolder (final Customer initial)
	{
		value = initial;
	}
	public org.omg.CORBA.TypeCode _type()
	{
		return CustomerHelper.type();
	}
	public void _read (final org.omg.CORBA.portable.InputStream in)
	{
		value = CustomerHelper.read (in);
	}
	public void _write (final org.omg.CORBA.portable.OutputStream _out)
	{
		CustomerHelper.write (_out,value);
	}
}

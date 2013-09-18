package edu.columbia.ase.auctionhouse.corba;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Properties;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class AuctionHouseCorbaServer {

	/**
	 * @param args 
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {

		if( args.length == 0  ){
			System.out.println("Need nameserver file");
			return;
		}
		
		/*
		 * Start the Core Auction House App first
		 */
        ApplicationContext context = 
	            new ClassPathXmlApplicationContext("config/spring-config.xml");
        
        edu.columbia.ase.auctionhouse.core.app.AuctionHouse auctionHouse = (edu.columbia.ase.auctionhouse.core.app.AuctionHouse) context.getBean("AuctionHouse");
		
        /*
         * then start to the orb
         */
        Properties props = new Properties ();
        props.put ("org.omg.PortableInterceptor.ORBInitializerClass.bidir_init",
                   "org.jacorb.orb.giop.BiDirConnectionInitializer");
        
		org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init(args, props);
        org.omg.PortableServer.POA poa =
            org.omg.PortableServer.POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
        poa.the_POAManager().activate();
        org.omg.PortableServer.Servant servant = new AuctionHouseCorbaImpl(auctionHouse);
        org.omg.CORBA.Object o = poa.servant_to_reference(servant );

        PrintWriter ps = new PrintWriter(new FileOutputStream(new File( args[0] )));
        ps.println( orb.object_to_string( o ) );
        ps.close();

        if (args.length == 2)
        {
            File killFile = new File(args[1]);
            while(!killFile.exists())
            {
                Thread.sleep(1000);
            }
            orb.shutdown(true);
        }
        else
        {
            orb.run();    
        }
    }

}

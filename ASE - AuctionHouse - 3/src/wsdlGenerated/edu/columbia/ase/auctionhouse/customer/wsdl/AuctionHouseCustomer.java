

/**
 * AuctionHouseCustomer.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package edu.columbia.ase.auctionhouse.customer.wsdl;

    /*
     *  AuctionHouseCustomer java interface
     */

    public interface AuctionHouseCustomer {
          

        /**
          * Auto generated method signature
          * 
                    * @param auctionWon0
                
         */

         
                     public edu.columbia.ase.auctionhouse.customer.AuctionWonResponseE auctionWon(

                        edu.columbia.ase.auctionhouse.customer.AuctionWonE auctionWon0)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param auctionWon0
            
          */
        public void startauctionWon(

            edu.columbia.ase.auctionhouse.customer.AuctionWonE auctionWon0,

            final edu.columbia.ase.auctionhouse.customer.wsdl.AuctionHouseCustomerCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param setId2
                
         */

         
                     public edu.columbia.ase.auctionhouse.customer.SetIdResponseE setId(

                        edu.columbia.ase.auctionhouse.customer.SetIdE setId2)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param setId2
            
          */
        public void startsetId(

            edu.columbia.ase.auctionhouse.customer.SetIdE setId2,

            final edu.columbia.ase.auctionhouse.customer.wsdl.AuctionHouseCustomerCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        
       //
       }
    
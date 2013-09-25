

/**
 * AuctionHouse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package edu.columbia.ase.auctionhouse.webservice.wsdl;

    /*
     *  AuctionHouse java interface
     */

    public interface AuctionHouse {
          

        /**
          * Auto generated method signature
          * 
                    * @param getActiveAuctions0
                
         */

         
                     public edu.columbia.ase.auctionhouse.webservice.GetActiveAuctionsResponseE getActiveAuctions(

                        edu.columbia.ase.auctionhouse.webservice.GetActiveAuctionsE getActiveAuctions0)
                        throws java.rmi.RemoteException
             ;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param getActiveAuctions0
            
          */
        public void startgetActiveAuctions(

            edu.columbia.ase.auctionhouse.webservice.GetActiveAuctionsE getActiveAuctions0,

            final edu.columbia.ase.auctionhouse.webservice.wsdl.AuctionHouseCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        /**
          * Auto generated method signature
          * 
                    * @param placeBid2
                
             * @throws edu.columbia.ase.auctionhouse.webservice.wsdl.ExceptionException : 
         */

         
                     public edu.columbia.ase.auctionhouse.webservice.PlaceBidResponseE placeBid(

                        edu.columbia.ase.auctionhouse.webservice.PlaceBidE placeBid2)
                        throws java.rmi.RemoteException
             
          ,edu.columbia.ase.auctionhouse.webservice.wsdl.ExceptionException;

        
         /**
            * Auto generated method signature for Asynchronous Invocations
            * 
                * @param placeBid2
            
          */
        public void startplaceBid(

            edu.columbia.ase.auctionhouse.webservice.PlaceBidE placeBid2,

            final edu.columbia.ase.auctionhouse.webservice.wsdl.AuctionHouseCallbackHandler callback)

            throws java.rmi.RemoteException;

     

        
       //
       }
    
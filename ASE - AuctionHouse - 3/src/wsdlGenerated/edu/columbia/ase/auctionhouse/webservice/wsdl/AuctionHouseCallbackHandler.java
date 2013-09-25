
/**
 * AuctionHouseCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package edu.columbia.ase.auctionhouse.webservice.wsdl;

    /**
     *  AuctionHouseCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class AuctionHouseCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public AuctionHouseCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public AuctionHouseCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for getActiveAuctions method
            * override this method for handling normal response from getActiveAuctions operation
            */
           public void receiveResultgetActiveAuctions(
                    edu.columbia.ase.auctionhouse.webservice.GetActiveAuctionsResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from getActiveAuctions operation
           */
            public void receiveErrorgetActiveAuctions(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for placeBid method
            * override this method for handling normal response from placeBid operation
            */
           public void receiveResultplaceBid(
                    edu.columbia.ase.auctionhouse.webservice.PlaceBidResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from placeBid operation
           */
            public void receiveErrorplaceBid(java.lang.Exception e) {
            }
                


    }
    
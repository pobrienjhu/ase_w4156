
/**
 * AuctionHouseCustomerCallbackHandler.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

    package edu.columbia.ase.auctionhouse.customer.wsdl;

    /**
     *  AuctionHouseCustomerCallbackHandler Callback class, Users can extend this class and implement
     *  their own receiveResult and receiveError methods.
     */
    public abstract class AuctionHouseCustomerCallbackHandler{



    protected Object clientData;

    /**
    * User can pass in any object that needs to be accessed once the NonBlocking
    * Web service call is finished and appropriate method of this CallBack is called.
    * @param clientData Object mechanism by which the user can pass in user data
    * that will be avilable at the time this callback is called.
    */
    public AuctionHouseCustomerCallbackHandler(Object clientData){
        this.clientData = clientData;
    }

    /**
    * Please use this constructor if you don't want to set any clientData
    */
    public AuctionHouseCustomerCallbackHandler(){
        this.clientData = null;
    }

    /**
     * Get the client data
     */

     public Object getClientData() {
        return clientData;
     }

        
           /**
            * auto generated Axis2 call back method for auctionWon method
            * override this method for handling normal response from auctionWon operation
            */
           public void receiveResultauctionWon(
                    edu.columbia.ase.auctionhouse.customer.AuctionWonResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from auctionWon operation
           */
            public void receiveErrorauctionWon(java.lang.Exception e) {
            }
                
           /**
            * auto generated Axis2 call back method for setId method
            * override this method for handling normal response from setId operation
            */
           public void receiveResultsetId(
                    edu.columbia.ase.auctionhouse.customer.SetIdResponseE result
                        ) {
           }

          /**
           * auto generated Axis2 Error handler
           * override this method for handling error response from setId operation
           */
            public void receiveErrorsetId(java.lang.Exception e) {
            }
                


    }
    
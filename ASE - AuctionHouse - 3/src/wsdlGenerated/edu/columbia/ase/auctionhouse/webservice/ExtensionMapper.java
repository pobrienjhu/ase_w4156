
/**
 * ExtensionMapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

        
            package edu.columbia.ase.auctionhouse.webservice;
        
            /**
            *  ExtensionMapper class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class ExtensionMapper{

          public static java.lang.Object getTypeObject(java.lang.String namespaceURI,
                                                       java.lang.String typeName,
                                                       javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{

              
                  if (
                  "http://webservice.auctionhouse.ase.columbia.edu/".equals(namespaceURI) &&
                  "getActiveAuctionsResponse".equals(typeName)){
                   
                            return  edu.columbia.ase.auctionhouse.webservice.GetActiveAuctionsResponse.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://webservice.auctionhouse.ase.columbia.edu/".equals(namespaceURI) &&
                  "placeBid".equals(typeName)){
                   
                            return  edu.columbia.ase.auctionhouse.webservice.PlaceBid.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://webservice.auctionhouse.ase.columbia.edu/".equals(namespaceURI) &&
                  "auctionItem".equals(typeName)){
                   
                            return  edu.columbia.ase.auctionhouse.webservice.AuctionItem.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://webservice.auctionhouse.ase.columbia.edu/".equals(namespaceURI) &&
                  "Exception".equals(typeName)){
                   
                            return  edu.columbia.ase.auctionhouse.webservice.Exception.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://webservice.auctionhouse.ase.columbia.edu/".equals(namespaceURI) &&
                  "auction".equals(typeName)){
                   
                            return  edu.columbia.ase.auctionhouse.webservice.Auction.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://webservice.auctionhouse.ase.columbia.edu/".equals(namespaceURI) &&
                  "getActiveAuctions".equals(typeName)){
                   
                            return  edu.columbia.ase.auctionhouse.webservice.GetActiveAuctions.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://webservice.auctionhouse.ase.columbia.edu/".equals(namespaceURI) &&
                  "placeBidResponse".equals(typeName)){
                   
                            return  edu.columbia.ase.auctionhouse.webservice.PlaceBidResponse.Factory.parse(reader);
                        

                  }

              
             throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
          }

        }
    
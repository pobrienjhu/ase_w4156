
/**
 * ExtensionMapper.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

        
            package edu.columbia.ase.auctionhouse.customer;
        
            /**
            *  ExtensionMapper class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class ExtensionMapper{

          public static java.lang.Object getTypeObject(java.lang.String namespaceURI,
                                                       java.lang.String typeName,
                                                       javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{

              
                  if (
                  "http://customer.auctionhouse.ase.columbia.edu/".equals(namespaceURI) &&
                  "auctionWon".equals(typeName)){
                   
                            return  edu.columbia.ase.auctionhouse.customer.AuctionWon.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://customer.auctionhouse.ase.columbia.edu/".equals(namespaceURI) &&
                  "setId".equals(typeName)){
                   
                            return  edu.columbia.ase.auctionhouse.customer.SetId.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://customer.auctionhouse.ase.columbia.edu/".equals(namespaceURI) &&
                  "auctionWonResponse".equals(typeName)){
                   
                            return  edu.columbia.ase.auctionhouse.customer.AuctionWonResponse.Factory.parse(reader);
                        

                  }

              
                  if (
                  "http://customer.auctionhouse.ase.columbia.edu/".equals(namespaceURI) &&
                  "setIdResponse".equals(typeName)){
                   
                            return  edu.columbia.ase.auctionhouse.customer.SetIdResponse.Factory.parse(reader);
                        

                  }

              
             throw new org.apache.axis2.databinding.ADBException("Unsupported type " + namespaceURI + " " + typeName);
          }

        }
    
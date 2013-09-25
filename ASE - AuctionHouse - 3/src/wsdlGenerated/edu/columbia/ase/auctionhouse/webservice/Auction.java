
/**
 * Auction.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:34:40 IST)
 */

            
                package edu.columbia.ase.auctionhouse.webservice;
            

            /**
            *  Auction bean class
            */
            @SuppressWarnings({"unchecked","unused"})
        
        public  class Auction
        implements org.apache.axis2.databinding.ADBBean{
        /* This type was generated from the piece of schema that had
                name = auction
                Namespace URI = http://webservice.auctionhouse.ase.columbia.edu/
                Namespace Prefix = ns1
                */
            

                        /**
                        * field for AuctionItemId
                        */

                        
                                    protected int localAuctionItemId ;
                                

                           /**
                           * Auto generated getter method
                           * @return int
                           */
                           public  int getAuctionItemId(){
                               return localAuctionItemId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AuctionItemId
                               */
                               public void setAuctionItemId(int param){
                            
                                            this.localAuctionItemId=param;
                                    

                               }
                            

                        /**
                        * field for AuctionLengthInMinutes
                        */

                        
                                    protected int localAuctionLengthInMinutes ;
                                

                           /**
                           * Auto generated getter method
                           * @return int
                           */
                           public  int getAuctionLengthInMinutes(){
                               return localAuctionLengthInMinutes;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AuctionLengthInMinutes
                               */
                               public void setAuctionLengthInMinutes(int param){
                            
                                            this.localAuctionLengthInMinutes=param;
                                    

                               }
                            

                        /**
                        * field for Item
                        */

                        
                                    protected edu.columbia.ase.auctionhouse.webservice.AuctionItem localItem ;
                                
                           /*  This tracker boolean wil be used to detect whether the user called the set method
                          *   for this attribute. It will be used to determine whether to include this field
                           *   in the serialized XML
                           */
                           protected boolean localItemTracker = false ;

                           public boolean isItemSpecified(){
                               return localItemTracker;
                           }

                           

                           /**
                           * Auto generated getter method
                           * @return edu.columbia.ase.auctionhouse.webservice.AuctionItem
                           */
                           public  edu.columbia.ase.auctionhouse.webservice.AuctionItem getItem(){
                               return localItem;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param Item
                               */
                               public void setItem(edu.columbia.ase.auctionhouse.webservice.AuctionItem param){
                            localItemTracker = param != null;
                                   
                                            this.localItem=param;
                                    

                               }
                            

                        /**
                        * field for StartOffsetMinutes
                        */

                        
                                    protected int localStartOffsetMinutes ;
                                

                           /**
                           * Auto generated getter method
                           * @return int
                           */
                           public  int getStartOffsetMinutes(){
                               return localStartOffsetMinutes;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param StartOffsetMinutes
                               */
                               public void setStartOffsetMinutes(int param){
                            
                                            this.localStartOffsetMinutes=param;
                                    

                               }
                            

                        /**
                        * field for StartPrice
                        */

                        
                                    protected double localStartPrice ;
                                

                           /**
                           * Auto generated getter method
                           * @return double
                           */
                           public  double getStartPrice(){
                               return localStartPrice;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param StartPrice
                               */
                               public void setStartPrice(double param){
                            
                                            this.localStartPrice=param;
                                    

                               }
                            

                        /**
                        * field for AuctionId
                        * This was an Attribute!
                        */

                        
                                    protected int localAuctionId ;
                                

                           /**
                           * Auto generated getter method
                           * @return int
                           */
                           public  int getAuctionId(){
                               return localAuctionId;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param AuctionId
                               */
                               public void setAuctionId(int param){
                            
                                            this.localAuctionId=param;
                                    

                               }
                            

                        /**
                        * field for CurrentPrice
                        * This was an Attribute!
                        */

                        
                                    protected double localCurrentPrice ;
                                

                           /**
                           * Auto generated getter method
                           * @return double
                           */
                           public  double getCurrentPrice(){
                               return localCurrentPrice;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param CurrentPrice
                               */
                               public void setCurrentPrice(double param){
                            
                                            this.localCurrentPrice=param;
                                    

                               }
                            

                        /**
                        * field for EndTime
                        * This was an Attribute!
                        */

                        
                                    protected java.util.Calendar localEndTime ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.util.Calendar
                           */
                           public  java.util.Calendar getEndTime(){
                               return localEndTime;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param EndTime
                               */
                               public void setEndTime(java.util.Calendar param){
                            
                                            this.localEndTime=param;
                                    

                               }
                            

                        /**
                        * field for ItemCondition
                        * This was an Attribute!
                        */

                        
                                    protected java.lang.String localItemCondition ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.lang.String
                           */
                           public  java.lang.String getItemCondition(){
                               return localItemCondition;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param ItemCondition
                               */
                               public void setItemCondition(java.lang.String param){
                            
                                            this.localItemCondition=param;
                                    

                               }
                            

                        /**
                        * field for StartTime
                        * This was an Attribute!
                        */

                        
                                    protected java.util.Calendar localStartTime ;
                                

                           /**
                           * Auto generated getter method
                           * @return java.util.Calendar
                           */
                           public  java.util.Calendar getStartTime(){
                               return localStartTime;
                           }

                           
                        
                            /**
                               * Auto generated setter method
                               * @param param StartTime
                               */
                               public void setStartTime(java.util.Calendar param){
                            
                                            this.localStartTime=param;
                                    

                               }
                            

     
     
        /**
        *
        * @param parentQName
        * @param factory
        * @return org.apache.axiom.om.OMElement
        */
       public org.apache.axiom.om.OMElement getOMElement (
               final javax.xml.namespace.QName parentQName,
               final org.apache.axiom.om.OMFactory factory) throws org.apache.axis2.databinding.ADBException{


        
               org.apache.axiom.om.OMDataSource dataSource =
                       new org.apache.axis2.databinding.ADBDataSource(this,parentQName);
               return factory.createOMElement(dataSource,parentQName);
            
        }

         public void serialize(final javax.xml.namespace.QName parentQName,
                                       javax.xml.stream.XMLStreamWriter xmlWriter)
                                throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
                           serialize(parentQName,xmlWriter,false);
         }

         public void serialize(final javax.xml.namespace.QName parentQName,
                               javax.xml.stream.XMLStreamWriter xmlWriter,
                               boolean serializeType)
            throws javax.xml.stream.XMLStreamException, org.apache.axis2.databinding.ADBException{
            
                


                java.lang.String prefix = null;
                java.lang.String namespace = null;
                

                    prefix = parentQName.getPrefix();
                    namespace = parentQName.getNamespaceURI();
                    writeStartElement(prefix, namespace, parentQName.getLocalPart(), xmlWriter);
                
                  if (serializeType){
               

                   java.lang.String namespacePrefix = registerPrefix(xmlWriter,"http://webservice.auctionhouse.ase.columbia.edu/");
                   if ((namespacePrefix != null) && (namespacePrefix.trim().length() > 0)){
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           namespacePrefix+":auction",
                           xmlWriter);
                   } else {
                       writeAttribute("xsi","http://www.w3.org/2001/XMLSchema-instance","type",
                           "auction",
                           xmlWriter);
                   }

               
                   }
               
                                                   if (localAuctionId!=java.lang.Integer.MIN_VALUE) {
                                               
                                                writeAttribute("",
                                                         "auctionId",
                                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAuctionId), xmlWriter);

                                            
                                      }
                                    
                                      else {
                                          throw new org.apache.axis2.databinding.ADBException("required attribute localAuctionId is null");
                                      }
                                    
                                                   if (!java.lang.Double.isNaN(localCurrentPrice)) {
                                               
                                                writeAttribute("",
                                                         "currentPrice",
                                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCurrentPrice), xmlWriter);

                                            
                                      }
                                    
                                      else {
                                          throw new org.apache.axis2.databinding.ADBException("required attribute localCurrentPrice is null");
                                      }
                                    
                                            if (localEndTime != null){
                                        
                                                writeAttribute("",
                                                         "endTime",
                                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEndTime), xmlWriter);

                                            
                                      }
                                    
                                            if (localItemCondition != null){
                                        
                                                writeAttribute("",
                                                         "itemCondition",
                                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localItemCondition), xmlWriter);

                                            
                                      }
                                    
                                            if (localStartTime != null){
                                        
                                                writeAttribute("",
                                                         "startTime",
                                                         org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localStartTime), xmlWriter);

                                            
                                      }
                                    
                                    namespace = "";
                                    writeStartElement(null, namespace, "auctionItemId", xmlWriter);
                             
                                               if (localAuctionItemId==java.lang.Integer.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("auctionItemId cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAuctionItemId));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "";
                                    writeStartElement(null, namespace, "auctionLengthInMinutes", xmlWriter);
                             
                                               if (localAuctionLengthInMinutes==java.lang.Integer.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("auctionLengthInMinutes cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAuctionLengthInMinutes));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                              if (localItemTracker){
                                            if (localItem==null){
                                                 throw new org.apache.axis2.databinding.ADBException("item cannot be null!!");
                                            }
                                           localItem.serialize(new javax.xml.namespace.QName("","item"),
                                               xmlWriter);
                                        }
                                    namespace = "";
                                    writeStartElement(null, namespace, "startOffsetMinutes", xmlWriter);
                             
                                               if (localStartOffsetMinutes==java.lang.Integer.MIN_VALUE) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("startOffsetMinutes cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localStartOffsetMinutes));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             
                                    namespace = "";
                                    writeStartElement(null, namespace, "startPrice", xmlWriter);
                             
                                               if (java.lang.Double.isNaN(localStartPrice)) {
                                           
                                                         throw new org.apache.axis2.databinding.ADBException("startPrice cannot be null!!");
                                                      
                                               } else {
                                                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localStartPrice));
                                               }
                                    
                                   xmlWriter.writeEndElement();
                             
                    xmlWriter.writeEndElement();
               

        }

        private static java.lang.String generatePrefix(java.lang.String namespace) {
            if(namespace.equals("http://webservice.auctionhouse.ase.columbia.edu/")){
                return "ns1";
            }
            return org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
        }

        /**
         * Utility method to write an element start tag.
         */
        private void writeStartElement(java.lang.String prefix, java.lang.String namespace, java.lang.String localPart,
                                       javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String writerPrefix = xmlWriter.getPrefix(namespace);
            if (writerPrefix != null) {
                xmlWriter.writeStartElement(namespace, localPart);
            } else {
                if (namespace.length() == 0) {
                    prefix = "";
                } else if (prefix == null) {
                    prefix = generatePrefix(namespace);
                }

                xmlWriter.writeStartElement(prefix, localPart, namespace);
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
        }
        
        /**
         * Util method to write an attribute with the ns prefix
         */
        private void writeAttribute(java.lang.String prefix,java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (xmlWriter.getPrefix(namespace) == null) {
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            xmlWriter.writeAttribute(namespace,attName,attValue);
        }

        /**
         * Util method to write an attribute without the ns prefix
         */
        private void writeAttribute(java.lang.String namespace,java.lang.String attName,
                                    java.lang.String attValue,javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException{
            if (namespace.equals("")) {
                xmlWriter.writeAttribute(attName,attValue);
            } else {
                registerPrefix(xmlWriter, namespace);
                xmlWriter.writeAttribute(namespace,attName,attValue);
            }
        }


           /**
             * Util method to write an attribute without the ns prefix
             */
            private void writeQNameAttribute(java.lang.String namespace, java.lang.String attName,
                                             javax.xml.namespace.QName qname, javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

                java.lang.String attributeNamespace = qname.getNamespaceURI();
                java.lang.String attributePrefix = xmlWriter.getPrefix(attributeNamespace);
                if (attributePrefix == null) {
                    attributePrefix = registerPrefix(xmlWriter, attributeNamespace);
                }
                java.lang.String attributeValue;
                if (attributePrefix.trim().length() > 0) {
                    attributeValue = attributePrefix + ":" + qname.getLocalPart();
                } else {
                    attributeValue = qname.getLocalPart();
                }

                if (namespace.equals("")) {
                    xmlWriter.writeAttribute(attName, attributeValue);
                } else {
                    registerPrefix(xmlWriter, namespace);
                    xmlWriter.writeAttribute(namespace, attName, attributeValue);
                }
            }
        /**
         *  method to handle Qnames
         */

        private void writeQName(javax.xml.namespace.QName qname,
                                javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {
            java.lang.String namespaceURI = qname.getNamespaceURI();
            if (namespaceURI != null) {
                java.lang.String prefix = xmlWriter.getPrefix(namespaceURI);
                if (prefix == null) {
                    prefix = generatePrefix(namespaceURI);
                    xmlWriter.writeNamespace(prefix, namespaceURI);
                    xmlWriter.setPrefix(prefix,namespaceURI);
                }

                if (prefix.trim().length() > 0){
                    xmlWriter.writeCharacters(prefix + ":" + org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                } else {
                    // i.e this is the default namespace
                    xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
                }

            } else {
                xmlWriter.writeCharacters(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qname));
            }
        }

        private void writeQNames(javax.xml.namespace.QName[] qnames,
                                 javax.xml.stream.XMLStreamWriter xmlWriter) throws javax.xml.stream.XMLStreamException {

            if (qnames != null) {
                // we have to store this data until last moment since it is not possible to write any
                // namespace data after writing the charactor data
                java.lang.StringBuffer stringToWrite = new java.lang.StringBuffer();
                java.lang.String namespaceURI = null;
                java.lang.String prefix = null;

                for (int i = 0; i < qnames.length; i++) {
                    if (i > 0) {
                        stringToWrite.append(" ");
                    }
                    namespaceURI = qnames[i].getNamespaceURI();
                    if (namespaceURI != null) {
                        prefix = xmlWriter.getPrefix(namespaceURI);
                        if ((prefix == null) || (prefix.length() == 0)) {
                            prefix = generatePrefix(namespaceURI);
                            xmlWriter.writeNamespace(prefix, namespaceURI);
                            xmlWriter.setPrefix(prefix,namespaceURI);
                        }

                        if (prefix.trim().length() > 0){
                            stringToWrite.append(prefix).append(":").append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        } else {
                            stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                        }
                    } else {
                        stringToWrite.append(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(qnames[i]));
                    }
                }
                xmlWriter.writeCharacters(stringToWrite.toString());
            }

        }


        /**
         * Register a namespace prefix
         */
        private java.lang.String registerPrefix(javax.xml.stream.XMLStreamWriter xmlWriter, java.lang.String namespace) throws javax.xml.stream.XMLStreamException {
            java.lang.String prefix = xmlWriter.getPrefix(namespace);
            if (prefix == null) {
                prefix = generatePrefix(namespace);
                javax.xml.namespace.NamespaceContext nsContext = xmlWriter.getNamespaceContext();
                while (true) {
                    java.lang.String uri = nsContext.getNamespaceURI(prefix);
                    if (uri == null || uri.length() == 0) {
                        break;
                    }
                    prefix = org.apache.axis2.databinding.utils.BeanUtil.getUniquePrefix();
                }
                xmlWriter.writeNamespace(prefix, namespace);
                xmlWriter.setPrefix(prefix, namespace);
            }
            return prefix;
        }


  
        /**
        * databinding method to get an XML representation of this object
        *
        */
        public javax.xml.stream.XMLStreamReader getPullParser(javax.xml.namespace.QName qName)
                    throws org.apache.axis2.databinding.ADBException{


        
                 java.util.ArrayList elementList = new java.util.ArrayList();
                 java.util.ArrayList attribList = new java.util.ArrayList();

                
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "auctionItemId"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAuctionItemId));
                            
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "auctionLengthInMinutes"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAuctionLengthInMinutes));
                             if (localItemTracker){
                            elementList.add(new javax.xml.namespace.QName("",
                                                                      "item"));
                            
                            
                                    if (localItem==null){
                                         throw new org.apache.axis2.databinding.ADBException("item cannot be null!!");
                                    }
                                    elementList.add(localItem);
                                }
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "startOffsetMinutes"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localStartOffsetMinutes));
                            
                                      elementList.add(new javax.xml.namespace.QName("",
                                                                      "startPrice"));
                                 
                                elementList.add(
                                   org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localStartPrice));
                            
                            attribList.add(
                            new javax.xml.namespace.QName("","auctionId"));
                            
                                      attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localAuctionId));
                                
                            attribList.add(
                            new javax.xml.namespace.QName("","currentPrice"));
                            
                                      attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localCurrentPrice));
                                
                            attribList.add(
                            new javax.xml.namespace.QName("","endTime"));
                            
                                      attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localEndTime));
                                
                            attribList.add(
                            new javax.xml.namespace.QName("","itemCondition"));
                            
                                      attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localItemCondition));
                                
                            attribList.add(
                            new javax.xml.namespace.QName("","startTime"));
                            
                                      attribList.add(org.apache.axis2.databinding.utils.ConverterUtil.convertToString(localStartTime));
                                

                return new org.apache.axis2.databinding.utils.reader.ADBXMLStreamReaderImpl(qName, elementList.toArray(), attribList.toArray());
            
            

        }

  

     /**
      *  Factory class that keeps the parse method
      */
    public static class Factory{

        
        

        /**
        * static method to create the object
        * Precondition:  If this object is an element, the current or next start element starts this object and any intervening reader events are ignorable
        *                If this object is not an element, it is a complex type and the reader is at the event just after the outer start element
        * Postcondition: If this object is an element, the reader is positioned at its end element
        *                If this object is a complex type, the reader is positioned at the end element of its outer element
        */
        public static Auction parse(javax.xml.stream.XMLStreamReader reader) throws java.lang.Exception{
            Auction object =
                new Auction();

            int event;
            java.lang.String nillableValue = null;
            java.lang.String prefix ="";
            java.lang.String namespaceuri ="";
            try {
                
                while (!reader.isStartElement() && !reader.isEndElement())
                    reader.next();

                
                if (reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","type")!=null){
                  java.lang.String fullTypeName = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance",
                        "type");
                  if (fullTypeName!=null){
                    java.lang.String nsPrefix = null;
                    if (fullTypeName.indexOf(":") > -1){
                        nsPrefix = fullTypeName.substring(0,fullTypeName.indexOf(":"));
                    }
                    nsPrefix = nsPrefix==null?"":nsPrefix;

                    java.lang.String type = fullTypeName.substring(fullTypeName.indexOf(":")+1);
                    
                            if (!"auction".equals(type)){
                                //find namespace for the prefix
                                java.lang.String nsUri = reader.getNamespaceContext().getNamespaceURI(nsPrefix);
                                return (Auction)edu.columbia.ase.auctionhouse.webservice.ExtensionMapper.getTypeObject(
                                     nsUri,type,reader);
                              }
                        

                  }
                

                }

                

                
                // Note all attributes that were handled. Used to differ normal attributes
                // from anyAttributes.
                java.util.Vector handledAttributes = new java.util.Vector();
                

                
                    // handle attribute "auctionId"
                    java.lang.String tempAttribAuctionId =
                        
                                reader.getAttributeValue(null,"auctionId");
                            
                   if (tempAttribAuctionId!=null){
                         java.lang.String content = tempAttribAuctionId;
                        
                                                 object.setAuctionId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(tempAttribAuctionId));
                                            
                    } else {
                       
                               throw new org.apache.axis2.databinding.ADBException("Required attribute auctionId is missing");
                           
                    }
                    handledAttributes.add("auctionId");
                    
                    // handle attribute "currentPrice"
                    java.lang.String tempAttribCurrentPrice =
                        
                                reader.getAttributeValue(null,"currentPrice");
                            
                   if (tempAttribCurrentPrice!=null){
                         java.lang.String content = tempAttribCurrentPrice;
                        
                                                 object.setCurrentPrice(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDouble(tempAttribCurrentPrice));
                                            
                    } else {
                       
                               throw new org.apache.axis2.databinding.ADBException("Required attribute currentPrice is missing");
                           
                    }
                    handledAttributes.add("currentPrice");
                    
                    // handle attribute "endTime"
                    java.lang.String tempAttribEndTime =
                        
                                reader.getAttributeValue(null,"endTime");
                            
                   if (tempAttribEndTime!=null){
                         java.lang.String content = tempAttribEndTime;
                        
                                                 object.setEndTime(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(tempAttribEndTime));
                                            
                    } else {
                       
                    }
                    handledAttributes.add("endTime");
                    
                    // handle attribute "itemCondition"
                    java.lang.String tempAttribItemCondition =
                        
                                reader.getAttributeValue(null,"itemCondition");
                            
                   if (tempAttribItemCondition!=null){
                         java.lang.String content = tempAttribItemCondition;
                        
                                                 object.setItemCondition(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToString(tempAttribItemCondition));
                                            
                    } else {
                       
                    }
                    handledAttributes.add("itemCondition");
                    
                    // handle attribute "startTime"
                    java.lang.String tempAttribStartTime =
                        
                                reader.getAttributeValue(null,"startTime");
                            
                   if (tempAttribStartTime!=null){
                         java.lang.String content = tempAttribStartTime;
                        
                                                 object.setStartTime(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDateTime(tempAttribStartTime));
                                            
                    } else {
                       
                    }
                    handledAttributes.add("startTime");
                    
                    
                    reader.next();
                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","auctionItemId").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"auctionItemId" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAuctionItemId(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","auctionLengthInMinutes").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"auctionLengthInMinutes" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setAuctionLengthInMinutes(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","item").equals(reader.getName())){
                                
                                                object.setItem(edu.columbia.ase.auctionhouse.webservice.AuctionItem.Factory.parse(reader));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                    else {
                                        
                                    }
                                
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","startOffsetMinutes").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"startOffsetMinutes" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setStartOffsetMinutes(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToInt(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                            
                                    
                                    while (!reader.isStartElement() && !reader.isEndElement()) reader.next();
                                
                                    if (reader.isStartElement() && new javax.xml.namespace.QName("","startPrice").equals(reader.getName())){
                                
                                    nillableValue = reader.getAttributeValue("http://www.w3.org/2001/XMLSchema-instance","nil");
                                    if ("true".equals(nillableValue) || "1".equals(nillableValue)){
                                        throw new org.apache.axis2.databinding.ADBException("The element: "+"startPrice" +"  cannot be null");
                                    }
                                    

                                    java.lang.String content = reader.getElementText();
                                    
                                              object.setStartPrice(
                                                    org.apache.axis2.databinding.utils.ConverterUtil.convertToDouble(content));
                                              
                                        reader.next();
                                    
                              }  // End of if for expected property start element
                                
                                else{
                                    // A start element we are not expecting indicates an invalid parameter was passed
                                    throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                                }
                              
                            while (!reader.isStartElement() && !reader.isEndElement())
                                reader.next();
                            
                                if (reader.isStartElement())
                                // A start element we are not expecting indicates a trailing invalid property
                                throw new org.apache.axis2.databinding.ADBException("Unexpected subelement " + reader.getName());
                            



            } catch (javax.xml.stream.XMLStreamException e) {
                throw new java.lang.Exception(e);
            }

            return object;
        }

        }//end of factory class

        

        }
           
    
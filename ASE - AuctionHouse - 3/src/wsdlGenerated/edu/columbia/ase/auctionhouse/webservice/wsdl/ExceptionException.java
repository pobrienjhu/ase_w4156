
/**
 * ExceptionException.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis2 version: 1.6.2  Built on : Apr 17, 2012 (05:33:49 IST)
 */

package edu.columbia.ase.auctionhouse.webservice.wsdl;

public class ExceptionException extends java.lang.Exception{

    private static final long serialVersionUID = 1378652771965L;
    
    private edu.columbia.ase.auctionhouse.webservice.ExceptionE faultMessage;

    
        public ExceptionException() {
            super("ExceptionException");
        }

        public ExceptionException(java.lang.String s) {
           super(s);
        }

        public ExceptionException(java.lang.String s, java.lang.Throwable ex) {
          super(s, ex);
        }

        public ExceptionException(java.lang.Throwable cause) {
            super(cause);
        }
    

    public void setFaultMessage(edu.columbia.ase.auctionhouse.webservice.ExceptionE msg){
       faultMessage = msg;
    }
    
    public edu.columbia.ase.auctionhouse.webservice.ExceptionE getFaultMessage(){
       return faultMessage;
    }
}
    
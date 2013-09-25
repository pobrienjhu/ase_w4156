
package edu.columbia.ase.auctionhouse.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for placeBid complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="placeBid">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="customerId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="auctionId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="bidAmount" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "placeBid", propOrder = {
    "customerId",
    "auctionId",
    "bidAmount"
})
public class PlaceBid {

    protected int customerId;
    protected int auctionId;
    protected double bidAmount;

    /**
     * Gets the value of the customerId property.
     * 
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets the value of the customerId property.
     * 
     */
    public void setCustomerId(int value) {
        this.customerId = value;
    }

    /**
     * Gets the value of the auctionId property.
     * 
     */
    public int getAuctionId() {
        return auctionId;
    }

    /**
     * Sets the value of the auctionId property.
     * 
     */
    public void setAuctionId(int value) {
        this.auctionId = value;
    }

    /**
     * Gets the value of the bidAmount property.
     * 
     */
    public double getBidAmount() {
        return bidAmount;
    }

    /**
     * Sets the value of the bidAmount property.
     * 
     */
    public void setBidAmount(double value) {
        this.bidAmount = value;
    }

}

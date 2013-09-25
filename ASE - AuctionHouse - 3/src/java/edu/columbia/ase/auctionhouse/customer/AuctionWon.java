
package edu.columbia.ase.auctionhouse.customer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for auctionWon complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="auctionWon">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="auctionId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="auctionPrice" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "auctionWon", propOrder = {
    "auctionId",
    "auctionPrice"
})
public class AuctionWon {

    protected int auctionId;
    protected double auctionPrice;

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
     * Gets the value of the auctionPrice property.
     * 
     */
    public double getAuctionPrice() {
        return auctionPrice;
    }

    /**
     * Sets the value of the auctionPrice property.
     * 
     */
    public void setAuctionPrice(double value) {
        this.auctionPrice = value;
    }

}

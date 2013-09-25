
package edu.columbia.ase.auctionhouse.webservice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for auction complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="auction">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="auctionItemId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="auctionLengthInMinutes" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="item" type="{http://webservice.auctionhouse.ase.columbia.edu/}auctionItem" minOccurs="0"/>
 *         &lt;element name="startOffsetMinutes" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="startPrice" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *       &lt;attribute name="auctionId" use="required" type="{http://www.w3.org/2001/XMLSchema}int" />
 *       &lt;attribute name="currentPrice" use="required" type="{http://www.w3.org/2001/XMLSchema}double" />
 *       &lt;attribute name="endTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *       &lt;attribute name="itemCondition" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="startTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "auction", propOrder = {
    "auctionItemId",
    "auctionLengthInMinutes",
    "item",
    "startOffsetMinutes",
    "startPrice"
})
public class Auction {

    protected int auctionItemId;
    protected int auctionLengthInMinutes;
    protected AuctionItem item;
    protected int startOffsetMinutes;
    protected double startPrice;
    @XmlAttribute(name = "auctionId", required = true)
    protected int auctionId;
    @XmlAttribute(name = "currentPrice", required = true)
    protected double currentPrice;
    @XmlAttribute(name = "endTime")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endTime;
    @XmlAttribute(name = "itemCondition")
    protected String itemCondition;
    @XmlAttribute(name = "startTime")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startTime;

    /**
     * Gets the value of the auctionItemId property.
     * 
     */
    public int getAuctionItemId() {
        return auctionItemId;
    }

    /**
     * Sets the value of the auctionItemId property.
     * 
     */
    public void setAuctionItemId(int value) {
        this.auctionItemId = value;
    }

    /**
     * Gets the value of the auctionLengthInMinutes property.
     * 
     */
    public int getAuctionLengthInMinutes() {
        return auctionLengthInMinutes;
    }

    /**
     * Sets the value of the auctionLengthInMinutes property.
     * 
     */
    public void setAuctionLengthInMinutes(int value) {
        this.auctionLengthInMinutes = value;
    }

    /**
     * Gets the value of the item property.
     * 
     * @return
     *     possible object is
     *     {@link AuctionItem }
     *     
     */
    public AuctionItem getItem() {
        return item;
    }

    /**
     * Sets the value of the item property.
     * 
     * @param value
     *     allowed object is
     *     {@link AuctionItem }
     *     
     */
    public void setItem(AuctionItem value) {
        this.item = value;
    }

    /**
     * Gets the value of the startOffsetMinutes property.
     * 
     */
    public int getStartOffsetMinutes() {
        return startOffsetMinutes;
    }

    /**
     * Sets the value of the startOffsetMinutes property.
     * 
     */
    public void setStartOffsetMinutes(int value) {
        this.startOffsetMinutes = value;
    }

    /**
     * Gets the value of the startPrice property.
     * 
     */
    public double getStartPrice() {
        return startPrice;
    }

    /**
     * Sets the value of the startPrice property.
     * 
     */
    public void setStartPrice(double value) {
        this.startPrice = value;
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
     * Gets the value of the currentPrice property.
     * 
     */
    public double getCurrentPrice() {
        return currentPrice;
    }

    /**
     * Sets the value of the currentPrice property.
     * 
     */
    public void setCurrentPrice(double value) {
        this.currentPrice = value;
    }

    /**
     * Gets the value of the endTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndTime() {
        return endTime;
    }

    /**
     * Sets the value of the endTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndTime(XMLGregorianCalendar value) {
        this.endTime = value;
    }

    /**
     * Gets the value of the itemCondition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemCondition() {
        return itemCondition;
    }

    /**
     * Sets the value of the itemCondition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemCondition(String value) {
        this.itemCondition = value;
    }

    /**
     * Gets the value of the startTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartTime() {
        return startTime;
    }

    /**
     * Sets the value of the startTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartTime(XMLGregorianCalendar value) {
        this.startTime = value;
    }

}

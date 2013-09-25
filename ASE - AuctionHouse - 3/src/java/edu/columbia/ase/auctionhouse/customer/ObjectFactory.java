
package edu.columbia.ase.auctionhouse.customer;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the edu.columbia.ase.auctionhouse.customer package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _SetAuctionHouseWebServiceURL_QNAME = new QName("http://customer.auctionhouse.ase.columbia.edu/", "setAuctionHouseWebServiceURL");
    private final static QName _AuctionWon_QNAME = new QName("http://customer.auctionhouse.ase.columbia.edu/", "auctionWon");
    private final static QName _SetCustomerURL_QNAME = new QName("http://customer.auctionhouse.ase.columbia.edu/", "setCustomerURL");
    private final static QName _GetCustomerURL_QNAME = new QName("http://customer.auctionhouse.ase.columbia.edu/", "getCustomerURL");
    private final static QName _SetCustomerURLResponse_QNAME = new QName("http://customer.auctionhouse.ase.columbia.edu/", "setCustomerURLResponse");
    private final static QName _SetId_QNAME = new QName("http://customer.auctionhouse.ase.columbia.edu/", "setId");
    private final static QName _SetAuctionHouseWebServiceURLResponse_QNAME = new QName("http://customer.auctionhouse.ase.columbia.edu/", "setAuctionHouseWebServiceURLResponse");
    private final static QName _AuctionWonResponse_QNAME = new QName("http://customer.auctionhouse.ase.columbia.edu/", "auctionWonResponse");
    private final static QName _GetAuctionHouseWebServiceURLResponse_QNAME = new QName("http://customer.auctionhouse.ase.columbia.edu/", "getAuctionHouseWebServiceURLResponse");
    private final static QName _GetAuctionHouseWebServiceURL_QNAME = new QName("http://customer.auctionhouse.ase.columbia.edu/", "getAuctionHouseWebServiceURL");
    private final static QName _GetCustomerURLResponse_QNAME = new QName("http://customer.auctionhouse.ase.columbia.edu/", "getCustomerURLResponse");
    private final static QName _SetIdResponse_QNAME = new QName("http://customer.auctionhouse.ase.columbia.edu/", "setIdResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: edu.columbia.ase.auctionhouse.customer
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SetAuctionHouseWebServiceURLResponse }
     * 
     */
    public SetAuctionHouseWebServiceURLResponse createSetAuctionHouseWebServiceURLResponse() {
        return new SetAuctionHouseWebServiceURLResponse();
    }

    /**
     * Create an instance of {@link AuctionWonResponse }
     * 
     */
    public AuctionWonResponse createAuctionWonResponse() {
        return new AuctionWonResponse();
    }

    /**
     * Create an instance of {@link GetAuctionHouseWebServiceURLResponse }
     * 
     */
    public GetAuctionHouseWebServiceURLResponse createGetAuctionHouseWebServiceURLResponse() {
        return new GetAuctionHouseWebServiceURLResponse();
    }

    /**
     * Create an instance of {@link GetCustomerURLResponse }
     * 
     */
    public GetCustomerURLResponse createGetCustomerURLResponse() {
        return new GetCustomerURLResponse();
    }

    /**
     * Create an instance of {@link GetAuctionHouseWebServiceURL }
     * 
     */
    public GetAuctionHouseWebServiceURL createGetAuctionHouseWebServiceURL() {
        return new GetAuctionHouseWebServiceURL();
    }

    /**
     * Create an instance of {@link SetIdResponse }
     * 
     */
    public SetIdResponse createSetIdResponse() {
        return new SetIdResponse();
    }

    /**
     * Create an instance of {@link SetAuctionHouseWebServiceURL }
     * 
     */
    public SetAuctionHouseWebServiceURL createSetAuctionHouseWebServiceURL() {
        return new SetAuctionHouseWebServiceURL();
    }

    /**
     * Create an instance of {@link AuctionWon }
     * 
     */
    public AuctionWon createAuctionWon() {
        return new AuctionWon();
    }

    /**
     * Create an instance of {@link SetCustomerURL }
     * 
     */
    public SetCustomerURL createSetCustomerURL() {
        return new SetCustomerURL();
    }

    /**
     * Create an instance of {@link SetCustomerURLResponse }
     * 
     */
    public SetCustomerURLResponse createSetCustomerURLResponse() {
        return new SetCustomerURLResponse();
    }

    /**
     * Create an instance of {@link GetCustomerURL }
     * 
     */
    public GetCustomerURL createGetCustomerURL() {
        return new GetCustomerURL();
    }

    /**
     * Create an instance of {@link SetId }
     * 
     */
    public SetId createSetId() {
        return new SetId();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetAuctionHouseWebServiceURL }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://customer.auctionhouse.ase.columbia.edu/", name = "setAuctionHouseWebServiceURL")
    public JAXBElement<SetAuctionHouseWebServiceURL> createSetAuctionHouseWebServiceURL(SetAuctionHouseWebServiceURL value) {
        return new JAXBElement<SetAuctionHouseWebServiceURL>(_SetAuctionHouseWebServiceURL_QNAME, SetAuctionHouseWebServiceURL.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuctionWon }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://customer.auctionhouse.ase.columbia.edu/", name = "auctionWon")
    public JAXBElement<AuctionWon> createAuctionWon(AuctionWon value) {
        return new JAXBElement<AuctionWon>(_AuctionWon_QNAME, AuctionWon.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetCustomerURL }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://customer.auctionhouse.ase.columbia.edu/", name = "setCustomerURL")
    public JAXBElement<SetCustomerURL> createSetCustomerURL(SetCustomerURL value) {
        return new JAXBElement<SetCustomerURL>(_SetCustomerURL_QNAME, SetCustomerURL.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCustomerURL }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://customer.auctionhouse.ase.columbia.edu/", name = "getCustomerURL")
    public JAXBElement<GetCustomerURL> createGetCustomerURL(GetCustomerURL value) {
        return new JAXBElement<GetCustomerURL>(_GetCustomerURL_QNAME, GetCustomerURL.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetCustomerURLResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://customer.auctionhouse.ase.columbia.edu/", name = "setCustomerURLResponse")
    public JAXBElement<SetCustomerURLResponse> createSetCustomerURLResponse(SetCustomerURLResponse value) {
        return new JAXBElement<SetCustomerURLResponse>(_SetCustomerURLResponse_QNAME, SetCustomerURLResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://customer.auctionhouse.ase.columbia.edu/", name = "setId")
    public JAXBElement<SetId> createSetId(SetId value) {
        return new JAXBElement<SetId>(_SetId_QNAME, SetId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetAuctionHouseWebServiceURLResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://customer.auctionhouse.ase.columbia.edu/", name = "setAuctionHouseWebServiceURLResponse")
    public JAXBElement<SetAuctionHouseWebServiceURLResponse> createSetAuctionHouseWebServiceURLResponse(SetAuctionHouseWebServiceURLResponse value) {
        return new JAXBElement<SetAuctionHouseWebServiceURLResponse>(_SetAuctionHouseWebServiceURLResponse_QNAME, SetAuctionHouseWebServiceURLResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuctionWonResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://customer.auctionhouse.ase.columbia.edu/", name = "auctionWonResponse")
    public JAXBElement<AuctionWonResponse> createAuctionWonResponse(AuctionWonResponse value) {
        return new JAXBElement<AuctionWonResponse>(_AuctionWonResponse_QNAME, AuctionWonResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAuctionHouseWebServiceURLResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://customer.auctionhouse.ase.columbia.edu/", name = "getAuctionHouseWebServiceURLResponse")
    public JAXBElement<GetAuctionHouseWebServiceURLResponse> createGetAuctionHouseWebServiceURLResponse(GetAuctionHouseWebServiceURLResponse value) {
        return new JAXBElement<GetAuctionHouseWebServiceURLResponse>(_GetAuctionHouseWebServiceURLResponse_QNAME, GetAuctionHouseWebServiceURLResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAuctionHouseWebServiceURL }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://customer.auctionhouse.ase.columbia.edu/", name = "getAuctionHouseWebServiceURL")
    public JAXBElement<GetAuctionHouseWebServiceURL> createGetAuctionHouseWebServiceURL(GetAuctionHouseWebServiceURL value) {
        return new JAXBElement<GetAuctionHouseWebServiceURL>(_GetAuctionHouseWebServiceURL_QNAME, GetAuctionHouseWebServiceURL.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCustomerURLResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://customer.auctionhouse.ase.columbia.edu/", name = "getCustomerURLResponse")
    public JAXBElement<GetCustomerURLResponse> createGetCustomerURLResponse(GetCustomerURLResponse value) {
        return new JAXBElement<GetCustomerURLResponse>(_GetCustomerURLResponse_QNAME, GetCustomerURLResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://customer.auctionhouse.ase.columbia.edu/", name = "setIdResponse")
    public JAXBElement<SetIdResponse> createSetIdResponse(SetIdResponse value) {
        return new JAXBElement<SetIdResponse>(_SetIdResponse_QNAME, SetIdResponse.class, null, value);
    }

}

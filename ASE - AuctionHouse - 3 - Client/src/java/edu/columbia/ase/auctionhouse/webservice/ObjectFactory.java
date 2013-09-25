
package edu.columbia.ase.auctionhouse.webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the edu.columbia.ase.auctionhouse.webservice package. 
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

    private final static QName _GetActiveAuctionsResponse_QNAME = new QName("http://webservice.auctionhouse.ase.columbia.edu/", "getActiveAuctionsResponse");
    private final static QName _PlaceBid_QNAME = new QName("http://webservice.auctionhouse.ase.columbia.edu/", "placeBid");
    private final static QName _Exception_QNAME = new QName("http://webservice.auctionhouse.ase.columbia.edu/", "Exception");
    private final static QName _RegisterCustomerResponse_QNAME = new QName("http://webservice.auctionhouse.ase.columbia.edu/", "registerCustomerResponse");
    private final static QName _Auction_QNAME = new QName("http://webservice.auctionhouse.ase.columbia.edu/", "auction");
    private final static QName _GetActiveAuctions_QNAME = new QName("http://webservice.auctionhouse.ase.columbia.edu/", "getActiveAuctions");
    private final static QName _PlaceBidResponse_QNAME = new QName("http://webservice.auctionhouse.ase.columbia.edu/", "placeBidResponse");
    private final static QName _RegisterCustomer_QNAME = new QName("http://webservice.auctionhouse.ase.columbia.edu/", "registerCustomer");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: edu.columbia.ase.auctionhouse.webservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RegisterCustomerResponse }
     * 
     */
    public RegisterCustomerResponse createRegisterCustomerResponse() {
        return new RegisterCustomerResponse();
    }

    /**
     * Create an instance of {@link Exception }
     * 
     */
    public Exception createException() {
        return new Exception();
    }

    /**
     * Create an instance of {@link PlaceBid }
     * 
     */
    public PlaceBid createPlaceBid() {
        return new PlaceBid();
    }

    /**
     * Create an instance of {@link GetActiveAuctionsResponse }
     * 
     */
    public GetActiveAuctionsResponse createGetActiveAuctionsResponse() {
        return new GetActiveAuctionsResponse();
    }

    /**
     * Create an instance of {@link RegisterCustomer }
     * 
     */
    public RegisterCustomer createRegisterCustomer() {
        return new RegisterCustomer();
    }

    /**
     * Create an instance of {@link PlaceBidResponse }
     * 
     */
    public PlaceBidResponse createPlaceBidResponse() {
        return new PlaceBidResponse();
    }

    /**
     * Create an instance of {@link GetActiveAuctions }
     * 
     */
    public GetActiveAuctions createGetActiveAuctions() {
        return new GetActiveAuctions();
    }

    /**
     * Create an instance of {@link Auction }
     * 
     */
    public Auction createAuction() {
        return new Auction();
    }

    /**
     * Create an instance of {@link AuctionItem }
     * 
     */
    public AuctionItem createAuctionItem() {
        return new AuctionItem();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetActiveAuctionsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.auctionhouse.ase.columbia.edu/", name = "getActiveAuctionsResponse")
    public JAXBElement<GetActiveAuctionsResponse> createGetActiveAuctionsResponse(GetActiveAuctionsResponse value) {
        return new JAXBElement<GetActiveAuctionsResponse>(_GetActiveAuctionsResponse_QNAME, GetActiveAuctionsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PlaceBid }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.auctionhouse.ase.columbia.edu/", name = "placeBid")
    public JAXBElement<PlaceBid> createPlaceBid(PlaceBid value) {
        return new JAXBElement<PlaceBid>(_PlaceBid_QNAME, PlaceBid.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Exception }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.auctionhouse.ase.columbia.edu/", name = "Exception")
    public JAXBElement<Exception> createException(Exception value) {
        return new JAXBElement<Exception>(_Exception_QNAME, Exception.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegisterCustomerResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.auctionhouse.ase.columbia.edu/", name = "registerCustomerResponse")
    public JAXBElement<RegisterCustomerResponse> createRegisterCustomerResponse(RegisterCustomerResponse value) {
        return new JAXBElement<RegisterCustomerResponse>(_RegisterCustomerResponse_QNAME, RegisterCustomerResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.auctionhouse.ase.columbia.edu/", name = "auction")
    public JAXBElement<Object> createAuction(Object value) {
        return new JAXBElement<Object>(_Auction_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetActiveAuctions }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.auctionhouse.ase.columbia.edu/", name = "getActiveAuctions")
    public JAXBElement<GetActiveAuctions> createGetActiveAuctions(GetActiveAuctions value) {
        return new JAXBElement<GetActiveAuctions>(_GetActiveAuctions_QNAME, GetActiveAuctions.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PlaceBidResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.auctionhouse.ase.columbia.edu/", name = "placeBidResponse")
    public JAXBElement<PlaceBidResponse> createPlaceBidResponse(PlaceBidResponse value) {
        return new JAXBElement<PlaceBidResponse>(_PlaceBidResponse_QNAME, PlaceBidResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegisterCustomer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservice.auctionhouse.ase.columbia.edu/", name = "registerCustomer")
    public JAXBElement<RegisterCustomer> createRegisterCustomer(RegisterCustomer value) {
        return new JAXBElement<RegisterCustomer>(_RegisterCustomer_QNAME, RegisterCustomer.class, null, value);
    }

}

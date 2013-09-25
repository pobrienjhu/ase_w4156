package edu.columbia.ase.auctionhouse.webservice;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.7.6
 * 2013-09-09T22:17:34.234-04:00
 * Generated source version: 2.7.6
 * 
 */
@WebServiceClient(name = "AuctionHouse", 
                  wsdlLocation = "http://localhost:8888/AuctionHouse?wsdl",
                  targetNamespace = "http://webservice.auctionhouse.ase.columbia.edu/") 
public class AuctionHouse extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://webservice.auctionhouse.ase.columbia.edu/", "AuctionHouse");
    public final static QName AuctionHouseWebServiceImplPort = new QName("http://webservice.auctionhouse.ase.columbia.edu/", "AuctionHouseWebServiceImplPort");
    static {
        URL url = null;
        try {
            url = new URL("http://localhost:8888/AuctionHouse?wsdl");
        } catch (MalformedURLException e) {
            java.util.logging.Logger.getLogger(AuctionHouse.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "http://localhost:8888/AuctionHouse?wsdl");
        }
        WSDL_LOCATION = url;
    }

    public AuctionHouse(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public AuctionHouse(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public AuctionHouse() {
        super(WSDL_LOCATION, SERVICE);
    }
    
    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public AuctionHouse(WebServiceFeature ... features) {
        super(WSDL_LOCATION, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public AuctionHouse(URL wsdlLocation, WebServiceFeature ... features) {
        super(wsdlLocation, SERVICE, features);
    }

    //This constructor requires JAX-WS API 2.2. You will need to endorse the 2.2
    //API jar or re-run wsdl2java with "-frontend jaxws21" to generate JAX-WS 2.1
    //compliant code instead.
    public AuctionHouse(URL wsdlLocation, QName serviceName, WebServiceFeature ... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     *
     * @return
     *     returns AuctionHouseWebServiceImpl
     */
    @WebEndpoint(name = "AuctionHouseWebServiceImplPort")
    public AuctionHouseWebServiceImpl getAuctionHouseWebServiceImplPort() {
        return super.getPort(AuctionHouseWebServiceImplPort, AuctionHouseWebServiceImpl.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns AuctionHouseWebServiceImpl
     */
    @WebEndpoint(name = "AuctionHouseWebServiceImplPort")
    public AuctionHouseWebServiceImpl getAuctionHouseWebServiceImplPort(WebServiceFeature... features) {
        return super.getPort(AuctionHouseWebServiceImplPort, AuctionHouseWebServiceImpl.class, features);
    }

}

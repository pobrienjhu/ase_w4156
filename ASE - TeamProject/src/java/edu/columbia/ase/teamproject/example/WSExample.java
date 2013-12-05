package edu.columbia.ase.teamproject.example;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

// TODO: Auto-generated Javadoc
/**
 * The Class WSExample.
 */
@Service("wsExampleEndpoint")
@WebService(serviceName = "WSExample")
public class WSExample {

    /** The log. */
    private Logger log = LoggerFactory.getLogger(WSExample.class);

    /**
     * Hello world.
     * 
     * @param inputString
     *            the input string
     * @return the string
     */
    @WebMethod(operationName = "helloWorld")
    public @WebResult(name = "returnString")
    String helloWorld(@WebParam(name = "inputString") String inputString) {
        log.info("input is |" + inputString + "|");
        return "Hello World. Your input was |" + inputString + "|";
    }

    /**
     * Hello world object.
     * 
     * @param inputString
     *            the input string
     * @return the wS example object
     */
    @WebMethod(operationName = "helloWorldObject")
    public @WebResult(name = "returnObject")
    WSExampleObject helloWorldObject(@WebParam(name = "inputString") String inputString) {
        log.info("input is |" + inputString + "|");
        return new WSExampleObject("Hello World. Your input was |" + inputString + "|");
    }

    /**
     * Method to exclude.
     */
    @WebMethod(exclude = true)
    public void methodToExclude() {

    }

}

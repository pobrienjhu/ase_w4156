package edu.columbia.ase.teamproject.example;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

// TODO: Auto-generated Javadoc
/**
 * The Class WSExampleObject.
 */
@XmlRootElement(name = "wsExample")
public class WSExampleObject {

    /** The message. */
    private String message;

    /*
     * Default constructor for de marshalling via Jaxb
     */
    /**
     * Instantiates a new wS example object.
     */
    public WSExampleObject() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * Instantiates a new wS example object.
     * 
     * @param message
     *            the message
     */
    public WSExampleObject(String message) {
        super();
        this.message = message;
    }

    /**
     * Gets the message.
     * 
     * @return the message
     */
    @XmlAttribute(name = "message")
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message.
     * 
     * @param message
     *            the new message
     */
    public void setMessage(String message) {
        this.message = message;
    }

}

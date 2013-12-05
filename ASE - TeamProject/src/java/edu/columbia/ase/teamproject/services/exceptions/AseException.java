package edu.columbia.ase.teamproject.services.exceptions;

import java.util.ArrayList;
import java.util.Collection;

// TODO: Auto-generated Javadoc
/**
 * The Class AseException.
 */
public class AseException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -3014083307092488968L;

    /** The error messages. */
    private Collection<String> errorMessages;

    /**
     * Instantiates a new ase exception.
     */
    public AseException() {
        super();
        this.errorMessages = new ArrayList<String>();
    }

    /**
     * Instantiates a new ase exception.
     *
     * @param message
     *            the message
     */
    public AseException(String message) {
        this();
        addMessage(message);
    }

    /**
     * Gets the error messages.
     *
     * @return the list of errorMessages
     */
    public Collection<String> getErrorMessages() {
        return errorMessages;
    }

    /**
     * Adds the message.
     *
     * @param message
     *            the message
     */
    public void addMessage(String message) {
        errorMessages.add(message);
    }

    /**
     * Adds the all messages.
     *
     * @param messages
     *            the messages
     */
    public void addAllMessages(Collection<String> messages) {
        errorMessages.addAll(messages);
    }

    /**
     * Checks for error messages.
     *
     * @return true, if successful
     */
    public boolean hasErrorMessages() {
        return (errorMessages.size() > 0);
    }

}

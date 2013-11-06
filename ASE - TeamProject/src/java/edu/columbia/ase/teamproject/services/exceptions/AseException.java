package edu.columbia.ase.teamproject.services.exceptions;

import java.util.ArrayList;
import java.util.Collection;

public class AseException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3014083307092488968L;
	
	
	private Collection<String> errorMessages;
	
	public AseException() {
		super();
		this.errorMessages = new ArrayList<String>();
	}
	
	public AseException(String message) {
		this();
		addMessage(message);
	}

	/**
	 * @return the list of errorMessages
	 */
	public Collection<String> getErrorMessages() {
		return errorMessages;
	}

	/**
	 * @param add an error message to the list of error messages
	 */
	public void addMessage(String message) {
		errorMessages.add(message);
	}
	
	public void addAllMessages(Collection<String> messages){
		errorMessages.addAll(messages);
	}
	
	public boolean hasErrorMessages(){
		return (errorMessages.size() > 0);
	}


	
}

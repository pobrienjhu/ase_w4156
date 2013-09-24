package edu.columbia.ase.teamproject.example;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="wsExample")
public class WSExampleObject {
	
	private String message;

	
	/*
	 * Default constructor for de marshalling via Jaxb
	 */
	public WSExampleObject() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WSExampleObject(String message) {
		super();
		this.message = message;
	}

	@XmlAttribute(name="message")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}

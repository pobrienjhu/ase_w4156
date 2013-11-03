package edu.columbia.w4156.ase.android;

import java.io.Serializable;

public class Session implements Cloneable, Serializable {

	private static final long serialVersionUID = 1L;

	private String sessionId;
	private String csrfToken;

	public Session(String sessionId, String csrfToken) {
		this.sessionId = sessionId;
		this.csrfToken = csrfToken;
	}

	public String getCsrfToken() {
		return csrfToken;
	}

	public String getSessionId() {
		return sessionId;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}

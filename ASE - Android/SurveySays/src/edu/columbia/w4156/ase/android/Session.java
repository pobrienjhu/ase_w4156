package edu.columbia.w4156.ase.android;

public class Session implements Cloneable {
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

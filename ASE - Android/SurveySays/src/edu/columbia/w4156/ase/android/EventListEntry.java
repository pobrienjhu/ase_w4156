package edu.columbia.w4156.ase.android;


public class EventListEntry {

	private long eventId;
	private String eventName;
	private String eventDescription;

	public EventListEntry(long eventId, String eventName,
			String eventDescription) {
		this.eventId = eventId;
		this.eventName = eventName;
		this.eventDescription = eventDescription;
	}

	public long getEventId() {
		return eventId;
	}

	public String getEventName() {
		return eventName;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	@Override
	public String toString() {
		return eventName;
	}
}

package edu.columbia.ase.teamproject.persistence.domain.enumeration;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

// TODO: Auto-generated Javadoc
/**
 * The Enum EventType.
 */
public enum EventType {

	/**
	 * Event only accessible to users designated by the admin.
	 */
	PRIVATE("PRIVATE"),
	/**
	 * Event accessible to anyone on the site.
	 */
	PUBLIC("PUBLIC");

	/** The Constant MAX_EVENT_TYPE_LENGTH. */
	public static final int MAX_EVENT_TYPE_LENGTH = 64;

	/** The event type. */
	private final String eventType;

	/** The Constant stringToEnum. */
	private static final Map<String, EventType> stringToEnum;

	static {
		final Map<String, EventType> tempMap = new HashMap<String, EventType>();
		for (EventType eventType : values()) {
			tempMap.put(eventType.getEventType(), eventType);
		}
		stringToEnum = ImmutableMap.copyOf(tempMap);
	}

	/**
	 * Instantiates a new event type.
	 *
	 * @param eventType
	 *            the event type
	 */
	private EventType(String eventType) {
		Preconditions.checkArgument(eventType.length() < MAX_EVENT_TYPE_LENGTH);
		this.eventType = eventType;
	}

	/**
	 * Gets the event type.
	 *
	 * @return the event type
	 */
	public String getEventType() {
		return eventType;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return eventType;
	}

	/**
	 * From string.
	 *
	 * @param eventType
	 *            the event type
	 * @return the event type
	 */
	public static EventType fromString(String eventType) {
		return stringToEnum.get(eventType);
	}

}

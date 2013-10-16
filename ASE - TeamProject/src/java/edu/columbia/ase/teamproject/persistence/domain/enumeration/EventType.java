package edu.columbia.ase.teamproject.persistence.domain.enumeration;

import java.util.HashMap;
import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

public enum EventType {

	/**
	 * Event only accessible to users designated by the admin.
	 */
	PRIVATE("PRIVATE"),
	/**
	 * Event accessible to anyone on the site. 
	 */
	PUBLIC("PUBLIC");
	
	public static final int MAX_EVENT_TYPE_LENGTH = 64;

	private final String eventType;	
	
	private static final Map<String, EventType> stringToEnum;
	
	static {
		final Map<String, EventType> tempMap = 
				new HashMap<String, EventType>();
		for(EventType eventType: values()){
			tempMap.put(eventType.getEventType(), eventType);
		}
		stringToEnum = ImmutableMap.copyOf(tempMap);
	}
	
	private EventType(String eventType) {
		Preconditions.checkArgument(
				eventType.length() < MAX_EVENT_TYPE_LENGTH);
		this.eventType = eventType;
	}

	public String getEventType() {
		return eventType;
	}

	@Override
	public String toString() {
		return eventType;
	}
	
	public static EventType fromString(String eventType){
		return stringToEnum.get(eventType);
	}
	
}

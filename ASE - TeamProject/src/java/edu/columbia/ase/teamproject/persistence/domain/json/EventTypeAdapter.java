package edu.columbia.ase.teamproject.persistence.domain.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import edu.columbia.ase.teamproject.persistence.domain.Event;
import edu.columbia.ase.teamproject.persistence.domain.UserAccount;
import edu.columbia.ase.teamproject.persistence.domain.VoteCategory;
import edu.columbia.ase.teamproject.persistence.domain.enumeration.EventType;

public class EventTypeAdapter extends TypeAdapter<Event> {

	private static final Logger logger =
			LoggerFactory.getLogger(EventTypeAdapter.class);

	private static enum EventProperty {
		PROPERTY_ID("id"),
		PROPERTY_NAME("name"),
		PROPERTY_DESCRIPTION("description"),
		PROPERTY_EVENT_USERS("eventUsers"),
		PROPERTY_VOTE_CATEGORIES("voteCategories"),
		PROPERTY_EVENT_TYPE("eventType"),
		PROPERTY_EVENT_START("eventStart"),
		PROPERTY_EVENT_END("eventEnd");

		private static final Map<String, EventProperty> propertyMap;
		static {
			propertyMap =
					Maps.newHashMap();
			for (EventProperty property : EventProperty.values()) {
				propertyMap.put(property.toString(), property);
			}
		}

		private final String propertyName; 
		EventProperty(String propertyName) {
			Preconditions.checkArgument(!propertyName.isEmpty());
			this.propertyName = propertyName;
		}

		@Override
		public String toString() {
			return propertyName;
		}

		public static EventProperty fromString(String propertyName) {
			return propertyMap.get(propertyName);
		}
	}

	private class EventBuilder {
		private boolean idSet;
		private Long id;
		private boolean nameSet;
		private String name;
		private boolean descriptionSet;
		private String description;
		private boolean eventUsersSet;
		private List<UserAccount> eventUsers;
		private boolean voteCategoriesSet;
		private List<VoteCategory> voteCategories;
		private EventType eventType;
		private boolean eventTypeSet;
		private DateTime eventStart;
		private boolean eventStartSet;
		private DateTime eventEnd;
		private boolean eventEndSet;

		public Event build() {
			Preconditions.checkState(nameSet);
			Preconditions.checkState(descriptionSet);
			Preconditions.checkState(eventTypeSet);
			Preconditions.checkState(eventUsersSet);
			Preconditions.checkState(voteCategoriesSet);
			Preconditions.checkState(eventStartSet);
			Preconditions.checkState(eventEndSet);
			// TODO(pames): ensure that the admin information is serialized.
			logger.warn("Building an event without any admin info");
			Event event = new Event(null, name, description, eventType,
					eventStart, eventEnd);
			if (idSet) {
				event.setId(id);
			}
			return event;
		}

		public EventBuilder setEventType(EventType eventType) {
			Preconditions.checkState(!eventTypeSet);
			this.eventType = Preconditions.checkNotNull(eventType);
			this.eventTypeSet = true;
			return this;
		}
		
		public EventBuilder setId(Long id) {
			Preconditions.checkState(!idSet);
			this.id = Preconditions.checkNotNull(id);
			this.idSet = true;
			return this;
		}

		public EventBuilder setName(String name) {
			Preconditions.checkState(!nameSet);
			this.name = name;
			this.nameSet = true;
			return this;
		}

		public EventBuilder setDescription(String description) {
			Preconditions.checkState(!descriptionSet);
			this.description = description;
			this.descriptionSet = true;
			return this;
		}

		public EventBuilder setEventStart(DateTime eventStart) {
			Preconditions.checkState(!eventStartSet);
			this.eventStart = eventStart;
			this.eventStartSet = true;
			return this;
		}

		public EventBuilder setEventEnd(DateTime eventEnd) {
			Preconditions.checkState(!eventEndSet);
			this.eventEnd = eventEnd;
			this.eventEndSet = true;
			return this;
		}


		public EventBuilder setEventUsers(List<UserAccount> eventUsers) {
			Preconditions.checkState(!eventUsersSet);
			this.eventUsers = eventUsers;
			this.eventUsersSet = true;
			return this;
		}

		public EventBuilder setVoteCategories(List<VoteCategory> voteCategories) {
			Preconditions.checkState(!voteCategoriesSet);
			this.voteCategories = voteCategories;
			this.voteCategoriesSet = true;
			return this;
		}
	}

	@Override
	public void write(JsonWriter out, Event value) throws IOException {
		out.beginObject();
		out.name(EventProperty.PROPERTY_ID.toString());
		out.value(value.getId());
		out.name(EventProperty.PROPERTY_NAME.toString());
		out.value(value.getName());
		out.name(EventProperty.PROPERTY_DESCRIPTION.toString());
		out.value(value.getDescription());
		out.name(EventProperty.PROPERTY_EVENT_TYPE.toString());
		out.value(value.getEventType().toString());
		out.name(EventProperty.PROPERTY_EVENT_START.toString());
		out.value(value.getStartTime().getMillis());

		out.name(EventProperty.PROPERTY_EVENT_END.toString());
		out.value(value.getEndTime().getMillis());

		out.name(EventProperty.PROPERTY_EVENT_USERS.toString());
		out.beginArray();
		// TOOD(pames): serialize the event users.
		logger.warn("Ignoring serialization of " +
				EventProperty.PROPERTY_EVENT_USERS);
		out.endArray();

		out.name(EventProperty.PROPERTY_VOTE_CATEGORIES.toString());
		logger.warn("Ignoring serialization of " +
				EventProperty.PROPERTY_VOTE_CATEGORIES);
		out.beginArray();
		// TODO(pames): serialize the vote categories.
		out.endArray();
		out.endObject();
	}

	@Override
	public Event read(JsonReader in) throws IOException {
		EventBuilder builder = new EventBuilder();

		in. beginObject();
		while (in.peek() != JsonToken.END_OBJECT) {
			EventProperty property = EventProperty.fromString(in.nextName());
			switch (property) {
			case PROPERTY_DESCRIPTION:
				builder.setDescription(in.nextString());
				break;
			case PROPERTY_EVENT_USERS:
				// TODO(pames): deserialize the array of users and set it.
				logger.warn("Ignoring deserialization of " +
						EventProperty.PROPERTY_EVENT_USERS);
				builder.setEventUsers(new ArrayList<UserAccount>());
				in.skipValue();
				break;
			case PROPERTY_ID:
				builder.setId(in.nextLong());
				break;
			case PROPERTY_NAME:
				builder.setName(in.nextString());
				break;
			case PROPERTY_VOTE_CATEGORIES:
				// TODO(pames): deserialize the array of categories and set it.
				logger.warn("Ignoring deserialization of " +
						EventProperty.PROPERTY_VOTE_CATEGORIES);
				builder.setVoteCategories(new ArrayList<VoteCategory>());
				in.skipValue();
				break;
			case PROPERTY_EVENT_TYPE:
				String eventType = in.nextString();
				builder.setEventType(EventType.fromString(eventType));
				break;
			case PROPERTY_EVENT_START:
				builder.setEventStart(new DateTime(in.nextLong()));
				break;
			case PROPERTY_EVENT_END:
				builder.setEventEnd(new DateTime(in.nextLong()));
				break;
			}
		}

		in.endObject();
		return builder.build();
	}

}

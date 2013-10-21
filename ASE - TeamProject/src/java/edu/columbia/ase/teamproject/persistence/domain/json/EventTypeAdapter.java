package edu.columbia.ase.teamproject.persistence.domain.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import edu.columbia.ase.teamproject.persistence.domain.Event;
import edu.columbia.ase.teamproject.persistence.domain.UserAccount;
import edu.columbia.ase.teamproject.persistence.domain.Vote;
import edu.columbia.ase.teamproject.persistence.domain.VoteCategory;
import edu.columbia.ase.teamproject.persistence.domain.VoteOption;
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
			for (VoteCategory category : voteCategories) {
				event.addVoteCategory(category);
			}
			// TODO(pames): consider checking if the event id is set here
			// and then transitively walking all VoteCategories to see if
			// they have an ID (and if it matches this one), then in each
			// VoteCategory, walking the VoteOptions to see if they have
			// a category ID set which corresponds to the VoteCategory that
			// contains them.
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

	private static enum VoteCategoryProperty {
		PROPERTY_ID("id"),
		PROPERTY_EVENT_ID("eventId"),
		PROPERTY_CATEGORY_NAME("categoryName"),
		PROPERTY_DESCRIPTION("description"),
		PROPERTY_VOTE_OPTIONS("voteOptions");

		private static final Map<String, VoteCategoryProperty> propertyMap;
		static {
			propertyMap =
					Maps.newHashMap();
			for (VoteCategoryProperty property : VoteCategoryProperty.values()) {
				propertyMap.put(property.toString(), property);
			}
		}

		private final String propertyName; 
		VoteCategoryProperty(String propertyName) {
			Preconditions.checkArgument(!propertyName.isEmpty());
			this.propertyName = propertyName;
		}

		@Override
		public String toString() {
			return propertyName;
		}

		public static VoteCategoryProperty fromString(String propertyName) {
			return propertyMap.get(propertyName);
		}
	}

	private class VoteCategoryBuilder {
		private Long id;
		private boolean idSet;
		private Long eventId;
		private boolean eventIdSet;
		private String categoryName;
		private boolean categoryNameSet;
		private String description;
		private boolean descriptionSet;
		private List<VoteOption> voteOptions;
		private boolean voteOptionsSet;

		public VoteCategory build() {
			// TODO(pames): similar logic to the VoteOption builder applies here
			// as to whether this field should be required.
			// Preconditions.checkState(eventIdSet);
			Preconditions.checkState(categoryNameSet);
			Preconditions.checkState(descriptionSet);
			Preconditions.checkState(voteOptionsSet);
			VoteCategory category = new VoteCategory(categoryName, description);
			for (VoteOption option : voteOptions) {
				category.addVotingOption(option);
			}

			if (idSet) {
				category.setId(id);
			}

			return category;
		}

		public VoteCategoryBuilder setId(Long id) {
			Preconditions.checkState(!idSet);
			this.id = Preconditions.checkNotNull(id);
			idSet = true;
			return this;
		}

		public VoteCategoryBuilder setEventId(Long eventId) {
			// Currently, we don't use this.  Perhaps it should not be required.
			Preconditions.checkState(!eventIdSet);
			this.eventId = Preconditions.checkNotNull(eventId);
			eventIdSet = true;
			return this;
		}

		public VoteCategoryBuilder setCategoryName(String categoryName) {
			Preconditions.checkState(!categoryNameSet);
			this.categoryName = categoryName;
			categoryNameSet = true;
			return this;
		}

		public VoteCategoryBuilder setDescription(String description) {
			Preconditions.checkState(!descriptionSet);
			this.description = description;
			descriptionSet = true;
			return this;
		}

		public VoteCategoryBuilder setVoteOptions(List<VoteOption> voteOptions) {
			Preconditions.checkState(!voteOptionsSet);
			Preconditions.checkNotNull(voteOptions);
			this.voteOptions = Preconditions.checkNotNull(voteOptions);
			voteOptionsSet = true;
			return this;
		}
	}

	private void writeVoteCategory(JsonWriter out, VoteCategory value) 
			throws IOException {
		out.beginObject();
		out.name(VoteCategoryProperty.PROPERTY_ID.toString());
		out.value(value.getId());
		out.name(VoteCategoryProperty.PROPERTY_EVENT_ID.toString());
		out.value(value.getEvent().getId());
		out.name(VoteCategoryProperty.PROPERTY_CATEGORY_NAME.toString());
		out.value(value.getCategoryName());
		out.name(VoteCategoryProperty.PROPERTY_DESCRIPTION.toString());
		out.value(value.getDescription());
		out.name(VoteCategoryProperty.PROPERTY_VOTE_OPTIONS.toString());
		out.beginArray();
		for (VoteOption option : value.getVoteOptions()) {
			writeVoteOption(out, option);
		}
		out.endArray();
		out.endObject();
	}

	private static enum VoteOptionProperty {
		PROPERTY_ID("id"),
		PROPERTY_CATEGORY_ID("voteCategoryId"),
		PROPERTY_OPTION_NAME("optionName"),
		PROPERTY_VOTES("votes");

		private static final Map<String, VoteOptionProperty> propertyMap;
		static {
			propertyMap =
					Maps.newHashMap();
			for (VoteOptionProperty property : VoteOptionProperty.values()) {
				propertyMap.put(property.toString(), property);
			}
		}

		private final String propertyName; 
		VoteOptionProperty(String propertyName) {
			Preconditions.checkArgument(!propertyName.isEmpty());
			this.propertyName = propertyName;
		}

		@Override
		public String toString() {
			return propertyName;
		}

		public static VoteOptionProperty fromString(String propertyName) {
			return propertyMap.get(propertyName);
		}
	}

	private class VoteOptionBuilder {
		private boolean idSet;
		private Long id;
		private boolean voteCategoryIdSet;
		private Long voteCategoryId;
		private String optionName;
		private boolean optionNameSet;
		private List<Vote> votes;
		private boolean votesSet;

		public VoteOption build() {
			// TODO(pames): is this necessary?  what if we are deserializing
			// something constructed by the client for the first time?
			// In VoteCategoryBuilder this seems more reasonable because we
			// currently treat creating an event as a distinct operation but if
			// UI code is added to allow creating the initial categories before
			// sending the createEvent() call to the server, then that check
			// should go away too.
			// Preconditions.checkState(voteCategoryIdSet);
			Preconditions.checkState(optionNameSet);
			VoteOption option = new VoteOption(optionName);
			if (idSet) {
				option.setId(id);
			}
			return option;
		}

		public VoteOptionBuilder setId(Long id) {
			Preconditions.checkState(!idSet);
			this.id = Preconditions.checkNotNull(id);
			idSet = true;
			return this;
		}

		public VoteOptionBuilder setVoteCategoryId(Long voteCategoryId) {
			Preconditions.checkState(!voteCategoryIdSet);
			this.voteCategoryId = Preconditions.checkNotNull(voteCategoryId);
			voteCategoryIdSet = true;
			return this;
		}

		public VoteOptionBuilder setOptionName(String optionName) {
			Preconditions.checkState(!optionNameSet);
			this.optionName = optionName;
			optionNameSet = true;
			return this;
		}

		public VoteOptionBuilder setVotes(List<Vote> votes) {
			Preconditions.checkState(!votesSet);
			this.votes = votes;
			votesSet = true;
			return this;
		}

	}

	private void writeVoteOption(JsonWriter out, VoteOption value)
			throws IOException {
		out.beginObject();
		out.name(VoteOptionProperty.PROPERTY_ID.toString());
		out.value(value.getId());
		out.name(VoteOptionProperty.PROPERTY_CATEGORY_ID.toString());
		out.value(value.getVoteCategory().getId());
		out.name(VoteOptionProperty.PROPERTY_OPTION_NAME.toString());
		out.value(value.getOptionName());
		logger.warn("Not serializing vote counts.");
		out.endObject();
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
		out.beginArray();
		for (VoteCategory category : value.getVoteCategories()) {
			writeVoteCategory(out, category);
		}
		out.endArray();
		out.endObject();
	}
 
	private List<VoteOption> readVoteOptions(JsonReader in) throws IOException {
		List<VoteOption> options = Lists.newArrayList();
		in.beginArray();
		while (in.peek() != JsonToken.END_ARRAY) {
			in.beginObject();
			VoteOptionBuilder builder = new VoteOptionBuilder();
			while (in.peek() != JsonToken.END_OBJECT) {
				VoteOptionProperty property = VoteOptionProperty.fromString(in.nextName());
				switch (property) {
				case PROPERTY_ID:
					builder.setId(in.nextLong());
					break;
				case PROPERTY_CATEGORY_ID:
					builder.setVoteCategoryId(in.nextLong());
					break;
				case PROPERTY_OPTION_NAME:
					builder.setOptionName(in.nextString());
					break;
				case PROPERTY_VOTES:
					in.skipValue();
					logger.warn("Not deserializing votes.");
					break;
				}
			}
			in.endObject();
			options.add(builder.build());
		}
		in.endArray();
		return options;
	}

	private List<VoteCategory> readVoteCategories(JsonReader in) throws IOException {
		List<VoteCategory> categories = Lists.newArrayList();
		in.beginArray();
		while (in.peek() != JsonToken.END_ARRAY) {
			in.beginObject();
			VoteCategoryBuilder builder = new VoteCategoryBuilder();
			while (in.peek() != JsonToken.END_OBJECT) {
				VoteCategoryProperty property = VoteCategoryProperty.fromString(in.nextName());
				switch (property) {
				case PROPERTY_CATEGORY_NAME:
					builder.setCategoryName(in.nextString());
					break;
				case PROPERTY_DESCRIPTION:
					builder.setDescription(in.nextString());
					break;
				case PROPERTY_EVENT_ID:
					builder.setEventId(in.nextLong());
					break;
				case PROPERTY_ID:
					builder.setId(in.nextLong());
					break;
				case PROPERTY_VOTE_OPTIONS:
					builder.setVoteOptions(readVoteOptions(in));
					break;
				}
			}
			in.endObject();
			categories.add(builder.build());
		}
		in.endArray();
		return categories;
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
				builder.setVoteCategories(readVoteCategories(in));
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

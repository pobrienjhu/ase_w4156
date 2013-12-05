package edu.columbia.ase.teamproject.persistence.domain.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
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

// TODO: Auto-generated Javadoc
/**
 * The Class EventTypeAdapter.
 */
public class EventTypeAdapter extends TypeAdapter<Event> {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(EventTypeAdapter.class);

    /**
     * The Enum EventProperty.
     */
    private static enum EventProperty {

        /** The property id. */
        PROPERTY_ID("id"),

        /** The property name. */
        PROPERTY_NAME("name"),

        /** The property description. */
        PROPERTY_DESCRIPTION("description"),

        /** The property event users. */
        PROPERTY_EVENT_USERS("eventUsers"),

        /** The property user emails. */
        PROPERTY_USER_EMAILS("userEmails"),

        /** The property vote categories. */
        PROPERTY_VOTE_CATEGORIES("voteCategories"),

        /** The property event type. */
        PROPERTY_EVENT_TYPE("eventType"),

        /** The property event start. */
        PROPERTY_EVENT_START("eventStart"),

        /** The property event end. */
        PROPERTY_EVENT_END("eventEnd");

        /** The Constant propertyMap. */
        private static final Map<String, EventProperty> propertyMap;
        static {
            propertyMap = Maps.newHashMap();
            for (EventProperty property : EventProperty.values()) {
                propertyMap.put(property.toString(), property);
            }
        }

        /** The property name. */
        private final String propertyName;

        /**
         * Instantiates a new event property.
         * 
         * @param propertyName
         *            the property name
         */
        EventProperty(String propertyName) {
            Preconditions.checkArgument(!propertyName.isEmpty());
            this.propertyName = propertyName;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return propertyName;
        }

        /**
         * From string.
         * 
         * @param propertyName
         *            the property name
         * @return the event property
         */
        public static EventProperty fromString(String propertyName) {
            return propertyMap.get(propertyName);
        }
    }

    /**
     * The Class EventBuilder.
     */
    private class EventBuilder {

        /** The id set. */
        private boolean idSet;

        /** The id. */
        private Long id;

        /** The name set. */
        private boolean nameSet;

        /** The name. */
        private String name;

        /** The description set. */
        private boolean descriptionSet;

        /** The description. */
        private String description;

        /** The event users set. */
        private boolean eventUsersSet;

        /** The event users. */
        private List<UserAccount> eventUsers;

        /** The user emails set. */
        private boolean userEmailsSet;

        /** The user emails. */
        private List<String> userEmails;

        /** The vote categories set. */
        private boolean voteCategoriesSet;

        /** The vote categories. */
        private List<VoteCategory> voteCategories;

        /** The event type. */
        private EventType eventType;

        /** The event type set. */
        private boolean eventTypeSet;

        /** The event start. */
        private DateTime eventStart;

        /** The event start set. */
        private boolean eventStartSet;

        /** The event end. */
        private DateTime eventEnd;

        /** The event end set. */
        private boolean eventEndSet;

        /**
         * Builds the.
         * 
         * @return the event
         */
        public Event build() {
            Preconditions.checkState(nameSet);
            Preconditions.checkState(descriptionSet);
            Preconditions.checkState(eventTypeSet);
            Preconditions.checkState(eventUsersSet);
            Preconditions.checkState(voteCategoriesSet);
            Preconditions.checkState(eventStartSet);
            Preconditions.checkState(eventEndSet);
            if (!userEmailsSet) {
                this.userEmails = ImmutableList.<String> of();
            }
            // TODO(pames): ensure that the admin information is serialized.
            logger.warn("Building an event without any admin info");
            Event event = new Event(userEmails, null, name, description, eventType, eventStart,
                    eventEnd);
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

        /**
         * Sets the event type.
         * 
         * @param eventType
         *            the event type
         * @return the event builder
         */
        public EventBuilder setEventType(EventType eventType) {
            Preconditions.checkState(!eventTypeSet);
            this.eventType = Preconditions.checkNotNull(eventType);
            this.eventTypeSet = true;
            return this;
        }

        /**
         * Sets the id.
         * 
         * @param id
         *            the id
         * @return the event builder
         */
        public EventBuilder setId(Long id) {
            Preconditions.checkState(!idSet);
            this.id = Preconditions.checkNotNull(id);
            this.idSet = true;
            return this;
        }

        /**
         * Sets the name.
         * 
         * @param name
         *            the name
         * @return the event builder
         */
        public EventBuilder setName(String name) {
            Preconditions.checkState(!nameSet);
            this.name = name;
            this.nameSet = true;
            return this;
        }

        /**
         * Sets the description.
         * 
         * @param description
         *            the description
         * @return the event builder
         */
        public EventBuilder setDescription(String description) {
            Preconditions.checkState(!descriptionSet);
            this.description = description;
            this.descriptionSet = true;
            return this;
        }

        /**
         * Sets the event start.
         * 
         * @param eventStart
         *            the event start
         * @return the event builder
         */
        public EventBuilder setEventStart(DateTime eventStart) {
            Preconditions.checkState(!eventStartSet);
            this.eventStart = eventStart;
            this.eventStartSet = true;
            return this;
        }

        /**
         * Sets the event end.
         * 
         * @param eventEnd
         *            the event end
         * @return the event builder
         */
        public EventBuilder setEventEnd(DateTime eventEnd) {
            Preconditions.checkState(!eventEndSet);
            this.eventEnd = eventEnd;
            this.eventEndSet = true;
            return this;
        }

        /**
         * Sets the event users.
         * 
         * @param eventUsers
         *            the event users
         * @return the event builder
         */
        public EventBuilder setEventUsers(List<UserAccount> eventUsers) {
            Preconditions.checkState(!eventUsersSet);
            this.eventUsers = eventUsers;
            this.eventUsersSet = true;
            return this;
        }

        /**
         * Sets the user emails.
         * 
         * @param userEmails
         *            the user emails
         * @return the event builder
         */
        public EventBuilder setUserEmails(List<String> userEmails) {
            Preconditions.checkState(!userEmailsSet);
            this.userEmails = userEmails;
            this.userEmailsSet = true;
            return this;
        }

        /**
         * Sets the vote categories.
         * 
         * @param voteCategories
         *            the vote categories
         * @return the event builder
         */
        public EventBuilder setVoteCategories(List<VoteCategory> voteCategories) {
            Preconditions.checkState(!voteCategoriesSet);
            this.voteCategories = voteCategories;
            this.voteCategoriesSet = true;
            return this;
        }
    }

    /**
     * The Enum VoteCategoryProperty.
     */
    private static enum VoteCategoryProperty {

        /** The property id. */
        PROPERTY_ID("id"),

        /** The property event id. */
        PROPERTY_EVENT_ID("eventId"),

        /** The property category name. */
        PROPERTY_CATEGORY_NAME("categoryName"),

        /** The property description. */
        PROPERTY_DESCRIPTION("description"),

        /** The property vote options. */
        PROPERTY_VOTE_OPTIONS("voteOptions");

        /** The Constant propertyMap. */
        private static final Map<String, VoteCategoryProperty> propertyMap;
        static {
            propertyMap = Maps.newHashMap();
            for (VoteCategoryProperty property : VoteCategoryProperty.values()) {
                propertyMap.put(property.toString(), property);
            }
        }

        /** The property name. */
        private final String propertyName;

        /**
         * Instantiates a new vote category property.
         * 
         * @param propertyName
         *            the property name
         */
        VoteCategoryProperty(String propertyName) {
            Preconditions.checkArgument(!propertyName.isEmpty());
            this.propertyName = propertyName;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return propertyName;
        }

        /**
         * From string.
         * 
         * @param propertyName
         *            the property name
         * @return the vote category property
         */
        public static VoteCategoryProperty fromString(String propertyName) {
            return propertyMap.get(propertyName);
        }
    }

    /**
     * The Class VoteCategoryBuilder.
     */
    private class VoteCategoryBuilder {

        /** The id. */
        private Long id;

        /** The id set. */
        private boolean idSet;

        /** The event id. */
        private Long eventId;

        /** The event id set. */
        private boolean eventIdSet;

        /** The category name. */
        private String categoryName;

        /** The category name set. */
        private boolean categoryNameSet;

        /** The description. */
        private String description;

        /** The description set. */
        private boolean descriptionSet;

        /** The vote options. */
        private List<VoteOption> voteOptions;

        /** The vote options set. */
        private boolean voteOptionsSet;

        /**
         * Builds the.
         * 
         * @return the vote category
         */
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

        /**
         * Sets the id.
         * 
         * @param id
         *            the id
         * @return the vote category builder
         */
        public VoteCategoryBuilder setId(Long id) {
            Preconditions.checkState(!idSet);
            this.id = Preconditions.checkNotNull(id);
            idSet = true;
            return this;
        }

        /**
         * Sets the event id.
         * 
         * @param eventId
         *            the event id
         * @return the vote category builder
         */
        public VoteCategoryBuilder setEventId(Long eventId) {
            // Currently, we don't use this. Perhaps it should not be required.
            Preconditions.checkState(!eventIdSet);
            this.eventId = Preconditions.checkNotNull(eventId);
            eventIdSet = true;
            return this;
        }

        /**
         * Sets the category name.
         * 
         * @param categoryName
         *            the category name
         * @return the vote category builder
         */
        public VoteCategoryBuilder setCategoryName(String categoryName) {
            Preconditions.checkState(!categoryNameSet);
            this.categoryName = categoryName;
            categoryNameSet = true;
            return this;
        }

        /**
         * Sets the description.
         * 
         * @param description
         *            the description
         * @return the vote category builder
         */
        public VoteCategoryBuilder setDescription(String description) {
            Preconditions.checkState(!descriptionSet);
            this.description = description;
            descriptionSet = true;
            return this;
        }

        /**
         * Sets the vote options.
         * 
         * @param voteOptions
         *            the vote options
         * @return the vote category builder
         */
        public VoteCategoryBuilder setVoteOptions(List<VoteOption> voteOptions) {
            Preconditions.checkState(!voteOptionsSet);
            Preconditions.checkNotNull(voteOptions);
            this.voteOptions = Preconditions.checkNotNull(voteOptions);
            voteOptionsSet = true;
            return this;
        }
    }

    /**
     * Write vote category.
     * 
     * @param out
     *            the out
     * @param value
     *            the value
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private void writeVoteCategory(JsonWriter out, VoteCategory value) throws IOException {
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

    /**
     * The Enum VoteOptionProperty.
     */
    private static enum VoteOptionProperty {

        /** The property id. */
        PROPERTY_ID("id"),

        /** The property category id. */
        PROPERTY_CATEGORY_ID("voteCategoryId"),

        /** The property option name. */
        PROPERTY_OPTION_NAME("optionName"),

        /** The property votes. */
        PROPERTY_VOTES("votes");

        /** The Constant propertyMap. */
        private static final Map<String, VoteOptionProperty> propertyMap;
        static {
            propertyMap = Maps.newHashMap();
            for (VoteOptionProperty property : VoteOptionProperty.values()) {
                propertyMap.put(property.toString(), property);
            }
        }

        /** The property name. */
        private final String propertyName;

        /**
         * Instantiates a new vote option property.
         * 
         * @param propertyName
         *            the property name
         */
        VoteOptionProperty(String propertyName) {
            Preconditions.checkArgument(!propertyName.isEmpty());
            this.propertyName = propertyName;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Enum#toString()
         */
        @Override
        public String toString() {
            return propertyName;
        }

        /**
         * From string.
         * 
         * @param propertyName
         *            the property name
         * @return the vote option property
         */
        public static VoteOptionProperty fromString(String propertyName) {
            return propertyMap.get(propertyName);
        }
    }

    /**
     * The Class VoteOptionBuilder.
     */
    private class VoteOptionBuilder {

        /** The id set. */
        private boolean idSet;

        /** The id. */
        private Long id;

        /** The vote category id set. */
        private boolean voteCategoryIdSet;

        /** The vote category id. */
        private Long voteCategoryId;

        /** The option name. */
        private String optionName;

        /** The option name set. */
        private boolean optionNameSet;

        /** The votes. */
        private List<Vote> votes;

        /** The votes set. */
        private boolean votesSet;

        /**
         * Builds the.
         * 
         * @return the vote option
         */
        public VoteOption build() {
            // TODO(pames): is this necessary? what if we are deserializing
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

        /**
         * Sets the id.
         * 
         * @param id
         *            the id
         * @return the vote option builder
         */
        public VoteOptionBuilder setId(Long id) {
            Preconditions.checkState(!idSet);
            this.id = Preconditions.checkNotNull(id);
            idSet = true;
            return this;
        }

        /**
         * Sets the vote category id.
         * 
         * @param voteCategoryId
         *            the vote category id
         * @return the vote option builder
         */
        public VoteOptionBuilder setVoteCategoryId(Long voteCategoryId) {
            Preconditions.checkState(!voteCategoryIdSet);
            this.voteCategoryId = Preconditions.checkNotNull(voteCategoryId);
            voteCategoryIdSet = true;
            return this;
        }

        /**
         * Sets the option name.
         * 
         * @param optionName
         *            the option name
         * @return the vote option builder
         */
        public VoteOptionBuilder setOptionName(String optionName) {
            Preconditions.checkState(!optionNameSet);
            this.optionName = optionName;
            optionNameSet = true;
            return this;
        }

        /**
         * Sets the votes.
         * 
         * @param votes
         *            the votes
         * @return the vote option builder
         */
        public VoteOptionBuilder setVotes(List<Vote> votes) {
            Preconditions.checkState(!votesSet);
            this.votes = votes;
            votesSet = true;
            return this;
        }

    }

    /**
     * Write vote option.
     * 
     * @param out
     *            the out
     * @param value
     *            the value
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    private void writeVoteOption(JsonWriter out, VoteOption value) throws IOException {
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

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gson.TypeAdapter#write(com.google.gson.stream.JsonWriter,
     * java.lang.Object)
     */
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
        /*
         * out.name(EventProperty.PROPERTY_USER_EMAILS.toString());
         * out.value(value.getUserEmails().toString());
         */
        out.name(EventProperty.PROPERTY_EVENT_USERS.toString());
        out.beginArray();
        // TOOD(pames): serialize the event users.
        logger.warn("Ignoring serialization of " + EventProperty.PROPERTY_EVENT_USERS);
        out.endArray();

        out.name(EventProperty.PROPERTY_VOTE_CATEGORIES.toString());
        out.beginArray();
        for (VoteCategory category : value.getVoteCategories()) {
            writeVoteCategory(out, category);
        }
        out.endArray();
        out.endObject();
    }

    /**
     * Read vote options.
     * 
     * @param in
     *            the in
     * @return the list
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
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

    /**
     * Read vote categories.
     * 
     * @param in
     *            the in
     * @return the list
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
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

    /*
     * (non-Javadoc)
     * 
     * @see com.google.gson.TypeAdapter#read(com.google.gson.stream.JsonReader)
     */
    @Override
    public Event read(JsonReader in) throws IOException {

        EventBuilder builder = new EventBuilder();

        in.beginObject();
        while (in.peek() != JsonToken.END_OBJECT) {
            EventProperty property = EventProperty.fromString(in.nextName());
            switch (property) {
            case PROPERTY_DESCRIPTION:
                builder.setDescription(in.nextString());
                break;
            case PROPERTY_EVENT_USERS:
                // TODO(pames): deserialize the array of users and set it.
                logger.warn("Ignoring deserialization of " + EventProperty.PROPERTY_EVENT_USERS);
                builder.setEventUsers(new ArrayList<UserAccount>());
                in.skipValue();
                break;
            case PROPERTY_USER_EMAILS:

                List<String> uel = new ArrayList<String>();
                in.beginArray();
                while (in.peek() != JsonToken.END_ARRAY) {

                    String ue = in.nextString();
                    uel.add(ue);
                }
                in.endArray();

                builder.setUserEmails(uel);
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

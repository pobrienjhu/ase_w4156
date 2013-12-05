/**
 *
 */
package edu.columbia.ase.teamproject.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import edu.columbia.ase.teamproject.persistence.dao.EventDao;
import edu.columbia.ase.teamproject.persistence.dao.UserAccountDao;
import edu.columbia.ase.teamproject.persistence.dao.VoteCategoryDao;
import edu.columbia.ase.teamproject.persistence.dao.VoteOptionDao;
import edu.columbia.ase.teamproject.persistence.domain.Event;
import edu.columbia.ase.teamproject.persistence.domain.UserAccount;
import edu.columbia.ase.teamproject.persistence.domain.VoteCategory;
import edu.columbia.ase.teamproject.persistence.domain.VoteOption;
import edu.columbia.ase.teamproject.persistence.domain.enumeration.EventType;
import edu.columbia.ase.teamproject.security.Permission;
import edu.columbia.ase.teamproject.services.exceptions.ValidationException;

// TODO: Auto-generated Javadoc
/**
 * The Class EventService.
 *
 * @author aiman
 */
public class EventService {

    /** The Constant logger. */
    private static final Logger logger = LoggerFactory.getLogger(EventService.class);

    /** The user dao. */
    @Autowired
    UserAccountDao userDao;

    /** The event dao. */
    @Autowired
    EventDao eventDao;

    /** The vote category dao. */
    @Autowired
    VoteCategoryDao voteCategoryDao;

    /** The vote option dao. */
    @Autowired
    VoteOptionDao voteOptionDao;

    /**
     * This method sanity checks the object graph of an event. The sanity checks
     * are: 1) For every VoteCategory, if it has an ID, that the event
     * associated with the version in the database matches the ID of the event
     * that was provided. If the VoteCategory does not have an ID, it verifies
     * that the object it refers to is the same as the provided event. 2) For
     * every VoteOption, if it has an ID, the VoteCategory associated with the
     * version in the database matches the ID of the VoteCategory that it was
     * associated with in the Event. If the VoteOption does not have an ID, it
     * verifies that the object it refers to is the same as the VoteCategory
     * that it was associated with.
     *
     * These checks are necessary to ensure that if an event is deserialized
     * where a malicious user tampered with the data, they cannot overwrite a
     * record in the database not associated with the event that has been
     * provided.
     *
     * @param e
     *            the e
     */
    @VisibleForTesting
    void validateEvent(final Event e) {
        Preconditions.checkNotNull(e);
        // TODO(pames): when an 'updateEvent' or equivalent is written, be sure
        // that the 'id' field is set appropriately or else eventId will be
        // null, and so all the nested checks will fail.
        final Long eventId = e.getId();

        for (final VoteCategory voteCategory : e.getVoteCategories()) {
            final Long categoryId = voteCategory.getId();

            // If the VoteCategory has an ID, it must be persisted, which in
            // turn implies that the associated Event must be persisted.
            if (categoryId != null) {
                final VoteCategory dbCategory = voteCategoryDao.find(categoryId);
                if (!dbCategory.getEvent().getId().equals(eventId)) {
                    throw new IllegalStateException("existing voteCategory / event mismatch");
                }
            } else {
                if (voteCategory.getEvent() != e) {
                    throw new IllegalStateException("new voteCategory / event mismatch");
                }
            }

            for (final VoteOption voteOption : voteCategory.getVoteOptions()) {
                final Long optionId = voteOption.getId();
                if (optionId != null) {
                    final VoteOption dbOption = voteOptionDao.find(optionId);
                    if (!dbOption.getVoteCategory().getId().equals(voteCategory.getId())) {
                        throw new IllegalStateException("existing option / category mismatch");
                    }
                } else {
                    if (voteOption.getVoteCategory() != voteCategory) {
                        throw new IllegalStateException("new voteOption / category mismatch");
                    }
                }
            }
        }

    }

    /**
     * Trusted user data from user list.
     *
     * @param accounts
     *            the accounts
     * @return the list
     */
    private List<UserAccount> trustedUserDataFromUserList(final List<UserAccount> accounts) {
        Preconditions.checkNotNull(accounts);
        final List<Long> userIds = Lists.newArrayList();
        final List<UserAccount> userData = Lists.newArrayList();

        for (final UserAccount user : accounts) {
            if (user.getId() != null) {
                userIds.add(user.getId());
            }
        }

        for (final Long id : userIds) {
            final UserAccount user = userDao.find(id);
            if (user != null) {
                userData.add(user);
            } else {
                logger.warn("passed bogus user id " + id + " for event");
            }
        }
        return userData;
    }

    /**
     * Certain fields in an Event should be immutable from the client. These
     * fields are: a) UserAccount (do not permit users to propagate changes to
     * others) b) Votes (do not permit users to overwrite the actual votes)
     *
     * If the event ID is non-null, this retrieves those immutable fields from
     * the database and replaces the copy in the provided event with the known
     * trusted data.
     *
     * In order to update those fields, specific service layer APIs should be
     * used instead of modifying the event directly and re-persisting it.
     *
     * @param e
     *            the e
     */
    private void replaceImmutableClientFieldsWithTrustedData(final Event e) {
        Preconditions.checkNotNull(e);

        final List<UserAccount> trustedUserData = trustedUserDataFromUserList(e.getEventUsers());
        final List<UserAccount> trustedAdminData = trustedUserDataFromUserList(e.getAdminUsers());
        e.getEventUsers().clear();
        e.addAllEventUser(trustedUserData);
        e.getAdminUsers().clear();
        e.addAllAdminUser(trustedAdminData);

        // TODO(pames): retrieve / protect the Vote data.
    }

    /**
     * User can update event.
     *
     * @param user
     *            the user
     * @param eventId
     *            the event id
     * @return true, if successful
     */
    @VisibleForTesting
    boolean userCanUpdateEvent(final UserAccount user, final Long eventId) {
        Preconditions.checkNotNull(user);
        // No event ID provided means auto-create a new one.
        if (eventId == null) {
            return true;
        }

        final Event event = eventDao.find(eventId);
        return userCanUpdateEvent(user, event);
    }

    /**
     * User can update event.
     *
     * @param user
     *            the user
     * @param event
     *            the event
     * @return true, if successful
     */
    private boolean userCanUpdateEvent(final UserAccount user, final Event event) {
        if (event == null) {
            // XXX: is this OK? It allows someone to specify an arbitrary
            // event id.
            return true;
        }

        return event.getStartTime().isAfter(DateTime.now())
                && (event.getAdminUsers().contains(user) || user.getPermissions().contains(
                        Permission.ADMIN));
    }

    /*
     * try to retrieve all the public events. If there is an exception, log it
     * and return and empty ArrayList
     */
    /**
     * Gets the all active public events.
     *
     * @param currentTime
     *            the current time
     * @return the all active public events
     */
    public Collection<Event> getAllActivePublicEvents(final DateTime currentTime) {
        try {
            return eventDao.getAllActivePublicEvents(currentTime);
        } catch (final Exception e) {
            logger.error("Unable to retrieve active public events. Root Cause ("
                    + e.getLocalizedMessage() + ")");
        }
        return new ArrayList<Event>();
    }

    /*
     * try to retrieve all the public events. If there is an exception, log it
     * and return and empty ArrayList
     */
    /**
     * Gets the all completed public events.
     *
     * @param currentTime
     *            the current time
     * @return the all completed public events
     */
    public Collection<Event> getAllCompletedPublicEvents(final DateTime currentTime) {
        try {
            return eventDao.getAllCompletedPublicEvents(currentTime);
        } catch (final Exception e) {
            logger.error("Unable to retrieve completed public events. Root Cause ("
                    + e.getLocalizedMessage() + ")");
        }
        return new ArrayList<Event>();
    }

    /*
     * try to retrieve all the requested events. If there is an exception, log
     * it and return and empty ArrayList
     */
    /**
     * Gets the all active private events for user id.
     *
     * @param currentTime
     *            the current time
     * @param userId
     *            the user id
     * @return the all active private events for user id
     */
    public Collection<Event> getAllActivePrivateEventsForUserId(final DateTime currentTime,
            final Long userId) {
        try {
            return eventDao.getAllActivePrivateEventsForUserId(currentTime, userId);
        } catch (final Exception e) {
            logger.error("Unable to retrieve private events for userId (" + userId
                    + "). Root Cause (" + e.getLocalizedMessage() + ")");
        }
        return new ArrayList<Event>();
    }

    /*
     * try to retrieve all the requested events. If there is an exception, log
     * it and return and empty ArrayList
     */
    /**
     * Gets the all active admin events for user id.
     *
     * @param currentTime
     *            the current time
     * @param userId
     *            the user id
     * @return the all active admin events for user id
     */
    public Collection<Event> getAllActiveAdminEventsForUserId(final DateTime currentTime,
            final Long userId) {
        try {
            return eventDao.getAllActiveAdminEventsForUserId(currentTime, userId);
        } catch (final Exception e) {
            logger.error("Unable to retrieve active admin events for userId (" + userId
                    + "). Root Cause (" + e.getLocalizedMessage() + ")");
        }
        return new ArrayList<Event>();
    }

    /*
     * try to retrieve all the requested events. If there is an exception, log
     * it and return and empty ArrayList
     */
    /**
     * Gets the all completed admin events for user id.
     *
     * @param currentTime
     *            the current time
     * @param userId
     *            the user id
     * @return the all completed admin events for user id
     */
    public Collection<Event> getAllCompletedAdminEventsForUserId(final DateTime currentTime,
            final Long userId) {
        try {
            return eventDao.getAllCompletedAdminEventsForUserId(currentTime, userId);
        } catch (final Exception e) {
            logger.error("Unable to retrieve completed admin events for userId (" + userId
                    + "). Root Cause (" + e.getLocalizedMessage() + ")");
        }
        return new ArrayList<Event>();
    }

    /*
     * try to retrieve all the requested events. If there is an exception, log
     * it and return and empty ArrayList
     */
    /**
     * Gets the all completed private events for user id.
     *
     * @param currentTime
     *            the current time
     * @param userId
     *            the user id
     * @return the all completed private events for user id
     */
    public Collection<Event> getAllCompletedPrivateEventsForUserId(final DateTime currentTime,
            final Long userId) {
        try {
            return eventDao.getAllCompletedPrivateEventsForUserId(currentTime, userId);
        } catch (final Exception e) {
            logger.error("Unable to retrieve completed private eventsfor userId (" + userId
                    + "). Root Cause (" + e.getLocalizedMessage() + ")");
        }
        return new ArrayList<Event>();
    }

    /*
     * try to retrieve all the requested events. If there is an exception, log
     * it and return and empty ArrayList
     */
    /**
     * Gets the all future admin events for user id.
     *
     * @param currentTime
     *            the current time
     * @param userId
     *            the user id
     * @return the all future admin events for user id
     */
    public Collection<Event> getAllFutureAdminEventsForUserId(final DateTime currentTime,
            final Long userId) {
        try {
            return eventDao.getAllFutureAdminEventsForUserId(currentTime, userId);
        } catch (final Exception e) {
            logger.error("Unable to retrieve future admin events for userId (" + userId
                    + "). Root Cause (" + e.getLocalizedMessage() + ")");
        }
        return new ArrayList<Event>();
    }

    /**
     * Creates the event.
     *
     * @param requestor
     *            the requestor
     * @param name
     *            the name
     * @param description
     *            the description
     * @param eventType
     *            the event type
     * @param start
     *            the start
     * @param end
     *            the end
     * @param voteCategories
     *            the vote categories
     * @param userEmails
     *            the user emails
     * @return the event
     * @throws ValidationException
     *             the validation exception
     */
    public Event createEvent(final UserAccount requestor, final String name,
            final String description, final EventType eventType, final DateTime start,
            final DateTime end, final List<VoteCategory> voteCategories,
            final List<String> userEmails) throws ValidationException {
        Preconditions.checkNotNull(requestor);
        logger.info("Creating event " + name);

        final Event event = new Event(null, requestor, name, description, eventType, start, end);
        for (final VoteCategory v : voteCategories) {
            v.setEvent(event);
        }
        event.setVoteCategories(voteCategories);

        if (eventType == EventType.PRIVATE) {
            // remove duplicates
            final HashSet<String> hs = new HashSet<String>();
            hs.addAll(userEmails);
            hs.remove(requestor.getEmail());

            for (final String email : hs) {
                final UserAccount ua = userDao.findAccountByEmail(email);
                if (ua != null) {
                    event.addEventUser(ua);
                }
            }
            event.addEventUser(requestor);
        }
        EventValidationHelper.validateEventCreation(event);
        validateEvent(event);

        return eventDao.add(event);
    }

    /**
     * Adds the user to event.
     *
     * @param requestor
     *            the requestor
     * @param id
     *            the id
     * @param victim
     *            the victim
     */
    @Transactional
    public void addUserToEvent(final UserAccount requestor, final Long id, final UserAccount victim) {
        final Event event = eventDao.find(id);

        if (event == null || victim == null) {
            logger.info("Attempt to operate on non-existent event " + id);
            return;
        }

        if (!EventType.PRIVATE.equals(event.getEventType())) {
            return;
        }

        // XXX: throw exception?
        if (!userCanUpdateEvent(requestor, event)) {
            return;
        }
        // XXX: should eventUsers actually be a Set<...> then?
        if (!event.getEventUsers().contains(victim)) {
            event.getEventUsers().add(victim);
        }

        eventDao.add(event);
    }

    /**
     * Removes the user from event.
     *
     * @param requestor
     *            the requestor
     * @param id
     *            the id
     * @param victim
     *            the victim
     */
    @Transactional
    public void removeUserFromEvent(final UserAccount requestor, final Long id,
            final UserAccount victim) {
        final Event event = eventDao.find(id);
        if (event == null || victim == null) {
            logger.info("Attempt to operate on non-existent event " + id);
            return;
        }

        if (!EventType.PRIVATE.equals(event.getEventType())) {
            return;
        }

        // XXX: throw exception?
        if (!userCanUpdateEvent(requestor, event)) {
            return;
        }

        final int idx = event.getEventUsers().indexOf(victim);
        if (idx != -1) {
            event.getEventUsers().remove(victim);
        }
        eventDao.add(event);
    }

    /**
     * Update event.
     *
     * @param requestor
     *            the requestor
     * @param id
     *            the id
     * @param newData
     *            the new data
     * @return the event
     * @throws ValidationException
     *             the validation exception
     */
    @Transactional
    public Event updateEvent(final UserAccount requestor, final Long id, final Event newData)
            throws ValidationException {
        Preconditions.checkNotNull(requestor);
        Preconditions.checkNotNull(id);
        Preconditions.checkNotNull(newData);

        logger.info("Updating event " + id);
        final Event existing = eventDao.find(id);
        if (!userCanUpdateEvent(requestor, existing)) {
            logger.info("user " + requestor.getId() + " cannot update event " + id);
            // XXX: throw exception?
            return null;
        }

        EventValidationHelper.validateEventUpdate(existing);

        validateEvent(newData);
        replaceImmutableClientFieldsWithTrustedData(newData);

        existing.setName(newData.getName());
        existing.setDescription(newData.getDescription());
        existing.setOptimisticLockingVersion(newData.getOptimisticLockingVersion());
        existing.setStartTime(newData.getStartTime());
        existing.setEndTime(newData.getEndTime());
        existing.setEventType(newData.getEventType());

        // TODO(pames): serialize admin users to client + sanitize
        existing.getAdminUsers().clear();
        existing.addAllAdminUser(newData.getAdminUsers());

        existing.getEventUsers().clear();
        existing.addAllEventUser(newData.getEventUsers());

        existing.getVoteCategories().clear();
        for (final VoteCategory category : newData.getVoteCategories()) {
            existing.addVoteCategory(category);
        }
        return eventDao.add(existing);
    }

    /**
     * Lookup event.
     *
     * @param requestor
     *            the requestor
     * @param id
     *            the id
     * @return the event
     */
    public Event lookupEvent(final UserAccount requestor, final Long id) {
        Preconditions.checkNotNull(requestor);
        Preconditions.checkNotNull(id);

        final Event event = eventDao.find(id);
        if (event == null) {
            return null;
        }

        switch (event.getEventType()) {
        case PUBLIC:
            return event;
        case PRIVATE:
           
            if (event.getAdminUsers().contains(requestor)
                    || event.getEventUsers().contains(requestor)) {
            	return event;
            }
        }
        return null;
    }
}

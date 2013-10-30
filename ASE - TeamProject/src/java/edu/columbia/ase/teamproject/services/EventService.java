/**
 * 
 */
package edu.columbia.ase.teamproject.services;

import java.util.ArrayList;
import java.util.Collection;
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

/**
 * @author aiman
 *
 */
public class EventService {
	private static final Logger logger =
			LoggerFactory.getLogger(EventService.class);

	@Autowired
	UserAccountDao userDao;
	
	@Autowired
	EventDao eventDao;

	@Autowired
	VoteCategoryDao voteCategoryDao;

	@Autowired
	VoteOptionDao voteOptionDao;

	/**
	 * This method sanity checks the object graph of an event.  The sanity
	 * checks are:
	 * 1) For every VoteCategory, if it has an ID, that the event associated
	 *    with the version in the database matches the ID of the event that was
	 *    provided.  If the VoteCategory does not have an ID, it verifies that
	 *    the object it refers to is the same as the provided event.
	 * 2) For every VoteOption, if it has an ID, the VoteCategory associated
	 *    with the version in the database matches the ID of the VoteCategory
	 *    that it was associated with in the Event.  If the VoteOption does not
	 *    have an ID, it verifies that the object it refers to is the same as
	 *    the VoteCategory that it was associated with.
	 *
	 * These checks are necessary to ensure that if an event is deserialized
	 * where a malicious user tampered with the data, they cannot overwrite
	 * a record in the database not associated with the event that has been
	 * provided. 
	 */
	@VisibleForTesting
	void validateEvent(Event e) {
		Preconditions.checkNotNull(e);
		// TODO(pames): when an 'updateEvent' or equivalent is written, be sure
		// that the 'id' field is set appropriately or else eventId will be
		// null, and so all the nested checks will fail.
		Long eventId = e.getId();

		for (VoteCategory voteCategory : e.getVoteCategories()) {
			Long categoryId = voteCategory.getId();

			// If the VoteCategory has an ID, it must be persisted, which in
			// turn implies that the associated Event must be persisted.
			if (categoryId != null) {
				VoteCategory dbCategory = voteCategoryDao.find(categoryId);
				if (!dbCategory.getEvent().getId().equals(eventId)) {
					throw new IllegalStateException("existing voteCategory / event mismatch");
				}
			} else {
				if (voteCategory.getEvent() != e) {
					throw new IllegalStateException("new voteCategory / event mismatch");
				}
			}

			for (VoteOption voteOption : voteCategory.getVoteOptions()) {
				Long optionId = voteOption.getId();
				if (optionId != null) { 
					VoteOption dbOption = voteOptionDao.find(optionId);
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

	private List<UserAccount> trustedUserDataFromUserList(List<UserAccount> accounts) {
		Preconditions.checkNotNull(accounts);
		List<Long> userIds = Lists.newArrayList();
		List<UserAccount> userData = Lists.newArrayList();

		for (UserAccount user : accounts) {
			if (user.getId() != null) {
				userIds.add(user.getId());
			}
		}

		for (Long id : userIds) {
			UserAccount user = userDao.find(id);
			if (user != null) {
				userData.add(user);
			} else {
				logger.warn("passed bogus user id " + id + " for event");
			}
		}
		return userData;
	}

	/** 
	 * Certain fields in an Event should be immutable from the client.  These
	 * fields are:
	 *   a) UserAccount (do not permit users to propagate changes to others)
	 *   b) Votes (do not permit users to overwrite the actual votes)
	 * 
	 * If the event ID is non-null, this retrieves those immutable fields from
	 * the database and replaces the copy in the provided event with the
	 * known trusted data.
	 *
	 * In order to update those fields, specific service layer APIs should be
	 * used instead of modifying the event directly and re-persisting it.
	 */
	private void replaceImmutableClientFieldsWithTrustedData(Event e) {
		Preconditions.checkNotNull(e);

		List<UserAccount> trustedUserData =
				trustedUserDataFromUserList(e.getEventUsers());
		List<UserAccount> trustedAdminData =
				trustedUserDataFromUserList(e.getAdminUsers());
		e.getEventUsers().clear();
		e.addAllEventUser(trustedUserData);
		e.getAdminUsers().clear();
		e.addAllAdminUser(trustedAdminData);

		// TODO(pames): retrieve / protect the Vote data.
	}

	@VisibleForTesting
	boolean userCanUpdateEvent(UserAccount user, Long eventId) {
		Preconditions.checkNotNull(user);
		// No event ID provided means auto-create a new one.
		if (eventId == null) {
			return true;
		}

		Event event = eventDao.find(eventId);
		return userCanUpdateEvent(user, event);
	}

	private boolean userCanUpdateEvent(UserAccount user, Event event) {
		if (event == null) {
			// XXX: is this OK?  It allows someone to specify an arbitrary
			// event id.
			return true;
		}

		return event.getAdminUsers().contains(user) ||
				user.getPermissions().contains(Permission.ADMIN);		
	}

	/*
	 * try to retrieve all the public events. 
	 * If there is an exception, log it and return and empty ArrayList
	 */
	public Collection<Event> getAllActivePublicEvents(DateTime currentTime){
		try {
			return eventDao.getAllActivePublicEvents(currentTime);
		} catch (Exception e) {
			logger.error("Unable to retrieve active public events. Root Cause ("+e.getLocalizedMessage()+")");
		}
		return new ArrayList<Event>();
	}
	
	/*
	 * try to retrieve all the public events. 
	 * If there is an exception, log it and return and empty ArrayList
	 */
	public Collection<Event> getAllCompletedPublicEvents(DateTime currentTime){
		try {
			return eventDao.getAllCompletedPublicEvents(currentTime);
		} catch (Exception e) {
			logger.error("Unable to retrieve completed public events. Root Cause ("+e.getLocalizedMessage()+")");
		}
		return new ArrayList<Event>();
	}
	
	
	/*
	 * try to retrieve all the requested events. 
	 * If there is an exception, log it and return and empty ArrayList
	 */
	public Collection<Event> getAllActivePrivateEventsForUserId(DateTime currentTime, Long userId){
		try {
			return eventDao.getAllActivePrivateEventsForUserId(currentTime, userId);
		} catch (Exception e) {
			logger.error("Unable to retrieve private events for userId ("+userId+"). Root Cause ("+e.getLocalizedMessage()+")");
		}
		return new ArrayList<Event>();
	}
	
	/*
	 * try to retrieve all the requested events. 
	 * If there is an exception, log it and return and empty ArrayList
	 */
	public Collection<Event> getAllActiveAdminEventsForUserId(DateTime currentTime, Long userId){
		try {
			return eventDao.getAllActiveAdminEventsForUserId(currentTime, userId);
		} catch (Exception e) {
			logger.error("Unable to retrieve active admin events for userId ("+userId+"). Root Cause ("+e.getLocalizedMessage()+")");
		}
		return new ArrayList<Event>();
	}
	
	
	
	/*
	 * try to retrieve all the requested events. 
	 * If there is an exception, log it and return and empty ArrayList
	 */
	public Collection<Event> getAllCompletedAdminEventsForUserId(DateTime currentTime, Long userId){
		try {
			return eventDao.getAllCompletedAdminEventsForUserId(currentTime, userId);
		} catch (Exception e) {
			logger.error("Unable to retrieve completed admin events for userId ("+userId+"). Root Cause ("+e.getLocalizedMessage()+")");
		}
		return new ArrayList<Event>();
	}
	
	/*
	 * try to retrieve all the requested events. 
	 * If there is an exception, log it and return and empty ArrayList
	 */
	public Collection<Event> getAllCompletedPrivateEventsForUserId(DateTime currentTime, Long userId){
		try {
			return eventDao.getAllCompletedPrivateEventsForUserId(currentTime, userId);
		} catch (Exception e) {
			logger.error("Unable to retrieve completed private eventsfor userId ("+userId+"). Root Cause ("+e.getLocalizedMessage()+")");
		}
		return new ArrayList<Event>();
	}
	
	
	/*
	 * try to retrieve all the requested events. 
	 * If there is an exception, log it and return and empty ArrayList
	 */
	public Collection<Event> getAllFutureAdminEventsForUserId(DateTime currentTime, Long userId){
		try {
			return eventDao.getAllFutureAdminEventsForUserId(currentTime, userId);
		} catch (Exception e) {
			logger.error("Unable to retrieve future admin events for userId ("+userId+"). Root Cause ("+e.getLocalizedMessage()+")");
		}
		return new ArrayList<Event>();
	}
	

	public Event createEvent(UserAccount requestor, String name, String description, EventType eventType,
			DateTime start, DateTime end, List<VoteCategory> voteCategories)
	{
		Preconditions.checkNotNull(requestor);
		logger.info("Creating event " + name);

		Event event = new Event(requestor, name, description, eventType,
				start, end);
		for (VoteCategory v : voteCategories)
		{
			v.setEvent(event);
		}
		event.setVoteCategories(voteCategories);
		validateEvent(event);
		replaceImmutableClientFieldsWithTrustedData(event);
		return eventDao.add(event);
	}

	@Transactional
	public Event updateEvent(UserAccount requestor, Long id, Event newData) {
		Preconditions.checkNotNull(requestor);
		Preconditions.checkNotNull(id);
		Preconditions.checkNotNull(newData);

		logger.info("Updating event " + id);
		Event existing = eventDao.find(id);
		if (!userCanUpdateEvent(requestor, existing)) {
			logger.info("user " + requestor.getId() + " cannot update event " + id);
			// XXX: throw exception?
			return null;
		}

		if (DateTime.now().isAfter(existing.getStartTime())) {
			logger.info("attempt to update event after start time has failed");
			return null;
		}

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
		for (VoteCategory category : newData.getVoteCategories()) {
			existing.addVoteCategory(category);
		}
		return eventDao.add(existing);
	}

	public Event lookupEvent(UserAccount requestor, Long id) {
		Preconditions.checkNotNull(requestor);
		Preconditions.checkNotNull(id);

		Event event = eventDao.find(id);
		if (event == null) {
			return null;
		}

		switch (event.getEventType()) {
		case PUBLIC:
			return event;
		case PRIVATE:
			// TODO(pames): add tests for this
			if (event.getAdminUsers().contains(requestor) ||
				event.getEventUsers().contains(requestor)) {
				return event;
			}
		}
		return null;
	}
}

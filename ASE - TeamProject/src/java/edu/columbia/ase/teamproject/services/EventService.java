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

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Preconditions;

import edu.columbia.ase.teamproject.persistence.dao.EventDao;
import edu.columbia.ase.teamproject.persistence.dao.UserAccountDao;
import edu.columbia.ase.teamproject.persistence.dao.VoteCategoryDao;
import edu.columbia.ase.teamproject.persistence.dao.VoteOptionDao;
import edu.columbia.ase.teamproject.persistence.domain.Event;
import edu.columbia.ase.teamproject.persistence.domain.UserAccount;
import edu.columbia.ase.teamproject.persistence.domain.VoteCategory;
import edu.columbia.ase.teamproject.persistence.domain.VoteOption;
import edu.columbia.ase.teamproject.persistence.domain.enumeration.EventType;

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
		// TODO(pames): retrieve the user data.
		// TODO(pames): retrieve the Vote data.
	}

	@VisibleForTesting
	boolean userCanUpdateEvent(UserAccount user, Long eventId) {
		Preconditions.checkNotNull(user);
		// No event ID provided means auto-create a new one.
		if (eventId == null) {
			return true;
		}

		Event event = eventDao.find(eventId);
		if (event == null) {
			// XXX: is this OK?  It allows someone to specify an arbitrary
			// event id.
			return true;
		}

		return event.getAdminUsers().contains(user);
	}

	/*
	 * try to retrieve all the public events. 
	 * If there is an exception, log it and return and empty ArrayList
	 */
	public Collection<Event> getAllPublicEvents(DateTime currentTime){
		try {
			return eventDao.getAllPublicEvents(currentTime);
		} catch (Exception e) {
			logger.error("Unable to retrieve public events. Root Cause ("+e.getLocalizedMessage()+")");
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

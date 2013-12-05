package edu.columbia.ase.teamproject.persistence.dao;

import java.util.Collection;

import org.joda.time.DateTime;

import edu.columbia.ase.teamproject.persistence.dao.generic.GenericDao;
import edu.columbia.ase.teamproject.persistence.domain.Event;

// TODO: Auto-generated Javadoc
/**
 * The Interface EventDao.
 */
public interface EventDao extends GenericDao<Event, Long> {

    /**
     * Gets the all active public events.
     *
     * @param currentTime
     *            the current time
     * @return the all active public events
     */
    public Collection<Event> getAllActivePublicEvents(DateTime currentTime);

    /**
     * Gets the all active private events for user id.
     *
     * @param currentTime
     *            the current time
     * @param userId
     *            the user id
     * @return the all active private events for user id
     */
    public Collection<Event> getAllActivePrivateEventsForUserId(DateTime currentTime, Long userId);

    /**
     * Gets the all completed public events.
     *
     * @param currentTime
     *            the current time
     * @return the all completed public events
     */
    public Collection<Event> getAllCompletedPublicEvents(DateTime currentTime);

    /**
     * Gets the all completed private events for user id.
     *
     * @param currentTime
     *            the current time
     * @param userId
     *            the user id
     * @return the all completed private events for user id
     */
    public Collection<Event> getAllCompletedPrivateEventsForUserId(DateTime currentTime, Long userId);

    /**
     * Gets the all active admin events for user id.
     *
     * @param currentTime
     *            the current time
     * @param userId
     *            the user id
     * @return the all active admin events for user id
     */
    public Collection<Event> getAllActiveAdminEventsForUserId(DateTime currentTime, Long userId);

    /**
     * Gets the all completed admin events for user id.
     *
     * @param currentTime
     *            the current time
     * @param userId
     *            the user id
     * @return the all completed admin events for user id
     */
    public Collection<Event> getAllCompletedAdminEventsForUserId(DateTime currentTime, Long userId);

    /**
     * Gets the all future admin events for user id.
     *
     * @param currentTime
     *            the current time
     * @param userId
     *            the user id
     * @return the all future admin events for user id
     */
    public Collection<Event> getAllFutureAdminEventsForUserId(DateTime currentTime, Long userId);

}

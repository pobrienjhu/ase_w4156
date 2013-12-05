package edu.columbia.ase.teamproject.persistence.dao.impl;

import java.util.Collection;

import org.joda.time.DateTime;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.columbia.ase.teamproject.persistence.dao.EventDao;
import edu.columbia.ase.teamproject.persistence.dao.generic.HibernateDao;
import edu.columbia.ase.teamproject.persistence.domain.Event;
import edu.columbia.ase.teamproject.persistence.domain.enumeration.EventType;

// TODO: Auto-generated Javadoc
/**
 * The Class EventDaoImpl.
 */
@Transactional(propagation = Propagation.REQUIRED)
public class EventDaoImpl extends HibernateDao<Event, Long> implements EventDao {

    /*
     * (non-Javadoc)
     *
     * @see edu.columbia.ase.teamproject.persistence.dao.EventDao#
     * getAllActivePublicEvents(org.joda.time.DateTime)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Collection<Event> getAllActivePublicEvents(final DateTime currentTime) {

        return currentSession()
                .createQuery(
                        "From Event e " + "Where e.eventType = :et "
                                + "and :ct between e.startTime and e.endTime  ")
                .setParameter("et", EventType.PUBLIC).setParameter("ct", currentTime).list();
    }

    /*
     * (non-Javadoc)
     *
     * @see edu.columbia.ase.teamproject.persistence.dao.EventDao#
     * getAllActivePrivateEventsForUserId(org.joda.time.DateTime,
     * java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Collection<Event> getAllActivePrivateEventsForUserId(final DateTime currentTime,
            final Long userId) {

        return currentSession()
                .createQuery(
                        "Select e " + "From Event e " + "inner join e.eventUsers eventUsers "
                                + "Where e.eventType = :et " + "and eventUsers.id = :uId "
                                + "and :ct between e.startTime and e.endTime ")
                .setParameter("uId", userId).setParameter("et", EventType.PRIVATE)
                .setParameter("ct", currentTime).list();

    }

    /*
     * (non-Javadoc)
     *
     * @see edu.columbia.ase.teamproject.persistence.dao.EventDao#
     * getAllActiveAdminEventsForUserId(org.joda.time.DateTime, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Collection<Event> getAllActiveAdminEventsForUserId(final DateTime currentTime,
            final Long userId) {

        return currentSession()
                .createQuery(
                        "Select e " + "From Event e " + "inner join e.adminUsers adminUsers "
                                + "Where adminUsers.id = :uId "
                                + "and :ct between e.startTime and e.endTime ")
                .setParameter("uId", userId).setParameter("ct", currentTime).list();

    }

    /*
     * (non-Javadoc)
     *
     * @see edu.columbia.ase.teamproject.persistence.dao.EventDao#
     * getAllCompletedPublicEvents(org.joda.time.DateTime)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Collection<Event> getAllCompletedPublicEvents(final DateTime currentTime) {
        return currentSession()
                .createQuery("From Event e " + "Where e.eventType = :et " + "and :ct > e.endTime  ")
                .setParameter("et", EventType.PUBLIC).setParameter("ct", currentTime).list();
    }

    /*
     * (non-Javadoc)
     *
     * @see edu.columbia.ase.teamproject.persistence.dao.EventDao#
     * getAllCompletedPrivateEventsForUserId(org.joda.time.DateTime,
     * java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Collection<Event> getAllCompletedPrivateEventsForUserId(final DateTime currentTime,
            final Long userId) {
        return currentSession()
                .createQuery(
                        "Select e " + "From Event e " + "inner join e.eventUsers eventUsers "
                                + "Where e.eventType = :et " + "and eventUsers.id = :uId "
                                + "and :ct > e.endTime ").setParameter("uId", userId)
                .setParameter("et", EventType.PRIVATE).setParameter("ct", currentTime).list();
    }

    /*
     * (non-Javadoc)
     *
     * @see edu.columbia.ase.teamproject.persistence.dao.EventDao#
     * getAllCompletedAdminEventsForUserId(org.joda.time.DateTime,
     * java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Collection<Event> getAllCompletedAdminEventsForUserId(final DateTime currentTime,
            final Long userId) {
        return currentSession()
                .createQuery(
                        "Select e " + "From Event e " + "inner join e.adminUsers adminUsers "
                                + "Where adminUsers.id = :uId " + "and :ct > e.endTime ")
                .setParameter("uId", userId).setParameter("ct", currentTime).list();
    }

    /*
     * (non-Javadoc)
     *
     * @see edu.columbia.ase.teamproject.persistence.dao.EventDao#
     * getAllFutureAdminEventsForUserId(org.joda.time.DateTime, java.lang.Long)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Collection<Event> getAllFutureAdminEventsForUserId(final DateTime currentTime,
            final Long userId) {
        return currentSession()
                .createQuery(
                        "Select e " + "From Event e " + "inner join e.adminUsers adminUsers "
                                + "Where adminUsers.id = :uId " + "and :ct < e.startTime ")
                .setParameter("uId", userId).setParameter("ct", currentTime).list();
    }

}

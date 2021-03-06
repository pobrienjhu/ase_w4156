package edu.columbia.ase.teamproject.services;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import edu.columbia.ase.teamproject.persistence.domain.Event;
import edu.columbia.ase.teamproject.persistence.domain.VoteCategory;
import edu.columbia.ase.teamproject.services.exceptions.ValidationException;

// TODO: Auto-generated Javadoc
/**
 * The Class EventValidationHelper.
 */
public class EventValidationHelper {

    /**
     * Validate event update.
     *
     * @param event
     *            the event
     * @throws ValidationException
     *             the validation exception
     */
    public static void validateEventUpdate(final Event event) throws ValidationException {
        final ValidationException validationException = new ValidationException();

        try {
            validateEventCreation(event);
        } catch (final ValidationException e) {
            validationException.addAllMessages(e.getErrorMessages());
        }

        if (event.getStartTime() != null && DateTime.now().isAfter(event.getStartTime())) {
            validationException.addMessage("Can not update event after start time");
        }

        if (validationException.hasErrorMessages()) {
            throw validationException;
        }

    }

    /**
     * Validate event creation.
     *
     * @param event
     *            the event
     * @throws ValidationException
     *             the validation exception
     */
    public static void validateEventCreation(final Event event) throws ValidationException {

        if (event == null) {
            return;
        }

        final ValidationException validationException = new ValidationException();

        if (StringUtils.isEmpty(StringUtils.trimToEmpty(event.getName()))) {
            validationException.addMessage("Event must have a name.");
        }

        if (event.getEventType() == null) {
            validationException.addMessage("EventType can not be empty.");
        }

        if (event.getStartTime() == null) {
            validationException.addMessage("Start date must be supplied.");
        }

        if (event.getEndTime() == null) {
            validationException.addMessage("End date must be supplied.");
        }

        if (event.getStartTime() != null && event.getEndTime() != null
                && (!event.getStartTime().isBefore(event.getEndTime()))) {
            validationException.addMessage("Start date must be before end date.");
        }

        if (event.getVoteCategories() == null || event.getVoteCategories().isEmpty()) {
            validationException.addMessage("At least one voting category must be supplied.");
        } else {

            for (final VoteCategory category : event.getVoteCategories()) {
                try {
                    validateVoteCategory(category);
                } catch (final ValidationException e) {
                    validationException.addAllMessages(e.getErrorMessages());
                }
            }

        }

        if (validationException.hasErrorMessages()) {
            throw validationException;
        }

    }

    /**
     * Validate vote category.
     *
     * @param category
     *            the category
     * @throws ValidationException
     *             the validation exception
     */
    public static void validateVoteCategory(final VoteCategory category) throws ValidationException {

        final ValidationException validationException = new ValidationException();

        if (category == null) {
            return;
        }

        if (StringUtils.isEmpty(StringUtils.trimToEmpty(category.getCategoryName()))) {
            validationException.addMessage("Vote Category must have a name.");
        }

        if (category.getVoteOptions() == null || category.getVoteOptions().size() < 2) {
            validationException.addMessage("Vote Category must have at least two options.");
        }

        if (validationException.hasErrorMessages()) {
            throw validationException;
        }
    }

}

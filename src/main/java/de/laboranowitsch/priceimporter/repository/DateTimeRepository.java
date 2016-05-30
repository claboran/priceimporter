package de.laboranowitsch.priceimporter.repository;

import de.laboranowitsch.priceimporter.domain.DateTime;

/**
 * DateTimeRepository for accessing {@link de.laboranowitsch.priceimporter.domain.DateTime}
 *
 * @author christian@laboranowitsch.de
 */
public interface DateTimeRepository {

    /**
     * Find a dateTime entity by dateTime object.
     * @param dateTime
     * @return DateTime
     */
    DateTime findByDateTime(DateTime dateTime);

    /**
     * Saves a new dateTime.
     * @param dateTime
     * @return id
     */
    Long saveNew(DateTime dateTime);

    /**
     * Saves a new dateTime object or retrieves the id.
     * @param dateTime
     * @return id
     */
    Long save(DateTime dateTime);
}

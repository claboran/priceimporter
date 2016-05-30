package de.laboranowitsch.priceimporter.repository;

import de.laboranowitsch.priceimporter.domain.Period;

/**
 * Period Repository for accessing {@link de.laboranowitsch.priceimporter.domain.Period}.
 *
 * @author christian@laboranowitsch.de
 */
public interface PeriodRepository {

    /**
     * Find a period entity by period type.
     * @param period
     * @return Period
     */
    Period findByPeriod(String period);

    /**
     * Saves a new period.
     * @param period
     * @return id
     */
    Long saveNew(Period period);

    /**
     * Saves a new period or retrieves the id.
     * @param period
     * @return id
     */
    Long save(Period period);
}

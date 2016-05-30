package de.laboranowitsch.priceimporter.repository;

import de.laboranowitsch.priceimporter.domain.FactData;

/**
 * FactDataRepository for accessing {@link de.laboranowitsch.priceimporter.domain.FactData}.
 *
 * @author christian@laboranowitsch.de
 */
public interface FactDataRepository {

    /**
     * Find a factData entity by foreign key relationships.
     * @param factData
     * @return FactData
     */
    FactData findByFactData(FactData factData);

    /**
     * Saves a new factData entity.
     * @param factData
     * @return id
     */
    Long saveNew(FactData factData);

    /**
     * Saves a new factData entity or retrieves the id.
     * @param factData
     * @return id
     */
    Long save(FactData factData);
}

package de.laboranowitsch.priceimporter.repository;

import de.laboranowitsch.priceimporter.domain.Region;

/**
 * RegionRepository for accessing {@link de.laboranowitsch.priceimporter.domain.Region}.
 *
 * @author christian@laboranowitsch.de
 */
public interface RegionRepository {

    /**
     * Find a region entity by region name.
     * @param region
     * @return Region
     */
    Region findByRegion(String region);

    /**
     * Saves a new region.
     * @param region
     * @return id
     */
    Long saveNew(Region region);

    /**
     * Saves a new region or retrieves the id.
     * @param region
     * @return id
     */
    Long save(Region region);
}

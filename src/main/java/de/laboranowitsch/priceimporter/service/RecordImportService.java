package de.laboranowitsch.priceimporter.service;

import de.laboranowitsch.priceimporter.domain.CompositeRecord;

/**
 * Service encapsulates the contract for saving dimensions
 * {@link de.laboranowitsch.priceimporter.domain.DateTime}, {@link de.laboranowitsch.priceimporter.domain.Period},
 * {@link de.laboranowitsch.priceimporter.domain.Region}
 * and facts {@link de.laboranowitsch.priceimporter.domain.FactData}.
 *
 * Created by cla on 5/1/16.
 */
public interface RecordImportService {

    /**
     * Imports a complete record
     *
     * @param compositeRecord
     */
    void importRecord(CompositeRecord compositeRecord);
}

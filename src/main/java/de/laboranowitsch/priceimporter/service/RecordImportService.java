package de.laboranowitsch.priceimporter.service;

import de.laboranowitsch.priceimporter.domain.CompositeRecord;

/**
 * Service encapsulates the contract for saving dimensions
 * {@link de.laboranowitsch.priceimporter.domain.DateTime}, {@link de.laboranowitsch.priceimporter.domain.Period},
 * {@link de.laboranowitsch.priceimporter.domain.Region}
 * and facts {@link de.laboranowitsch.priceimporter.domain.FactData}.
 *
 * @author christian@laboranowitsch.de
 */
public interface RecordImportService {

    /**
     * Imports a complete record. {@link CompositeRecord} . See {@link de.laboranowitsch.priceimporter.BatchConfiguration}.
     *
     * @param compositeRecord
     */
    void importRecord(CompositeRecord compositeRecord);
}

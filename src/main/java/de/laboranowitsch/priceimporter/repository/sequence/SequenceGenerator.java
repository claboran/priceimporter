package de.laboranowitsch.priceimporter.repository.sequence;

/**
 * SequenceIdGenerator for generating database sequences.
 *
 * Created by cla on 4/8/16.
 */
public interface SequenceGenerator {

    /**
     * Creates the next sequence for a given sequence name
     *
     * @param sequenceName
     * @return next id
     */
    Long getNextSequence(String sequenceName);
}

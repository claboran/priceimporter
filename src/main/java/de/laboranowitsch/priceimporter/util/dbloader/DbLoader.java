package de.laboranowitsch.priceimporter.util.dbloader;

import java.sql.SQLException;

/**
 * DBLoader keeps track of the different loading strategy for H2 and Hana.
 *
 * Created by cla on 4/10/16.
 */
public interface DbLoader {

    /**
     * prepares the database
     */
    void prepareDatabase();
}

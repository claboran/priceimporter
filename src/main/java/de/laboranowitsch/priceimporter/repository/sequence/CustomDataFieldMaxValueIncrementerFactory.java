package de.laboranowitsch.priceimporter.repository.sequence;

import org.springframework.batch.item.database.support.DataFieldMaxValueIncrementerFactory;
import org.springframework.jdbc.support.incrementer.DataFieldMaxValueIncrementer;
import org.springframework.jdbc.support.incrementer.H2SequenceMaxValueIncrementer;

import javax.sql.DataSource;

/**
 * Implements a custom factory for {@link DataFieldMaxValueIncrementerFactory}
 * We are using H2 and Hana here. If you need other types as well you need to add it.
 * Tweak of {@link org.springframework.batch.item.database.support.DefaultDataFieldMaxValueIncrementerFactory}.
 *
 * @author christian@laboranowitsch.de
 */
public class CustomDataFieldMaxValueIncrementerFactory implements DataFieldMaxValueIncrementerFactory {

    private final DataSource dataSource;

    private String incrementerColumnName = "ID";
    private String[] supportedTypes = {"H2", "HDB"};

    public void setIncrementerColumnName(String incrementerColumnName) {
        this.incrementerColumnName = incrementerColumnName;
    }

    public CustomDataFieldMaxValueIncrementerFactory(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Creates the requested {@link DataFieldMaxValueIncrementer}.
     *
     * @param databaseType
     * @param incrementerName
     * @return {@link DataFieldMaxValueIncrementer}
     */
    @Override
    public DataFieldMaxValueIncrementer getIncrementer(String databaseType, String incrementerName) {
        if (databaseType == "H2") {
            return new H2SequenceMaxValueIncrementer(dataSource, incrementerName);
        } else if(databaseType == "HDB") {
            return new HdbSequenceMaxValueIncrementer(dataSource, incrementerName);
        }
        throw new IllegalArgumentException("databaseType argument was not on the approved list");
    }

    /**
     * We have only two types implemented here H2 and SAP Hana.
     *
     * @param databaseType
     * @return true if the database is supported, else false
     */
    @Override
    public boolean isSupportedIncrementerType(String databaseType) {
        if((databaseType == "H2") || (databaseType == "HDB")) {
            return true;
        }
        return false;
    }

    @Override
    public String[] getSupportedIncrementerTypes() {
        return supportedTypes;
    }
}

package de.laboranowitsch.priceimporter.repository.sequence;

import org.springframework.jdbc.support.incrementer.AbstractSequenceMaxValueIncrementer;

import javax.sql.DataSource;

/**
 * Spring Batch incrementer for the Hana database.
 *
 * Created by cla on 4/23/16.
 */
public class HdbSequenceMaxValueIncrementer extends AbstractSequenceMaxValueIncrementer {

    public HdbSequenceMaxValueIncrementer() {}

    public HdbSequenceMaxValueIncrementer(DataSource dataSource, String incrementerName) {
        super(dataSource, incrementerName);
    }

    @Override
    protected String getSequenceQuery() {
        return "select " + getIncrementerName() + ".nextval from dummy";
    }
}

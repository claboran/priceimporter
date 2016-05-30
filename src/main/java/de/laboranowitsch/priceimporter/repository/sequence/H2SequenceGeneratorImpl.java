package de.laboranowitsch.priceimporter.repository.sequence;

import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * H2 specific {@link SequenceGenerator} implementation used for the Repositories.
 *
 * @author christian@laboranowitsch.de
 */
public class H2SequenceGeneratorImpl implements SequenceGenerator {

    private final static String SEQ_STMT = "select SEQ_NAME.nextval from dual";
    private final JdbcTemplate jdbcTemplate;

    public H2SequenceGeneratorImpl(final DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Long getNextSequence(String sequenceName) {
        String stmt = SEQ_STMT.replace("SEQ_NAME", sequenceName);
        return jdbcTemplate.queryForObject(stmt, Long.class);
    }
}

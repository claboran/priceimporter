package de.laboranowitsch.priceimporter.repository;

import de.laboranowitsch.priceimporter.domain.Period;
import de.laboranowitsch.priceimporter.repository.sequence.SequenceGenerator;
import de.laboranowitsch.priceimporter.util.ActualParameterHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

/**
 * Repository implementing {@link PeriodRepository} for dealing with {@link Period}s.
 *
 * @author christian@laboranowitsch.de
 */
@Component
public class PeriodRepositoryImpl implements PeriodRepository{

    private static final String PERIOD_PARAM = "period_type";
    private static final String ID_PARAM = "id";
    private static final String PERIOD_QUERY = "select * from d_period_type where period_type = " + ActualParameterHelper.makeActualParameterOf(PERIOD_PARAM);
    private static final String INSERT_STMT = "insert into d_period_type (" + ID_PARAM + ", " + PERIOD_PARAM + ") values("
            + ActualParameterHelper.makeActualParameterOf(ID_PARAM) + ", " + ActualParameterHelper.makeActualParameterOf(PERIOD_PARAM) + ")";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SequenceGenerator sequenceGenerator;
    private final String sequenceName;
    private final String periodQuery;
    private final String insertStmt;

    @Autowired
    public PeriodRepositoryImpl(final DataSource dataSource,
                                final SequenceGenerator sequenceGenerator,
                                @Value("${priceimporter.table.name.d_period_type}") final String periodTypeTableName,
                                @Value("${priceimporter.seq.name.d_period_type}") final String sequenceName) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.sequenceGenerator = sequenceGenerator;
        periodQuery = PERIOD_QUERY.replace("d_period_type", periodTypeTableName);
        insertStmt = INSERT_STMT.replace("d_period_type", periodTypeTableName);
        this.sequenceName = sequenceName;
    }

    @Override
    public Period findByPeriod(String period) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(PERIOD_PARAM, period);

        List<Period> result = jdbcTemplate.query(periodQuery, sqlParameterSource,
                ((rs, rowNum) -> Period.builder().id(rs.getLong(1)).period(rs.getString(2)).build()));

        if(result.size() > 1) {
            throw new RuntimeException("Unexpected number of Periods found: " + result.size() + " only one is expected.");
        }
        if(result.size() == 0) {
            return null;
        }
        return result.get(0);
    }

    @Override
    public Long saveNew(Period period) {
        Long nextVal = sequenceGenerator.getNextSequence(sequenceName);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(ID_PARAM, nextVal);
        mapSqlParameterSource.addValue(PERIOD_PARAM, period.getPeriod());
        jdbcTemplate.update(insertStmt, mapSqlParameterSource);
        return nextVal;
    }

    @Override
    public Long save(Period period) {
        Period result = findByPeriod(period.getPeriod());
        if(result == null) {
            return saveNew(period);
        }
        return result.getId();
    }
}

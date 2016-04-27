package de.laboranowitsch.priceimporter.repository;

import de.laboranowitsch.priceimporter.domain.DateTime;
import de.laboranowitsch.priceimporter.repository.sequence.SequenceGenerator;
import de.laboranowitsch.priceimporter.util.ActualParameterHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

/**
 * Repository implementing {@link DateTimeRepository} for dealing with {@link DateTime}s.
 *
 * Created by cla on 4/24/16.
 */
@Component
public class DateTimeRepositoryImpl implements DateTimeRepository {

    private static final Logger LOG = LoggerFactory.getLogger(DateTimeRepositoryImpl.class);

    private static final String ID_PARAM = "id";
    private static final String YEAR_PARAM = "the_year";
    private static final String MONTH_OF_YEAR_PARAM = "month_of_year";
    private static final String DAY_OF_MONTH_PARAM = "day_of_month";
    private static final String HOUR_OF_DAY_PARAM = "hour_of_day";
    private static final String MINUTES_OF_HOUR_PARAM = "minutes_of_hour";

    private static final String DATE_TIME_QUERY = "select * from d_date_time where ";
    private static final String INSERT_STMT = "insert into d_date_time (";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SequenceGenerator sequenceGenerator;
    private final String dateTimeQuery;
    private final String insertStmt;
    private final String sequenceName;

    @Autowired
    public DateTimeRepositoryImpl(final DataSource dataSource,
                                  final SequenceGenerator sequenceGenerator,
                                  @Value("${priceimporter.table.name.d_date_time}") final String dateTimeTableName,
                                  @Value("${priceimporter.seq.name.d_date_time}") final String sequenceName) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.sequenceGenerator = sequenceGenerator;
        this.dateTimeQuery = createDateTimeQuery().replace("d_date_time", dateTimeTableName);
        this.insertStmt = createDateTimeInsertStmt().replace("d_date_time", dateTimeTableName);
        this.sequenceName = sequenceName;
        LOG.info("DateTime query: {}", this.dateTimeQuery);
        LOG.info("DateTime insert statement: {}", this.insertStmt);
    }

    @Override
    public DateTime findByDateTime(DateTime dateTime) {
        SqlParameterSource sqlParameterSource = createQueryParameterMap(dateTime);
        LOG.info("SqlParameterMap: {}", sqlParameterSource);
        List<DateTime> result = jdbcTemplate.query(dateTimeQuery, sqlParameterSource,
                ((rs, rowNum) -> DateTime.builder().id(rs.getLong(1))
                        .dayOfMonth(rs.getInt(2))
                        .monthOfYear(rs.getInt(3))
                        .year(rs.getInt(4))
                        .hourOfDay(rs.getInt(5))
                        .minuteOfHour(rs.getInt(6))
                        .build()));

        if(result.size() > 1) {
            throw new RuntimeException("Unexpected number of DayTime entries found: " + result.size() + " only one is expected.");
        }
        if(result.size() == 0) {
            return null;
        }
        return result.get(0);
    }

    @Override
    public Long saveNew(DateTime dateTime) {
        Long nextVal = sequenceGenerator.getNextSequence(sequenceName);
        dateTime.setId(nextVal);
        MapSqlParameterSource mapSqlParameterSource = createInsertStmtParameterMap(dateTime);
        jdbcTemplate.update(insertStmt, mapSqlParameterSource);
        return nextVal;
    }

    @Override
    public Long save(DateTime dateTime) {
        DateTime result = findByDateTime(dateTime);
        if(result == null) {
            return saveNew(dateTime);
        }
        return result.getId();
    }

    private MapSqlParameterSource createQueryParameterMap(DateTime dateTime) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(DAY_OF_MONTH_PARAM, dateTime.getDayOfMonth());
        sqlParameterSource.addValue(MONTH_OF_YEAR_PARAM, dateTime.getMonthOfYear());
        sqlParameterSource.addValue(YEAR_PARAM, dateTime.getYear());
        sqlParameterSource.addValue(HOUR_OF_DAY_PARAM, dateTime.getHourOfDay());
        sqlParameterSource.addValue(MINUTES_OF_HOUR_PARAM, dateTime.getMinuteOfHour());
        return sqlParameterSource;
    }

    private MapSqlParameterSource createInsertStmtParameterMap(DateTime dateTime) {
        MapSqlParameterSource sqlParameterSource = createQueryParameterMap(dateTime);
        sqlParameterSource.addValue(ID_PARAM, dateTime.getId());
        return sqlParameterSource;
    }

    private String createDateTimeQuery() {
        StringBuilder stmt = new StringBuilder(DATE_TIME_QUERY);
        return stmt.append("day_of_month = ")
                .append(ActualParameterHelper.makeActualParameterOf(DAY_OF_MONTH_PARAM))
                .append(" and month_of_year = ")
                .append(ActualParameterHelper.makeActualParameterOf(MONTH_OF_YEAR_PARAM))
                .append(" and the_year = ")
                .append(ActualParameterHelper.makeActualParameterOf(YEAR_PARAM))
                .append(" and hour_of_day = ")
                .append(ActualParameterHelper.makeActualParameterOf(HOUR_OF_DAY_PARAM))
                .append(" and minutes_of_hour = ")
                .append(ActualParameterHelper.makeActualParameterOf(MINUTES_OF_HOUR_PARAM))
                .toString();
    }

    private String createDateTimeInsertStmt() {
        StringBuilder stmt = new StringBuilder(INSERT_STMT);
        return stmt.append(ID_PARAM + ", ")
                .append(DAY_OF_MONTH_PARAM + ", ")
                .append(MONTH_OF_YEAR_PARAM + ", ")
                .append(YEAR_PARAM + ", ")
                .append(HOUR_OF_DAY_PARAM + ", ")
                .append(MINUTES_OF_HOUR_PARAM)
                .append(") values(")
                .append(ActualParameterHelper.makeActualParameterOf(ID_PARAM) + ", ")
                .append(ActualParameterHelper.makeActualParameterOf(DAY_OF_MONTH_PARAM) + ", ")
                .append(ActualParameterHelper.makeActualParameterOf(MONTH_OF_YEAR_PARAM) + ", ")
                .append(ActualParameterHelper.makeActualParameterOf(YEAR_PARAM) + ", ")
                .append(ActualParameterHelper.makeActualParameterOf(HOUR_OF_DAY_PARAM) + ", ")
                .append(ActualParameterHelper.makeActualParameterOf(MINUTES_OF_HOUR_PARAM))
                .append(")")
                .toString();
    }
}

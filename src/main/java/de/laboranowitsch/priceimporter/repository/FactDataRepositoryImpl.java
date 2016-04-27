package de.laboranowitsch.priceimporter.repository;

import de.laboranowitsch.priceimporter.domain.FactData;
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
 * Repository implementing {@link FactDataRepository} for dealing with {@link FactData}s.
 *
 * Created by cla on 4/24/16.
 */
@Component
public class FactDataRepositoryImpl implements FactDataRepository {

    private static final Logger LOG = LoggerFactory.getLogger(FactDataRepositoryImpl.class);

    private static final String ID_PARAM = "id";
    private static final String TOTAL_DEMAND_PARAM = "total_demand";
    private static final String RPR_PARAM = "rpr";
    private static final String D_REGION_ID_PARAM = "d_region_id";
    private static final String D_PERIOD_ID_PARAM = "d_period_type_id";
    private static final String D_DATE_TIME_ID_PARAM = "d_date_time_id";

    private static final String FACT_DATA_QUERY = "select * from f_energy_price_demand ";
    private static final String INSERT_STMT = "insert into f_energy_price_demand (";

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SequenceGenerator sequenceGenerator;
    private final String factDataQuery;
    private final String insertStmt;
    private final String sequenceName;

    @Autowired
    public FactDataRepositoryImpl(final DataSource dataSource,
                                  final SequenceGenerator sequenceGenerator,
                                  @Value("${priceimporter.table.name.f_energy_price_demand}") final String factDataTableName,
                                  @Value("${priceimporter.seq.name.f_energy_price_demand}") final String sequenceName) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.sequenceGenerator = sequenceGenerator;
        this.factDataQuery = createFactDataQuery().replace("f_energy_price_demand", factDataTableName);
        this.insertStmt = createFactDataInsertStmt().replace("f_energy_price_demand", factDataTableName);
        this.sequenceName = sequenceName;
        LOG.info("FactData query: {}", this.factDataQuery);
        LOG.info("FactData insert statement: {}", this.insertStmt);
    }

    @Override
    public FactData findByFactData(FactData factData) {
        SqlParameterSource sqlParameterSource = createQueryParameterMap(factData);
        LOG.info("SqlParameterMap: {}", sqlParameterSource);
        List<FactData> result = jdbcTemplate.query(factDataQuery, sqlParameterSource,
                ((rs, rowNum) -> FactData.builder().id(rs.getLong(1))
                        .totalDemand(rs.getDouble(2))
                        .rpr(rs.getDouble(3))
                        .regionId(rs.getLong(4))
                        .periodId(rs.getLong(5))
                        .dateTimeId(rs.getLong(6))
                        .build()));

        if(result.size() > 1) {
            throw new RuntimeException("Unexpected number of FactData entries found: " + result.size() + " only one is expected.");
        }
        if(result.size() == 0) {
            return null;
        }
        return result.get(0);
    }



    @Override
    public Long saveNew(FactData factData) {
        Long nextVal = sequenceGenerator.getNextSequence(sequenceName);
        factData.setId(nextVal);
        MapSqlParameterSource mapSqlParameterSource = createInsertStmtParameterMap(factData);
        jdbcTemplate.update(insertStmt, mapSqlParameterSource);
        return nextVal;
    }

    @Override
    public Long save(FactData factData) {
        FactData result = findByFactData(factData);
        if(result == null) {
            return saveNew(factData);
        }
        return result.getId();
    }

    private MapSqlParameterSource createQueryParameterMap(FactData factData) {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(D_REGION_ID_PARAM, factData.getRegionId());
        sqlParameterSource.addValue(D_PERIOD_ID_PARAM, factData.getPeriodId());
        sqlParameterSource.addValue(D_DATE_TIME_ID_PARAM, factData.getDateTimeId());
        return sqlParameterSource;
    }

    private MapSqlParameterSource createInsertStmtParameterMap(FactData factData) {
        MapSqlParameterSource sqlParameterSource = createQueryParameterMap(factData);
        sqlParameterSource.addValue(ID_PARAM, factData.getId());
        sqlParameterSource.addValue(TOTAL_DEMAND_PARAM, factData.getTotalDemand());
        sqlParameterSource.addValue(RPR_PARAM, factData.getRpr());
        return sqlParameterSource;
    }

    private String createFactDataQuery() {
        StringBuilder stmt = new StringBuilder(FACT_DATA_QUERY);
        return stmt.append(D_REGION_ID_PARAM + " = ")
                .append(ActualParameterHelper.makeActualParameterOf(D_REGION_ID_PARAM))
                .append(" and " + D_PERIOD_ID_PARAM + " = ")
                .append(ActualParameterHelper.makeActualParameterOf(D_PERIOD_ID_PARAM))
                .append(" and " + D_DATE_TIME_ID_PARAM + " = ")
                .append(ActualParameterHelper.makeActualParameterOf(D_DATE_TIME_ID_PARAM))
                .toString();
    }

    private String createFactDataInsertStmt() {
        StringBuilder stmt = new StringBuilder(INSERT_STMT);
        return stmt.append(ID_PARAM + ", ")
                .append(TOTAL_DEMAND_PARAM + ", ")
                .append(RPR_PARAM + ", ")
                .append(D_REGION_ID_PARAM + ", ")
                .append(D_PERIOD_ID_PARAM + ", ")
                .append(D_DATE_TIME_ID_PARAM)
                .append(") values(")
                .append(ActualParameterHelper.makeActualParameterOf(ID_PARAM) + ", ")
                .append(ActualParameterHelper.makeActualParameterOf(TOTAL_DEMAND_PARAM) + ", ")
                .append(ActualParameterHelper.makeActualParameterOf(RPR_PARAM) + ", ")
                .append(ActualParameterHelper.makeActualParameterOf(D_REGION_ID_PARAM) + ", ")
                .append(ActualParameterHelper.makeActualParameterOf(D_PERIOD_ID_PARAM) + ", ")
                .append(ActualParameterHelper.makeActualParameterOf(D_DATE_TIME_ID_PARAM))
                .append(")")
                .toString();
    }

}

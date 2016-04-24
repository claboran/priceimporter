package de.laboranowitsch.priceimporter.repository;

import de.laboranowitsch.priceimporter.domain.Region;
import de.laboranowitsch.priceimporter.repository.sequence.SequenceGenerator;
import de.laboranowitsch.priceimporter.util.ActualParameterHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.*;

/**
 * Repository implementing {@link RegionRepository} for dealing with {@link Region}s.
 *
 * Created by cla on 4/8/16.
 */
@Component
public class RegionRepositoryImpl implements RegionRepository {

    private static final String REGION_PARAM = "region";
    private static final String ID_PARAM = "id";
    private static final String REGION_QUERY = "select * from d_region where region = " + ActualParameterHelper.makeActualParameterOf(REGION_PARAM);
    private static final String INSERT_STMT = "insert into d_region (" + ID_PARAM + ", " + REGION_PARAM + ") values("
            + ActualParameterHelper.makeActualParameterOf(ID_PARAM) + ", " + ActualParameterHelper.makeActualParameterOf(REGION_PARAM) + ")";


    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SequenceGenerator sequenceGenerator;
    private final String sequenceName;
    private String regionQuery;
    private String insertStmt;

    @Autowired
    public RegionRepositoryImpl(final DataSource dataSource,
                                final SequenceGenerator sequenceGenerator,
                                @Value("${priceimporter.table.name.d_region}") final String regionTableName,
                                @Value("${priceimporter.seq.name.d_region}") final String sequenceName) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.sequenceGenerator = sequenceGenerator;
        regionQuery = REGION_QUERY.replace("d_region", regionTableName);
        insertStmt = INSERT_STMT.replace("d_region", regionTableName);
        this.sequenceName = sequenceName;
    }

    @Override
    public Region findByRegion(String region) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(REGION_PARAM, region);

        List<Region> result = jdbcTemplate.query(regionQuery, sqlParameterSource,
                ((rs, rowNum) -> Region.builder().id(rs.getLong(1)).region(rs.getString(2)).build()));

        if(result.size() > 1) {
            throw new RuntimeException("Unexpected number of Regions found: " + result.size() + " only one is expected.");
        }
        if(result.size() == 0) {
            return null;
        }
        return result.get(0);
    }

    @Override
    public Long saveNew(Region region) {
        Long nextVal = sequenceGenerator.getNextSequence(sequenceName);
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(ID_PARAM, nextVal);
        mapSqlParameterSource.addValue(REGION_PARAM, region.getRegion());
        jdbcTemplate.update(insertStmt, mapSqlParameterSource);
        return nextVal;
    }

    @Override
    public Long save(Region region) {
        Region result = findByRegion(region.getRegion());
        if(result == null) {
           return saveNew(region);
        }
        return result.getId();
    }
}

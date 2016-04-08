package de.laboranowitsch.priceimporter.repository;

import de.laboranowitsch.priceimporter.domain.Region;
import de.laboranowitsch.priceimporter.util.ActualParameterHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.util.*;

/**
 * Repository implementing {@link RegionRepository} for dealing with {@link Region}s
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

    @Autowired
    public RegionRepositoryImpl(final DataSource dataSource, final SequenceGenerator sequenceGenerator) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.sequenceGenerator = sequenceGenerator;
    }
    @Override
    public Region findByRegion(String region) {
        SqlParameterSource sqlParameterSource = new MapSqlParameterSource(REGION_PARAM, region);

        List<Region> result = jdbcTemplate.query(REGION_QUERY, sqlParameterSource,
                ((rs, rowNum) -> Region.builder().id(rs.getLong(1)).region(rs.getString(2)).build()));

        if(result.size() > 1) {
            throw new RuntimeException("Unexpected number of Regions found: " + result.size() + " only one is expexted.");
        }
        if(result.size() == 0) {
            return null;
        }
        return result.get(0);
    }

    @Override
    public Long saveNew(Region region) {
        Long nextVal = sequenceGenerator.getNextSequence("region_seq");
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(ID_PARAM, nextVal);
        mapSqlParameterSource.addValue(REGION_PARAM, region.getRegion());
        jdbcTemplate.update(INSERT_STMT, mapSqlParameterSource);
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

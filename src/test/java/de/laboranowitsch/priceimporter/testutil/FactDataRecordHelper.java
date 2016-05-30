package de.laboranowitsch.priceimporter.testutil;

import de.laboranowitsch.priceimporter.domain.FactData;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

/**
 * Reads {@link de.laboranowitsch.priceimporter.domain.FactData} for test purpose.
 *
 * @author christian@laboranowitsch.de
 */
public class FactDataRecordHelper {

    /**
     * Reads all entries of {@link FactData}
     *
     * @param dataSource
     * @return List of FactData
     */
    public static  List<FactData> getFactData(DataSource dataSource) {
        NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        return jdbcTemplate.query("select * from int_test_f_energy_price_demand", ((rs, rowNum) -> FactData.builder().id(rs.getLong(1))
                .totalDemand(rs.getDouble(2))
                .rpr(rs.getDouble(3))
                .regionId(rs.getLong(4))
                .periodId(rs.getLong(5))
                .dateTimeId(rs.getLong(6))
                .build()));
    }
}

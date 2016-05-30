package de.laboranowitsch.priceimporter.repository;

import de.laboranowitsch.priceimporter.PriceImporterApplication;
import de.laboranowitsch.priceimporter.domain.DateTime;
import de.laboranowitsch.priceimporter.domain.FactData;
import de.laboranowitsch.priceimporter.domain.Period;
import de.laboranowitsch.priceimporter.domain.Region;
import de.laboranowitsch.priceimporter.repository.sequence.SequenceGenerator;
import de.laboranowitsch.priceimporter.util.dbloader.DbLoader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link FactDataRepository} and {@link SequenceGenerator}
 * for {@link de.laboranowitsch.priceimporter.domain.FactData} Sequence.
 *
 * @author christian@laboranowitsch.de
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PriceImporterApplication.class)
public class FactDataRepositoryTests {

    private static final Logger LOG = LoggerFactory.getLogger(FactDataRepositoryTests.class);
    public static final String TRADE = "TRADE";
    public static final String PD = "PD";
    @Autowired
    private DbLoader dbLoader;
    @Autowired
    private FactDataRepository factDataRepository;
    @Autowired
    private SequenceGenerator sequenceGenerator;
    @Autowired
    private DateTimeRepository dateTimeRepository;
    @Autowired
    private PeriodRepository periodRepository;
    @Autowired
    private RegionRepository regionRepository;

    @Before
    public void before() throws SQLException {
        dbLoader.prepareDatabase();
    }
    @Test
    public void testContextLoads() {
        assertThat("wiring of dependencies is done properly", factDataRepository, is(not(nullValue())));
        assertThat("wiring of dependencies is done properly", sequenceGenerator, is(not(nullValue())));
        assertThat("wiring of dependencies is done properly", dateTimeRepository, is(not(nullValue())));
        assertThat("wiring of dependencies is done properly", periodRepository, is(not(nullValue())));
        assertThat("wiring of dependencies is done properly", regionRepository, is(not(nullValue())));
    }

    @Transactional
    @Test
    public void testSequenceGenerator() {
        assertThat("sequence has 1", sequenceGenerator.getNextSequence("int_test_fact_seq"), is(equalTo(1L)));
        assertThat("sequence has 2", sequenceGenerator.getNextSequence("int_test_fact_seq"), is(equalTo(2L)));
    }

    @Transactional
    @Test
    public void testInsertNew() {
        Map<String, Long> dimensionMap = createDimensions(TRADE);
        assertThat("one entry has been inserted", factDataRepository.save(FactData.builder().dateTimeId(dimensionMap.get("dateTimeId"))
                .periodId(dimensionMap.get("periodId"))
                .regionId(dimensionMap.get("regionId"))
                .rpr(23.45)
                .totalDemand(567.89)
                .build()), is(equalTo(1L)));
        FactData factData = factDataRepository.findByFactData(FactData.builder().dateTimeId(dimensionMap.get("dateTimeId"))
                .periodId(dimensionMap.get("periodId"))
                .regionId(dimensionMap.get("regionId"))
                .build());
        assertThat("contains the right totalDemand", factData.getTotalDemand(), is(closeTo(567.89, 0.0001)));
        assertThat("contains the right rpr", factData.getRpr(), is(closeTo(23.45, 0.0001)));
    }

    @Transactional
    @Test
    public void testInsert2New() {
        Map<String, Long> dimensionMap = createDimensions(TRADE);
        assertThat("one entry has been inserted", factDataRepository.save(FactData.builder().dateTimeId(dimensionMap.get("dateTimeId"))
                .periodId(dimensionMap.get("periodId"))
                .regionId(dimensionMap.get("regionId"))
                .rpr(23.45)
                .totalDemand(567.89)
                .build()), is(equalTo(1L)));
        FactData factData = factDataRepository.findByFactData(FactData.builder().dateTimeId(dimensionMap.get("dateTimeId"))
                .periodId(dimensionMap.get("periodId"))
                .regionId(dimensionMap.get("regionId"))
                .build());
        assertThat("contains the right totalDemand", factData.getTotalDemand(), is(closeTo(567.89, 0.0001)));
        assertThat("contains the right rpr", factData.getRpr(), is(closeTo(23.45, 0.0001)));

        dimensionMap = createDimensions(PD);
        assertThat("one entry has been inserted", factDataRepository.save(FactData.builder().dateTimeId(dimensionMap.get("dateTimeId"))
                .periodId(dimensionMap.get("periodId"))
                .regionId(dimensionMap.get("regionId"))
                .rpr(20.12)
                .totalDemand(550.89)
                .build()), is(equalTo(2L)));
        factData = factDataRepository.findByFactData(FactData.builder().dateTimeId(dimensionMap.get("dateTimeId"))
                .periodId(dimensionMap.get("periodId"))
                .regionId(dimensionMap.get("regionId"))
                .build());
        assertThat("contains the right totalDemand", factData.getTotalDemand(), is(closeTo(550.89, 0.0001)));
        assertThat("contains the right rpr", factData.getRpr(), is(closeTo(20.12, 0.0001)));
    }

    @Transactional
    @Test
    public void testUpdate() {
        Map<String, Long> dimensionMap = createDimensions(TRADE);
        assertThat("one entry has been inserted", factDataRepository.save(FactData.builder().dateTimeId(dimensionMap.get("dateTimeId"))
                .periodId(dimensionMap.get("periodId"))
                .regionId(dimensionMap.get("regionId"))
                .rpr(23.45)
                .totalDemand(567.89)
                .build()), is(equalTo(1L)));
        FactData factData = factDataRepository.findByFactData(FactData.builder().dateTimeId(dimensionMap.get("dateTimeId"))
                .periodId(dimensionMap.get("periodId"))
                .regionId(dimensionMap.get("regionId"))
                .build());
        assertThat("contains the right totalDemand", factData.getTotalDemand(), is(closeTo(567.89, 0.0001)));
        assertThat("contains the right rpr", factData.getRpr(), is(closeTo(23.45, 0.0001)));

        assertThat("one entry has been inserted", factDataRepository.save(FactData.builder().dateTimeId(dimensionMap.get("dateTimeId"))
                .periodId(dimensionMap.get("periodId"))
                .regionId(dimensionMap.get("regionId"))
                .rpr(19.45)
                .totalDemand(500.00)
                .build()), is(equalTo(1L)));

        factData = factDataRepository.findByFactData(FactData.builder().dateTimeId(dimensionMap.get("dateTimeId"))
                .periodId(dimensionMap.get("periodId"))
                .regionId(dimensionMap.get("regionId"))
                .build());
        assertThat("contains the right totalDemand", factData.getTotalDemand(), is(closeTo(500.00, 0.0001)));
        assertThat("contains the right rpr", factData.getRpr(), is(closeTo(19.45, 0.0001)));
    }

    private Map<String, Long> createDimensions(String periodType) {
        Map<String, Long> dimensionMap = new HashMap<>();
        Long id = dateTimeRepository.save(DateTime.builder().dayOfMonth(1)
                .monthOfYear(1)
                .year(2016)
                .hourOfDay(00)
                .minuteOfHour(00)
                .build());
        dimensionMap.put("dateTimeId", id);
        id = periodRepository.save(Period.builder().period(periodType).build());
        dimensionMap.put("periodId", id);
        id = regionRepository.save(Region.builder().region("NSW").build());
        dimensionMap.put("regionId", id);
        return dimensionMap;
    }
}

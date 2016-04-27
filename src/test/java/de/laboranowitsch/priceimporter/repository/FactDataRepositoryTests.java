package de.laboranowitsch.priceimporter.repository;

import de.laboranowitsch.priceimporter.PriceImporterApplication;
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

import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

/**
 * Tests for {@link FactDataRepository} and {@link SequenceGenerator}
 * for {@link de.laboranowitsch.priceimporter.domain.FactData} Sequence
 *
 * Created by cla on 4/27/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PriceImporterApplication.class)
public class FactDataRepositoryTests {

    private static final Logger LOG = LoggerFactory.getLogger(FactDataRepositoryTests.class);
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

}

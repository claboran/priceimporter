package de.laboranowitsch.priceimporter.repository;

import de.laboranowitsch.priceimporter.PriceImporterApplication;
import de.laboranowitsch.priceimporter.domain.Region;
import de.laboranowitsch.priceimporter.repository.sequence.SequenceGenerator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link RegionRepository} and {@link SequenceGenerator}
 * for {@link de.laboranowitsch.priceimporter.domain.Region} Sequence
 *
 * Created by cla on 4/8/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PriceImporterApplication.class)
@SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:sql/create-tables-int-h2.sql"})
})
public class RegionRepositoryTests {

    private static final Logger LOG = LoggerFactory.getLogger(RegionRepositoryTests.class);

    @Autowired
    private RegionRepository regionRepository;
    @Autowired
    private SequenceGenerator sequenceGenerator;

    @Test
    public void testContextLoads() {
        assertThat("wiring of dependencies is done properly", regionRepository, is(not(nullValue())));
        assertThat("wiring of dependencies is done properly", sequenceGenerator, is(not(nullValue())));
    }
    @Transactional
    @Test
    public void testSequenceGenerator() {
        assertThat("sequence has 1", sequenceGenerator.getNextSequence("region_seq"), is(equalTo(1L)));
        assertThat("sequence has 2", sequenceGenerator.getNextSequence("region_seq"), is(equalTo(2L)));
    }
    @Transactional
    @Test
    public void testInsertNew() {
        assertThat("one entry has been inserted", regionRepository.save(Region.builder().region("NSW").build()), is(equalTo(1L)));
    }
    @Transactional
    @Test
    public void testInserExisting() {
        assertThat("one entry has been inserted", regionRepository.save(Region.builder().region("NSW").build()), is(equalTo(1L)));
        assertThat("already existing is used", regionRepository.save(Region.builder().region("NSW").build()), is(equalTo(1L)));
    }
    @Transactional
    @Test
    public void testInserSecondOne() {
        assertThat("one entry has been inserted", regionRepository.save(Region.builder().region("NSW").build()), is(equalTo(1L)));
        assertThat("already existing is used", regionRepository.save(Region.builder().region("NSW").build()), is(equalTo(1L)));
        assertThat("one entry has been inserted", regionRepository.save(Region.builder().region("XYZ").build()), is(equalTo(2L)));
        assertThat("already existing is used", regionRepository.save(Region.builder().region("XYZ").build()), is(equalTo(2L)));
    }
}

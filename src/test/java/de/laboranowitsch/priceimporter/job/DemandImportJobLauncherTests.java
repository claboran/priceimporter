package de.laboranowitsch.priceimporter.job;

import de.laboranowitsch.priceimporter.PriceImporterApplication;
import de.laboranowitsch.priceimporter.launcher.DemandImportJobLauncher;
import de.laboranowitsch.priceimporter.testutil.FactDataRecordHelper;
import de.laboranowitsch.priceimporter.util.dbloader.DbLoader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Tests for {@link DemandImportJobLauncher}
 *
 * Created by cla on 5/06/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PriceImporterApplication.class)
public class DemandImportJobLauncherTests {

    private static final Logger LOG = LoggerFactory.getLogger(DemandImportJobLauncherTests.class);

    @Autowired
    private DbLoader dbLoader;

    @Autowired
    private DemandImportJobLauncher demandImportJobLauncher;

    @Autowired
    private DataSource dataSource;

    @Before
    public void before() throws SQLException {
        dbLoader.prepareDatabase();
    }
    @Test
    public void testContextLoads() {
        assertThat("wiring of dependencies is done properly", demandImportJobLauncher, is(not(nullValue())));
    }

    @Test
    public void testJobRun() throws Exception {
        demandImportJobLauncher.launchDemandImportJob("GRAPH_30NSW1.csv");
        assertThat("Importer job has the right number of elements", FactDataRecordHelper.getFactData(dataSource).size(), is(equalTo(96)));
    }

}

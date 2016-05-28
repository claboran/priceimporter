package de.laboranowitsch.priceimporter.config;

import de.laboranowitsch.priceimporter.repository.sequence.CustomDataFieldMaxValueIncrementerFactory;
import de.laboranowitsch.priceimporter.util.Profiles;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.Types;

/**
 * Hana Configuration class. Implements the {@link BatchConfigurer} interface
 * SAP Hana database is an unsupported database for Spring Batch, so there is a
 * bunch of configuration needed here.
 *
 * Created by cla on 4/8/16.
 */
@Configuration
@Profile(Profiles.DEV_HANA)
public class HanaConfiguration implements BatchConfigurer {

    @Autowired
    private DataSource dataSource;

    @Override
    public JobRepository getJobRepository() throws Exception {
        return HanaBatchConfigurationHelper.createJobRepository(dataSource, getTransactionManager(), "DEV_BATCH_");
    }

    @Override
    public PlatformTransactionManager getTransactionManager() throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }


    @Override
    public JobLauncher getJobLauncher() throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(getJobRepository());
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }

    @Override
    public JobExplorer getJobExplorer() throws Exception {
        JobExplorerFactoryBean jobExplorerFactoryBean = new JobExplorerFactoryBean();
        jobExplorerFactoryBean.setDataSource(dataSource);
        jobExplorerFactoryBean.afterPropertiesSet();
        return jobExplorerFactoryBean.getObject();
    }

}

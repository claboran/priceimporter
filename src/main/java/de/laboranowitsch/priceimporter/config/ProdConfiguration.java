package de.laboranowitsch.priceimporter.config;

import de.laboranowitsch.priceimporter.util.Profiles;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * Hana Configuration class. Implements the {@link BatchConfigurer} interface
 * SAP Hana database is an unsupported database for Spring Batch, so there is a
 * special configuration needed here. See also {@link HanaBatchConfigurationHelper}.
 *
 * @author christian@laboranowitsch.de
 */
@Configuration
@Profile(Profiles.PROD)
public class ProdConfiguration implements BatchConfigurer {

    @Autowired
    private DataSource dataSource;

    @Bean
    public TaskExecutor taskExecutor() {
        return new SimpleAsyncTaskExecutor();
    }

    @Override
    public JobRepository getJobRepository() throws Exception {
        return HanaBatchConfigurationHelper.createJobRepository(dataSource, getTransactionManager(), "BATCH_");
    }

    @Override
    public PlatformTransactionManager getTransactionManager() throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }

    @Override
    public JobLauncher getJobLauncher() throws Exception {
        return HanaBatchConfigurationHelper.createJobLauncher(taskExecutor(), getJobRepository());
    }

    @Override
    public JobExplorer getJobExplorer() throws Exception {
        JobExplorerFactoryBean jobExplorerFactoryBean = new JobExplorerFactoryBean();
        jobExplorerFactoryBean.setDataSource(dataSource);
        jobExplorerFactoryBean.afterPropertiesSet();
        return jobExplorerFactoryBean.getObject();
    }

}

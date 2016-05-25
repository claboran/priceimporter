package de.laboranowitsch.priceimporter.config;

import de.laboranowitsch.priceimporter.reader.FlatFileItemReaderFactoryBean;
import de.laboranowitsch.priceimporter.reader.PriceRecord;
import de.laboranowitsch.priceimporter.repository.sequence.H2SequenceGeneratorImpl;
import de.laboranowitsch.priceimporter.repository.sequence.SequenceGenerator;
import de.laboranowitsch.priceimporter.util.Profiles;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.explore.support.JobExplorerFactoryBean;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.item.ItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * H2 Configuration class.
 *
 * Created by cla on 4/8/16.
 */
@Configuration
@Profile(Profiles.INT_TEST_H2)
public class H2TestConfiguration implements BatchConfigurer {

    @Autowired
    private DataSource dataSource;

    @Bean
    public SequenceGenerator sequenceGenerator() {
        return new H2SequenceGeneratorImpl(dataSource);
    }

    @Bean
    public ItemReader<PriceRecord> reader() throws Exception {
        FlatFileItemReaderFactoryBean flatFileItemReaderFactoryBean = new FlatFileItemReaderFactoryBean();
        flatFileItemReaderFactoryBean.setResource("GRAPH_30NSW1.csv");
        flatFileItemReaderFactoryBean.afterPropertiesSet();
        return flatFileItemReaderFactoryBean.getObject();
    }

    @Override
    public JobRepository getJobRepository() throws Exception {
        JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
        jobRepositoryFactoryBean.setDatabaseType("H2");
        jobRepositoryFactoryBean.setDataSource(dataSource);
        jobRepositoryFactoryBean.setTransactionManager(getTransactionManager());
        jobRepositoryFactoryBean.setTablePrefix("INT_TEST_BATCH_");
        jobRepositoryFactoryBean.afterPropertiesSet();
        return jobRepositoryFactoryBean.getObject();
    }

    @Override
    public PlatformTransactionManager getTransactionManager() throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }

    @Override
    public JobLauncher getJobLauncher() throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher(); //TODO check if need to change to async operation here
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

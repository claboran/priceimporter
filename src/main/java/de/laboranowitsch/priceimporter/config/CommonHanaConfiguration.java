package de.laboranowitsch.priceimporter.config;

import de.laboranowitsch.priceimporter.repository.sequence.CustomDataFieldMaxValueIncrementerFactory;
import de.laboranowitsch.priceimporter.repository.sequence.HanaSequenceGeneratorImpl;
import de.laboranowitsch.priceimporter.repository.sequence.SequenceGenerator;
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
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Types;

/**
 * Hana Configuration class. Implements the {@link BatchConfigurer} interface
 * SAP Hana database is an unsupported database for Spring Batch, so there is a
 * bunch of configuration needed here.
 *
 * @author christian@laboranowitsch.de
 */
@Configuration
@Profile({Profiles.DEV_HANA, Profiles.INT_TEST_HANA, Profiles.PROD})
public class CommonHanaConfiguration {

    @Autowired
    private HanaDataBaseConfiguration hanaDataBaseConfiguration;

    @Bean(destroyMethod = "close")
    public DataSource dataSource() {

        org.apache.tomcat.jdbc.pool.DataSource ds = new org.apache.tomcat.jdbc.pool.DataSource();

        ds.setDriverClassName(hanaDataBaseConfiguration.getHanaDriverName());
        ds.setUrl(hanaDataBaseConfiguration.getHanaUrl());
        ds.setUsername(hanaDataBaseConfiguration.getHanaUserName());
        ds.setPassword(hanaDataBaseConfiguration.getHanaPassword());
        ds.setInitialSize(5);
        ds.setMaxActive(10);
        ds.setMaxIdle(5);
        ds.setMinIdle(2);

        return ds;
    }


    @Bean
    public SequenceGenerator sequenceGenerator() {
        return new HanaSequenceGeneratorImpl(dataSource());
    }


}

package de.laboranowitsch.priceimporter.config;

import de.laboranowitsch.priceimporter.repository.sequence.HanaSequenceGeneratorImpl;
import de.laboranowitsch.priceimporter.repository.sequence.SequenceGenerator;
import de.laboranowitsch.priceimporter.util.Profiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;

/**
 * H2 Configuration class.
 *
 * Created by cla on 4/8/16.
 */
@Configuration
@Profile(Profiles.INT_TEST_HANA)
public class HanaConfiguration {

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

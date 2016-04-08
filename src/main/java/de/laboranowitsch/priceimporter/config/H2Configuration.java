package de.laboranowitsch.priceimporter.config;

import de.laboranowitsch.priceimporter.repository.H2SequenceGeneratorImpl;
import de.laboranowitsch.priceimporter.repository.SequenceGenerator;
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
@Profile(Profiles.INT_TEST_H2)
public class H2Configuration {

    @Autowired
    private DataSource dataSource;

    @Bean
    public SequenceGenerator sequenceGenerator() {
        return new H2SequenceGeneratorImpl(dataSource);
    }
}

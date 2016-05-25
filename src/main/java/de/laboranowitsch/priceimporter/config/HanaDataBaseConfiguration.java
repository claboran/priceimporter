package de.laboranowitsch.priceimporter.config;

import de.laboranowitsch.priceimporter.util.Profiles;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Property exporter for Hana database configuration
 * {@link HanaConfiguration}.
 *
 * Created by cla on 4/13/16.
 */
@Profile({Profiles.INT_TEST_HANA})
@Component
public class HanaDataBaseConfiguration {

    @Value("${priceimporter.hana.driverclass}")
    private String hanaDriverName;
    @Value("${priceimporter.hana.url}")
    private String hanaUrl;
    @Value("${priceimporter.hana.username}")
    private String hanaUserName;
    @Value("${priceimporter.hana.password}")
    private String hanaPassword;

    public String getHanaDriverName() {
        return hanaDriverName;
    }

    public String getHanaUrl() {
        return hanaUrl;
    }

    public String getHanaUserName() {
        return hanaUserName;
    }

    public String getHanaPassword() {
        return hanaPassword;
    }
}

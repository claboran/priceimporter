package de.laboranowitsch.priceimporter.config;

import de.laboranowitsch.priceimporter.util.Profiles;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Property exporter for Hana {@link javax.sql.DataSource} configuration
 * {@link CommonHanaConfiguration}.
 *
 * @author christian@laboranowitsch.de
 */
@Profile({Profiles.INT_TEST_HANA, Profiles.DEV_HANA, Profiles.PROD})
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

package de.laboranowitsch.priceimporter.util;

/**
 * Spring Profiles going to be used for staging of the application.
 * 
 * @author christian@laboranowitsch.de
 */
public class Profiles {

    /**
     * Integration testing on H2
     */
    public static final String INT_TEST_H2 = "int-test-h2";
    /**
     * Integration testing on SAP Hana
     */
    public static final String INT_TEST_HANA = "int-test-hana";
    /**
     * Development server on H2
     */
    public static final String DEV_H2 = "dev-h2";
    /**
     * Development server on SAP Hana
     */
    public static final String DEV_HANA = "dev-hana";
    /**
     * Production server on SAP Hana
     */
    public static final String PROD = "prod";

}

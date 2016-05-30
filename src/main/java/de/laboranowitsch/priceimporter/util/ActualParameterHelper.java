package de.laboranowitsch.priceimporter.util;

/**
 * Helper class for adding column to NamedParameters.
 *
 * @author christian@laboranowitsch.de
 */
public class ActualParameterHelper {

    /**
     * Adds a colon to the parameter.
     *
     * @param parameter
     * @return parameter with colon
     */
    public static String makeActualParameterOf(String parameter) {
        return ":" + parameter;
    }
}

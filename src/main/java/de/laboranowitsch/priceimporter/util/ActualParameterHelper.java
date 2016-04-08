package de.laboranowitsch.priceimporter.util;

/**
 * Helper class for adding column to NamedParameters.
 *
 * Created by cla on 4/8/16.
 */
public class ActualParameterHelper {

    /**
     * Adds a column to the parameter
     *
     * @param parameter
     * @return :parameter
     */
    public static String makeActualParameterOf(String parameter) {
        return ":" + parameter;
    }
}

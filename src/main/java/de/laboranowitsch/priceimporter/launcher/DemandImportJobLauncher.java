package de.laboranowitsch.priceimporter.launcher;

/**
 * Responsible service for creation and execution of DemandImportJobs.
 *
 * @author christian@laboranowitsch.de
 */
public interface DemandImportJobLauncher {

    /**
     * Launches DemandImportJobs
     *
     * @param fileName
     */
    void launchDemandImportJob(String fileName) throws Exception;
}

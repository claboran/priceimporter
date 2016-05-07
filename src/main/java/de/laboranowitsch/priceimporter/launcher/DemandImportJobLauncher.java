package de.laboranowitsch.priceimporter.launcher;

import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

/**
 * Encapsulates creation and execution of DemandImportJobs.
 *
 * Created by cla on 5/7/16.
 */
public interface DemandImportJobLauncher {

    /**
     * Launches DemandImportJobs
     *
     * @param fileName
     */
    void launchDemandImportJob(String fileName) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException;
}

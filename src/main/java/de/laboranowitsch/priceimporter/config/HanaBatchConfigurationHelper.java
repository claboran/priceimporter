package de.laboranowitsch.priceimporter.config;

import de.laboranowitsch.priceimporter.repository.sequence.CustomDataFieldMaxValueIncrementerFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.Types;


/**
 * Helper class for common preparation of SAP Hana
 * {@link org.springframework.batch.core.configuration.annotation.BatchConfigurer} components.
 *
 * @author christian@laboranowitsch.de
 */
public class HanaBatchConfigurationHelper {

    /**
     * Creates a HANA {@link JobRepository}. Configuration of a non-supported database type like SAP Hana to be
     * handled by Spring-Batch.
     *
     * @param dataSource
     * @param platformTransactionManager
     * @param batchTablePrefix according to the selected {@link de.laboranowitsch.priceimporter.util.Profiles}
     * @return finished {@link JobRepository}
     * @throws Exception
     */
    public static JobRepository createJobRepository(DataSource dataSource,
                                                    PlatformTransactionManager platformTransactionManager,
                                                    String batchTablePrefix) throws Exception {

        JobRepositoryFactoryBean jobRepositoryFactoryBean = new JobRepositoryFactoryBean();
        jobRepositoryFactoryBean.setDatabaseType("HDB"); //JDBC Driver Metadata requests HDB
        jobRepositoryFactoryBean.setDataSource(dataSource);
        jobRepositoryFactoryBean.setTransactionManager(platformTransactionManager);
        jobRepositoryFactoryBean.setIncrementerFactory(new CustomDataFieldMaxValueIncrementerFactory(dataSource));
        jobRepositoryFactoryBean.setTablePrefix(batchTablePrefix);
        jobRepositoryFactoryBean.setClobType(Types.NCLOB);
        jobRepositoryFactoryBean.afterPropertiesSet();
        return jobRepositoryFactoryBean.getObject();

    }

    /**
     * Creates an {@link JobLauncher} for SAP Hana configuration.
     * @param taskExecutor
     * @param jobRepository
     * @return finished {@link JobLauncher}
     * @throws Exception
     */
    public static JobLauncher createJobLauncher(TaskExecutor taskExecutor, JobRepository jobRepository) throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(taskExecutor);
        jobLauncher.afterPropertiesSet();
        return jobLauncher;

    }
}

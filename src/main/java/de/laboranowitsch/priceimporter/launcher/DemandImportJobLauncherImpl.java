package de.laboranowitsch.priceimporter.launcher;

import de.laboranowitsch.priceimporter.domain.CompositeRecord;
import de.laboranowitsch.priceimporter.reader.PriceRecord;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Implementation class of {@link DemandImportJobLauncher}.
 *
 * Created by cla on 5/7/16.
 */
@Component
public class DemandImportJobLauncherImpl implements DemandImportJobLauncher {

    private final JobLauncher jobLauncher;
    private final ItemReader<PriceRecord> itemReader;
    private final ItemWriter<CompositeRecord> itemWriter;
    private final ItemProcessor<PriceRecord, CompositeRecord> itemProcessor;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final Integer chunkSize;

    @Autowired
    public DemandImportJobLauncherImpl(final JobLauncher jobLauncher,
                                       final ItemReader<PriceRecord> itemReader,
                                       final ItemWriter<CompositeRecord> itemWriter,
                                       final ItemProcessor<PriceRecord, CompositeRecord> itemProcessor,
                                       final JobBuilderFactory jobBuilderFactory,
                                       final StepBuilderFactory stepBuilderFactory,
                                       @Value("${priceimporter.demandjobimporter.chunksize}") final Integer chunkSize) {

        this.jobLauncher = jobLauncher;
        this.itemReader = itemReader;
        this.itemWriter = itemWriter;
        this.itemProcessor = itemProcessor;
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.chunkSize = chunkSize;
    }

    @Override
    public void launchDemandImportJob(String fileName) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        jobLauncher.run(createDemandImportJob(fileName), new JobParameters());
    }

    private Job createDemandImportJob(String fileName) {
        return jobBuilderFactory.get("demandDataImportJob-"+fileName+"-"+ LocalDateTime.now().toString())
                .incrementer(new RunIdIncrementer())
                .flow(stepBuilderFactory.get("step1")
                        .<PriceRecord, CompositeRecord> chunk(chunkSize)
                        .reader(itemReader)
                        .processor(itemProcessor)
                        .writer(itemWriter)
                        .build())
                .end()
                .build();
    }
}

package de.laboranowitsch.priceimporter.launcher;

import de.laboranowitsch.priceimporter.domain.CompositeRecord;
import de.laboranowitsch.priceimporter.reader.ItemReaderResourceLoader;
import de.laboranowitsch.priceimporter.reader.PriceRecord;
import de.laboranowitsch.priceimporter.reader.PriceRecordFlatFileItemReaderFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
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
    private final ItemWriter<CompositeRecord> itemWriter;
    private final ItemProcessor<PriceRecord, CompositeRecord> itemProcessor;
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final Integer chunkSize;
    private final ItemReaderResourceLoader itemReaderResourceLoader;

    @Autowired
    public DemandImportJobLauncherImpl(final JobLauncher jobLauncher,
                                       final ItemWriter<CompositeRecord> itemWriter,
                                       final ItemProcessor<PriceRecord, CompositeRecord> itemProcessor,
                                       final JobBuilderFactory jobBuilderFactory,
                                       final StepBuilderFactory stepBuilderFactory,
                                       final ItemReaderResourceLoader itemReaderResourceLoader,
                                       @Value("${priceimporter.demandjobimporter.chunksize}") final Integer chunkSize) {

        this.jobLauncher = jobLauncher;
        this.itemWriter = itemWriter;
        this.itemProcessor = itemProcessor;
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.itemReaderResourceLoader = itemReaderResourceLoader;
        this.chunkSize = chunkSize;
    }

    @Override
    public void launchDemandImportJob(String fileName) throws Exception {
        jobLauncher.run(createDemandImportJob(itemReaderResourceLoader.getResourceFromClasspath(fileName)), new JobParameters());
    }

    private Job createDemandImportJob(Resource resource) throws Exception {
        return jobBuilderFactory.get("demandDataImportJob-"+ resource.getFilename() +"-"+ LocalDateTime.now().toString())
                .incrementer(new RunIdIncrementer())
                .flow(stepBuilderFactory.get("priceimporter-step")
                        .<PriceRecord, CompositeRecord> chunk(chunkSize)
                        .reader(PriceRecordFlatFileItemReaderFactory.createReader(resource))
                        .processor(itemProcessor)
                        .writer(itemWriter)
                        .build())
                .end()
                .build();
    }

}

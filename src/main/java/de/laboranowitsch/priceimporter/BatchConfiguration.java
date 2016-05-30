package de.laboranowitsch.priceimporter;

import de.laboranowitsch.priceimporter.domain.CompositeRecord;
import de.laboranowitsch.priceimporter.processor.PriceRecordToCompositeRecordProcessor;
import de.laboranowitsch.priceimporter.reader.PriceRecord;
import de.laboranowitsch.priceimporter.service.RecordImportService;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.adapter.ItemWriterAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration of {@link ItemWriter} and {@linl ItemProcessor} Beans.
 *
 * @author christian@laboranowitsch.de
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    private RecordImportService recordImportService;


    /**
     * {@link RecordImportService} gets wired by {@link ItemWriterAdapter}
     * @return finished {@link ItemWriter}
     * @throws Exception
     */
    @Bean
    public ItemWriter<CompositeRecord> itemWriter() throws Exception {
        ItemWriterAdapter<CompositeRecord> itemWriterAdapter = new ItemWriterAdapter<>();
        itemWriterAdapter.setTargetObject(recordImportService);
        itemWriterAdapter.setTargetMethod("importRecord");
        itemWriterAdapter.afterPropertiesSet();
        return itemWriterAdapter;
    }

    @Bean
    public ItemProcessor<PriceRecord, CompositeRecord> itemProcessor() {
        return new PriceRecordToCompositeRecordProcessor();
    }


}

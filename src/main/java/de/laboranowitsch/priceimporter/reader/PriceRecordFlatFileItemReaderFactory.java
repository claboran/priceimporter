package de.laboranowitsch.priceimporter.reader;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Static factory for creating a {@link PriceRecord} {@link org.springframework.batch.item.file.FlatFileItemReader}.
 *
 * Created by cla on 5/26/16.
 */
public class PriceRecordFlatFileItemReaderFactory {

    /**
     * Convenience function for creating a {@link FlatFileItemReader}
     *
     * @param resource could be taken from {@link ItemReaderResourceLoader}
     * @return {@link FlatFileItemReader}
     * @throws Exception
     */
    public static FlatFileItemReader<PriceRecord> createReader(Resource resource) throws Exception {
        FlatFileItemReader<PriceRecord> reader = new FlatFileItemReader<>();
        reader.setResource(resource);
        reader.setLineMapper(new DefaultLineMapper<PriceRecord>() {{
            setLineTokenizer(
                    new DelimitedLineTokenizer() {{
                        setNames(PriceRecord.FIELD_NAMES);
                    }});

            setFieldSetMapper(fieldSet -> PriceRecord.builder().region(fieldSet.readString("REGION"))
                    .settlementDate(fieldSet.readString("SETTLEMENTDATE"))
                    .totalDemand(fieldSet.readDouble("TOTALDEMAND"))
                    .rrp(fieldSet.readDouble("RRP"))
                    .periodeType(fieldSet.readString("PERIODTYPE"))
                    .build());
        }});

        reader.setLinesToSkip(1);
        reader.afterPropertiesSet();

        return reader;
    }

}

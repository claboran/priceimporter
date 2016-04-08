package de.laboranowitsch.priceimporter;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.core.io.ClassPathResource;

/**
 * Created by cla on 3/22/16.
 */
public class FlatFileItemReaderFactoryBean extends AbstractFactoryBean<FlatFileItemReader<PriceRecord>> {


    private String resource;

    @Override
    public Class<?> getObjectType() {
        return FlatFileItemReader.class;
    }

    @Override
    protected FlatFileItemReader<PriceRecord> createInstance() throws Exception {
        FlatFileItemReader<PriceRecord> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource(resource));
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

    public void setResource(String resource) {
        this.resource = resource;
    }
}

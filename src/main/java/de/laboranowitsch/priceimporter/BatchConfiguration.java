package de.laboranowitsch.priceimporter;

import de.laboranowitsch.priceimporter.reader.FlatFileItemReaderFactoryBean;
import de.laboranowitsch.priceimporter.reader.PriceRecord;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.item.ItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    // tag::readerwriterprocessor[]
    @Bean
    public ItemReader<PriceRecord> reader() throws Exception {
        FlatFileItemReaderFactoryBean flatFileItemReaderFactoryBean = new FlatFileItemReaderFactoryBean();
        flatFileItemReaderFactoryBean.setResource("GRAPH_30NSW1.csv");
        flatFileItemReaderFactoryBean.afterPropertiesSet();
        return flatFileItemReaderFactoryBean.getObject();
    }

//    @Bean
//    public DataSource dataSource() {
//        org.apache.tomcat.jdbc.pool.DataSource ds = new org.apache.tomcat.jdbc.pool.DataSource();
//        ds.setDriverClassName("org.h2.Driver");
//        ds.setUrl("jdbc:h2:tcp://localhost/~/testdb");
//        ds.setUsername("sa");
//        ds.setPassword("");
//        ds.setInitialSize(5);
//        ds.setMaxActive(10);
//        ds.setMaxIdle(5);
//        ds.setMinIdle(2);
//        return ds;
//    }
//    @Bean
//    public ItemProcessor<Person, Person> processor() {
//        return new PersonItemProcessor();
//    }
//
//    @Bean
//    public ItemWriter<Person> writer(DataSource dataSource) {
//        JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<Person>();
//        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Person>());
//        writer.setSql("INSERT INTO people (first_name, last_name) VALUES (:firstName, :lastName)");
////        writer.setDataSource(dataSource());
//        writer.setDataSource(dataSource);
//        return writer;
//    }
//    // end::readerwriterprocessor[]
//
//    // tag::jobstep[]
//    @Bean
//    public Job importUserJob(JobBuilderFactory jobs, Step s1, JobExecutionListener listener) {
//        return jobs.get("importUserJob")
//                .incrementer(new RunIdIncrementer())
//                .listener(listener)
//                .flow(s1)
//                .end()
//                .build();
//    }
//
//    @Bean
//    public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<Person> reader,
//            ItemWriter<Person> writer, ItemProcessor<Person, Person> processor) {
//        return stepBuilderFactory.get("step1")
//                .<Person, Person> chunk(10)
//                .reader(reader)
//                .processor(processor)
//                .writer(writer)
//                .build();
//    }
//    // end::jobstep[]
//
//    @Bean
//    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//
////        return new JdbcTemplate(dataSource());
//    }

}

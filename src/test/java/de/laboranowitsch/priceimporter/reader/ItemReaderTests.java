package de.laboranowitsch.priceimporter.reader;

import de.laboranowitsch.priceimporter.PriceImporterApplication;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PriceImporterApplication.class)
public class ItemReaderTests {

	private FlatFileItemReader<PriceRecord> itemReader;

    @Before
    public void before() throws Exception {
        FlatFileItemReaderFactoryBean flatFileItemReaderFactoryBean = new FlatFileItemReaderFactoryBean();
        flatFileItemReaderFactoryBean.setResource("GRAPH_30NSW1.csv");
        flatFileItemReaderFactoryBean.afterPropertiesSet();
        itemReader = flatFileItemReaderFactoryBean.getObject();
        itemReader.open(new ExecutionContext());
    }

    @After
    public void after() {
        itemReader.close();
    }
	@Test
	public void contextLoads() {
	}

    @Test
    public void testItemReader() throws Exception {

        List<PriceRecord> priceRecords = new ArrayList<>();
        PriceRecord priceRecord;

        while(true) {
            priceRecord = itemReader.read();
            if(priceRecord == null) {
                break;
            } else {
                priceRecords.add(priceRecord);
            }
        }
        itemReader.close();

        assertThat("number of elements is 96", priceRecords.size(), is(equalTo(96)));

        assertThat("region is equal to NSW1", priceRecords.get(0).getRegion(), is(equalTo("NSW1")));
        assertThat("settlementDate is equal to 2016/03/22 04:30:00", priceRecords.get(0).getSettlementDate(), is(equalTo(ZonedDateTime.parse("2016/03/22 04:30:00",
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
                        .withZone(ZoneId.of("Australia/NSW"))))));
        assertThat("totalDemand is equal to 6359.17", priceRecords.get(0).getTotalDemand(), is(equalTo(6359.17)));
        assertThat("rpr is equal to 47.78", priceRecords.get(0).getRrp(), is(equalTo(47.78)));
        assertThat("periodType is equal to TRADE", priceRecords.get(0).getPeriodeType(), is(equalTo("TRADE")));

        assertThat("region is equal to NSW1", priceRecords.get(95).getRegion(), is(equalTo("NSW1")));
        assertThat("settlementDate is equal to 2016/03/24 04:00:00", priceRecords.get(95).getSettlementDate(), is(equalTo(ZonedDateTime.parse("2016/03/24 04:00:00",
                DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
                        .withZone(ZoneId.of("Australia/NSW"))))));
        assertThat("totalDemand is equal to 6053.73", priceRecords.get(95).getTotalDemand(), is(equalTo(6053.73)));
        assertThat("rpr is equal to 31.03005", priceRecords.get(95).getRrp(), is(equalTo(31.03005)));
        assertThat("periodType is equal to PD", priceRecords.get(95).getPeriodeType(), is(equalTo("PD")));
    }
}

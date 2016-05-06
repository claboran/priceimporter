package de.laboranowitsch.priceimporter.processor;

import de.laboranowitsch.priceimporter.domain.*;
import de.laboranowitsch.priceimporter.reader.PriceRecord;
import org.springframework.batch.item.ItemProcessor;

/**
 * Spring Batch {@link ItemProcessor} for transforming of {@link PriceRecord}
 * to {@link CompositeRecord}.
 *
 * Created by cla on 5/6/16.
 */
public class PriceRecordToCompositeRecordProcessor implements ItemProcessor<PriceRecord, CompositeRecord>{

    @Override
    public CompositeRecord process(PriceRecord item) throws Exception {
        CompositeRecord compositeRecord = new CompositeRecord();
        Region region = Region.builder().region(item.getRegion()).build();
        Period period = Period.builder().period(item.getPeriodeType()).build();
        DateTime dateTime = DateTime.builder().year(item.getSettlementDate().getYear())
                .monthOfYear(item.getSettlementDate().getMonthValue())
                .dayOfMonth(item.getSettlementDate().getDayOfMonth())
                .hourOfDay(item.getSettlementDate().getHour())
                .minuteOfHour(item.getSettlementDate().getMinute()).build();
        FactData factData = FactData.builder().totalDemand(item.getTotalDemand())
                .rpr(item.getRrp()).build();

        compositeRecord.setFactData(factData);
        compositeRecord.setRegion(region);
        compositeRecord.setPeriod(period);
        compositeRecord.setDateTime(dateTime);
        return compositeRecord;
    }
}

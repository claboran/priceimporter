package de.laboranowitsch.priceimporter.testutil;

import de.laboranowitsch.priceimporter.domain.*;

/**
 * Helper class to create {@link de.laboranowitsch.priceimporter.domain.CompositeRecord}.
 *
 * @author christian@laboranowitsch.de
 */
public class CompositeRecordHelper {

    public static CompositeRecord createCompositeRecord(String periodType, Double rpr, Double totalDemand) {
        CompositeRecord compositeRecord = new CompositeRecord();
        compositeRecord.setDateTime(DateTime.builder().dayOfMonth(1)
                .monthOfYear(1)
                .year(2016)
                .hourOfDay(00)
                .minuteOfHour(00)
                .build());
        compositeRecord.setPeriod(Period.builder().period(periodType).build());
        compositeRecord.setRegion(Region.builder().region("NSW").build());
        compositeRecord.setFactData(FactData.builder().rpr(rpr).totalDemand(totalDemand).build());
        return compositeRecord;
    }
}

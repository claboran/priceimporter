package de.laboranowitsch.priceimporter.service;

import de.laboranowitsch.priceimporter.domain.CompositeRecord;
import de.laboranowitsch.priceimporter.domain.FactData;
import de.laboranowitsch.priceimporter.repository.DateTimeRepository;
import de.laboranowitsch.priceimporter.repository.FactDataRepository;
import de.laboranowitsch.priceimporter.repository.PeriodRepository;
import de.laboranowitsch.priceimporter.repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementation class for {@link RecordImportService}.
 *
 * @author christian@laboranowitsch.de
 */
@Component
public class RecordImportServiceImpl implements RecordImportService {

    private final DateTimeRepository dateTimeRepository;
    private final FactDataRepository factDataRepository;
    private final PeriodRepository periodRepository;
    private final RegionRepository regionRepository;

    @Autowired
    public RecordImportServiceImpl(final DateTimeRepository dateTimeRepository,
                                   final FactDataRepository factDataRepository,
                                   final PeriodRepository periodRepository,
                                   final RegionRepository regionRepository) {
        this.dateTimeRepository = dateTimeRepository;
        this.factDataRepository = factDataRepository;
        this.periodRepository = periodRepository;
        this.regionRepository = regionRepository;
    }

    @Override
    public void importRecord(CompositeRecord compositeRecord) {
        Long regionId = regionRepository.save(compositeRecord.getRegion());
        Long periodId = periodRepository.save(compositeRecord.getPeriod());
        Long dateTimeId = dateTimeRepository.save(compositeRecord.getDateTime());
        FactData factData = compositeRecord.getFactData();
        factData.setDateTimeId(dateTimeId);
        factData.setPeriodId(periodId);
        factData.setRegionId(regionId);
        factDataRepository.save(factData);
    }
}

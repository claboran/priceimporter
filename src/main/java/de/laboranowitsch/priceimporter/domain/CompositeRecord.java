package de.laboranowitsch.priceimporter.domain;

/**
 * Encapsulates a complete record transformed by the {@link org.springframework.batch.item.ItemProcessor}.
 *
 * Created by cla on 5/1/16.
 */
public class CompositeRecord {

    private DateTime dateTime;
    private Period period;
    private Region region;
    private FactData factData;

    public DateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public FactData getFactData() {
        return factData;
    }

    public void setFactData(FactData factData) {
        this.factData = factData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompositeRecord that = (CompositeRecord) o;

        if (dateTime != null ? !dateTime.equals(that.dateTime) : that.dateTime != null) return false;
        if (period != null ? !period.equals(that.period) : that.period != null) return false;
        if (region != null ? !region.equals(that.region) : that.region != null) return false;
        return factData != null ? factData.equals(that.factData) : that.factData == null;

    }

    @Override
    public int hashCode() {
        int result = dateTime != null ? dateTime.hashCode() : 0;
        result = 31 * result + (period != null ? period.hashCode() : 0);
        result = 31 * result + (region != null ? region.hashCode() : 0);
        result = 31 * result + (factData != null ? factData.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CompositeRecord{");
        sb.append("dateTime=").append(dateTime);
        sb.append(", period=").append(period);
        sb.append(", region=").append(region);
        sb.append(", factData=").append(factData);
        sb.append('}');
        return sb.toString();
    }
}

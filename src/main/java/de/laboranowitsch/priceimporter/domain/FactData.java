package de.laboranowitsch.priceimporter.domain;

/**
 * FactData encapsulates the facts and the foreign keys of all dimensions.
 *
 * @author christian@laboranowitsch.de
 */
public class FactData {

    private Long id;
    private Double totalDemand;
    private Double rpr;
    private Long periodId;
    private Long dateTimeId;
    private Long regionId;

    private FactData(Builder builder) {
        setId(builder.id);
        setTotalDemand(builder.totalDemand);
        setRpr(builder.rpr);
        setPeriodId(builder.periodId);
        setDateTimeId(builder.dateTimeId);
        setRegionId(builder.regionId);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalDemand() {
        return totalDemand;
    }

    public void setTotalDemand(Double totalDemand) {
        this.totalDemand = totalDemand;
    }

    public Double getRpr() {
        return rpr;
    }

    public void setRpr(Double rpr) {
        this.rpr = rpr;
    }

    public Long getPeriodId() {
        return periodId;
    }

    public void setPeriodId(Long periodId) {
        this.periodId = periodId;
    }

    public Long getDateTimeId() {
        return dateTimeId;
    }

    public void setDateTimeId(Long dateTimeId) {
        this.dateTimeId = dateTimeId;
    }

    public Long getRegionId() {
        return regionId;
    }

    public void setRegionId(Long regionId) {
        this.regionId = regionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FactData factData = (FactData) o;

        if (id != null ? !id.equals(factData.id) : factData.id != null) return false;
        if (totalDemand != null ? !totalDemand.equals(factData.totalDemand) : factData.totalDemand != null)
            return false;
        if (rpr != null ? !rpr.equals(factData.rpr) : factData.rpr != null) return false;
        if (periodId != null ? !periodId.equals(factData.periodId) : factData.periodId != null) return false;
        if (dateTimeId != null ? !dateTimeId.equals(factData.dateTimeId) : factData.dateTimeId != null) return false;
        return regionId != null ? regionId.equals(factData.regionId) : factData.regionId == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (totalDemand != null ? totalDemand.hashCode() : 0);
        result = 31 * result + (rpr != null ? rpr.hashCode() : 0);
        result = 31 * result + (periodId != null ? periodId.hashCode() : 0);
        result = 31 * result + (dateTimeId != null ? dateTimeId.hashCode() : 0);
        result = 31 * result + (regionId != null ? regionId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("FactData{");
        sb.append("id=").append(id);
        sb.append(", totalDemand=").append(totalDemand);
        sb.append(", rpr=").append(rpr);
        sb.append(", periodId=").append(periodId);
        sb.append(", dateTimeId=").append(dateTimeId);
        sb.append(", regionId=").append(regionId);
        sb.append('}');
        return sb.toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private Double totalDemand;
        private Double rpr;
        private Long periodId;
        private Long dateTimeId;
        private Long regionId;

        public Builder() {
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder totalDemand(Double val) {
            totalDemand = val;
            return this;
        }

        public Builder rpr(Double val) {
            rpr = val;
            return this;
        }

        public Builder periodId(Long val) {
            periodId = val;
            return this;
        }

        public Builder dateTimeId(Long val) {
            dateTimeId = val;
            return this;
        }

        public Builder regionId(Long val) {
            regionId = val;
            return this;
        }

        public FactData build() {
            return new FactData(this);
        }
    }
}

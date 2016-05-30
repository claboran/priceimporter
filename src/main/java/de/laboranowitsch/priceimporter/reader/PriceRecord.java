package de.laboranowitsch.priceimporter.reader;

import de.laboranowitsch.priceimporter.util.DateConverterUtil;

import java.time.ZonedDateTime;

/**
 * Representing a row in the CSV format.
 *
 * @author christian@laboranowitsch.de
 */
public class PriceRecord {

    /**
     * Common FIELD_NAMES for the Reader
     */
    public static final String[] FIELD_NAMES = {"REGION", "SETTLEMENTDATE", "TOTALDEMAND", "RRP", "PERIODTYPE"};

    private String region;
    private ZonedDateTime settlementDate;
    private Double totalDemand;
    private Double rrp;
    private String periodeType;

    private PriceRecord(Builder builder) {
        setRegion(builder.region);
        setSettlementDate(builder.settlementDate);
        setTotalDemand(builder.totalDemand);
        setRrp(builder.rrp);
        setPeriodeType(builder.periodeType);
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public ZonedDateTime getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(String settlementDate) {
        this.settlementDate = DateConverterUtil.convertString2ZoneDateTime(settlementDate);
    }

    public Double getTotalDemand() {
        return totalDemand;
    }

    public void setTotalDemand(Double totalDemand) {
        this.totalDemand = totalDemand;
    }

    public Double getRrp() {
        return rrp;
    }

    public void setRrp(Double rrp) {
        this.rrp = rrp;
    }

    public String getPeriodeType() {
        return periodeType;
    }

    public void setPeriodeType(String periodeType) {
        this.periodeType = periodeType;
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("PriceRecord{");
        sb.append("region='").append(region).append('\'');
        sb.append(", settlementDate='").append(settlementDate).append('\'');
        sb.append(", totalDemand=").append(totalDemand);
        sb.append(", rrp=").append(rrp);
        sb.append(", periodeType='").append(periodeType).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String region;
        private String settlementDate;
        private Double totalDemand;
        private Double rrp;
        private String periodeType;

        private Builder() {
        }

        public Builder region(String val) {
            region = val;
            return this;
        }

        public Builder settlementDate(String val) {
            settlementDate = val;
            return this;
        }

        public Builder totalDemand(Double val) {
            totalDemand = val;
            return this;
        }

        public Builder rrp(Double val) {
            rrp = val;
            return this;
        }

        public Builder periodeType(String val) {
            periodeType = val;
            return this;
        }

        public PriceRecord build() {
            return new PriceRecord(this);
        }
    }
}

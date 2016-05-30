package de.laboranowitsch.priceimporter.domain;

/**
 * Domain class representing the Period dimension.
 *
 * @author christian@laboranowitsch.de
 */
public class Period {

    private Long id;
    private String period;

    private Period(Builder builder) {
        setId(builder.id);
        setPeriod(builder.period);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Period period1 = (Period) o;

        if (id != null ? !id.equals(period1.id) : period1.id != null) return false;
        return period != null ? period.equals(period1.period) : period1.period == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (period != null ? period.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Period{");
        sb.append("id=").append(id);
        sb.append(", period='").append(period).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private String period;

        public Builder() {
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder period(String val) {
            period = val;
            return this;
        }

        public Period build() {
            return new Period(this);
        }
    }
}

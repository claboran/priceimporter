package de.laboranowitsch.priceimporter.domain;

/**
 * Domain class representing the DateTime dimension.
 *
 * Created by cla on 4/24/16.
 */
public class DateTime {

    private Long id;
    private Integer year;
    private Integer monthOfYear;
    private Integer dayOfMonth;
    private Integer hourOfDay;
    private Integer minuteOfHour;

    private DateTime(Builder builder) {
        setId(builder.id);
        setYear(builder.year);
        setMonthOfYear(builder.monthOfYear);
        setDayOfMonth(builder.dayOfMonth);
        setHourOfDay(builder.hourOfDay);
        setMinuteOfHour(builder.minuteOfHour);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMonthOfYear() {
        return monthOfYear;
    }

    public void setMonthOfYear(Integer monthOfYear) {
        this.monthOfYear = monthOfYear;
    }

    public Integer getDayOfMonth() {
        return dayOfMonth;
    }

    public void setDayOfMonth(Integer dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public Integer getHourOfDay() {
        return hourOfDay;
    }

    public void setHourOfDay(Integer hourOfDay) {
        this.hourOfDay = hourOfDay;
    }

    public Integer getMinuteOfHour() {
        return minuteOfHour;
    }

    public void setMinuteOfHour(Integer minuteOfHour) {
        this.minuteOfHour = minuteOfHour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DateTime dateTime = (DateTime) o;

        if (id != null ? !id.equals(dateTime.id) : dateTime.id != null) return false;
        if (year != null ? !year.equals(dateTime.year) : dateTime.year != null) return false;
        if (monthOfYear != null ? !monthOfYear.equals(dateTime.monthOfYear) : dateTime.monthOfYear != null)
            return false;
        if (dayOfMonth != null ? !dayOfMonth.equals(dateTime.dayOfMonth) : dateTime.dayOfMonth != null) return false;
        if (hourOfDay != null ? !hourOfDay.equals(dateTime.hourOfDay) : dateTime.hourOfDay != null) return false;
        return minuteOfHour != null ? minuteOfHour.equals(dateTime.minuteOfHour) : dateTime.minuteOfHour == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (year != null ? year.hashCode() : 0);
        result = 31 * result + (monthOfYear != null ? monthOfYear.hashCode() : 0);
        result = 31 * result + (dayOfMonth != null ? dayOfMonth.hashCode() : 0);
        result = 31 * result + (hourOfDay != null ? hourOfDay.hashCode() : 0);
        result = 31 * result + (minuteOfHour != null ? minuteOfHour.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DateTime{");
        sb.append("id=").append(id);
        sb.append(", year=").append(year);
        sb.append(", monthOfYear=").append(monthOfYear);
        sb.append(", dayOfMonth=").append(dayOfMonth);
        sb.append(", hourOfDay=").append(hourOfDay);
        sb.append(", minuteOfHour=").append(minuteOfHour);
        sb.append('}');
        return sb.toString();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private Integer year;
        private Integer monthOfYear;
        private Integer dayOfMonth;
        private Integer hourOfDay;
        private Integer minuteOfHour;

        public Builder() {
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder year(Integer val) {
            year = val;
            return this;
        }

        public Builder monthOfYear(Integer val) {
            monthOfYear = val;
            return this;
        }

        public Builder dayOfMonth(Integer val) {
            dayOfMonth = val;
            return this;
        }

        public Builder hourOfDay(Integer val) {
            hourOfDay = val;
            return this;
        }

        public Builder minuteOfHour(Integer val) {
            minuteOfHour = val;
            return this;
        }

        public DateTime build() {
            return new DateTime(this);
        }
    }
}

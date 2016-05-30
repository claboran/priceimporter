package de.laboranowitsch.priceimporter.domain;

/**
 * Domain class representing the Region dimension.
 *
 * @author christian@laboranowitsch.de
 */
public class Region {

    private Long id;
    private String region;

    private Region(Builder builder) {
        setId(builder.id);
        setRegion(builder.region);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Region region1 = (Region) o;

        if (!id.equals(region1.id)) return false;
        return region.equals(region1.region);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + region.hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Region{");
        sb.append("id=").append(id);
        sb.append(", region='").append(region).append('\'');
        sb.append('}');
        return sb.toString();
    }


    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private Long id;
        private String region;

        public Builder() {
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder region(String val) {
            region = val;
            return this;
        }

        public Region build() {
            return new Region(this);
        }
    }
}

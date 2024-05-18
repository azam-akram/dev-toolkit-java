import javafx.util.Builder;

public class DataSource {

    private int mandatoryInt;

    private long mandatoryLong;

    private String optionalString;

    private int optionalInt;

    private long optionalLong;

    public static class Builder {

        private int mandatoryInt;

        private long mandatoryLong;

        private String optionalString;

        private int optionalInt;

        private long optionalLong;

        public Builder (int mandatoryInt, long mandatoryLong) {
            this.mandatoryInt = mandatoryInt;
            this.mandatoryLong = mandatoryLong;
        }

        public Builder optionalString(String optionalString) {
            this.optionalString = optionalString;
            return this;
        }

        public Builder optionalInt(int optionalInt) {
            this.optionalInt = optionalInt;
            return this;
        }

        public Builder optionalLong(long optionalLong) {
            this.optionalLong = optionalLong;
            return this;
        }

        public DataSource build() {
            return new DataSource(this);
        }
    }

    private DataSource(Builder builder) {
        this.mandatoryInt = builder.mandatoryInt;
        this.mandatoryLong = builder.mandatoryLong;
        this.optionalInt = builder.optionalInt;
        this.optionalLong = builder.optionalLong;
        this.optionalString = builder.optionalString;
    }

    public int getMandatoryInt() {
        return mandatoryInt;
    }

    public long getMandatoryLong() {
        return mandatoryLong;
    }

    public String getOptionalString() {
        return optionalString;
    }

    public int getOptionalInt() {
        return optionalInt;
    }

    public long getOptionalLong() {
        return optionalLong;
    }
}
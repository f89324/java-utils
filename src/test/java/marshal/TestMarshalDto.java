package marshal;

import java.io.Serializable;
import java.util.Objects;

public class TestMarshalDto implements Serializable {
    private static final long serialVersionUID = 936302430672008423L;

    private int intValue;
    private boolean booleanValue;
    private double doubleValue;
    private String stringValue;

    TestMarshalDto() {
    }

    TestMarshalDto(int intValue, boolean booleanValue, double doubleValue, String stringValue) {
        this.intValue = intValue;
        this.booleanValue = booleanValue;
        this.doubleValue = doubleValue;
        this.stringValue = stringValue;
    }

    public int getIntValue() {
        return intValue;
    }

    public void setIntValue(int intValue) {
        this.intValue = intValue;
    }

    public boolean isBooleanValue() {
        return booleanValue;
    }

    public void setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
    }

    public double getDoubleValue() {
        return doubleValue;
    }

    public void setDoubleValue(double doubleValue) {
        this.doubleValue = doubleValue;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestMarshalDto that = (TestMarshalDto) o;
        return intValue == that.intValue &&
                booleanValue == that.booleanValue &&
                Double.compare(that.doubleValue, doubleValue) == 0 &&
                Objects.equals(stringValue, that.stringValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(intValue, booleanValue, doubleValue, stringValue);
    }
}

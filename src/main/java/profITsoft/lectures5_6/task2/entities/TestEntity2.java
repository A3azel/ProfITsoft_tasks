package profITsoft.lectures5_6.task2.entities;

import profITsoft.lectures5_6.task2.annotations.Property;

import java.time.Instant;
import java.util.Objects;

public class TestEntity2 {
    @Property(name = "sp")
    private String stringProperty;
    @Property(name = "number")
    private int myNumber;
    @Property(name = "timeP", format = "dd/MM/yyyy HH:mm")
    private Instant instant;

    public TestEntity2() {
    }

    public TestEntity2(String stringProperty, int myNumber, Instant instant) {
        this.stringProperty = stringProperty;
        this.myNumber = myNumber;
        this.instant = instant;
    }

    public String getStringProperty() {
        return stringProperty;
    }

    public void setStringProperty(String stringProperty) {
        this.stringProperty = stringProperty;
    }

    public int getMyNumber() {
        return myNumber;
    }

    public void setMyNumber(int myNumber) {
        this.myNumber = myNumber;
    }

    public Instant getInstant() {
        return instant;
    }

    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestEntity2)) return false;
        TestEntity2 that = (TestEntity2) o;
        return myNumber == that.myNumber && Objects.equals(stringProperty, that.stringProperty) && Objects.equals(instant, that.instant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stringProperty, myNumber, instant);
    }

    @Override
    public String toString() {
        return "TestEntity2{" +
                "stringProperty='" + stringProperty + '\'' +
                ", myNumber=" + myNumber +
                ", instant=" + instant +
                '}';
    }
}

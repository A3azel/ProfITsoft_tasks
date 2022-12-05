package profITsoft.lectures5_6.task2.entities;

import java.util.Date;
import java.util.Objects;

public class TestEntity {
    private String stringProperty;
    private int myNumber;
    private Date timeProperty;

    public TestEntity() {
    }

    public TestEntity(String stringProperty, int myNumber, Date timeProperty) {
        this.stringProperty = stringProperty;
        this.myNumber = myNumber;
        this.timeProperty = timeProperty;
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

    public Date getTimeProperty() {
        return timeProperty;
    }

    public void setTimeProperty(Date timeProperty) {
        this.timeProperty = timeProperty;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TestEntity)) return false;
        TestEntity that = (TestEntity) o;
        return myNumber == that.myNumber && Objects.equals(stringProperty, that.stringProperty) && Objects.equals(timeProperty, that.timeProperty);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stringProperty, myNumber, timeProperty);
    }

    @Override
    public String toString() {
        return "TestEntity{" +
                "stringProperty='" + stringProperty + '\'' +
                ", myNumber=" + myNumber +
                ", timeProperty=" + timeProperty +
                '}';
    }
}

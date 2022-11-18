package profITsoft.lectures3_4.task2.entity;

import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.Date;

public class Violation{
    @SerializedName("first_name")
    private final String firstName;
    @SerializedName("last_name")
    private final String lastName;
    @SerializedName("type")
    private final String violationType;
    @SerializedName("fine_amount")
    private final BigDecimal fineAmount;
    @SerializedName("date_time")
    private final Date dateTime;

    public Violation(String firstName, String lastName, String violationType, BigDecimal fineAmount, Date dateTime) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.violationType = violationType;
        this.fineAmount = fineAmount;
        this.dateTime = dateTime;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getViolationType() {
        return violationType;
    }

    public BigDecimal getFineAmount() {
        return fineAmount;
    }

    public Date getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "Violation{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", violationType='" + violationType + '\'' +
                ", fineAmount=" + fineAmount +
                ", dateTime=" + dateTime +
                '}';
    }
}

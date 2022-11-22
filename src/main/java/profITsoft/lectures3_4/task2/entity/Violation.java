package profITsoft.lectures3_4.task2.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.math.BigDecimal;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Violation{
    @JsonProperty("type")
    @JacksonXmlProperty(localName = "type")
    private String violationType;
    @JsonProperty("fine_amount")
    @JacksonXmlProperty(localName = "total_amount_of_fines")
    private BigDecimal fineAmount;

    public Violation() {
    }

    public Violation(String violationType, BigDecimal fineAmount) {
        this.violationType = violationType;
        this.fineAmount = fineAmount;
    }

    public String getViolationType() {
        return violationType;
    }

    public BigDecimal getFineAmount() {
        return fineAmount;
    }

    // second solution

    /*@JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    @JsonProperty("type")
    private String violationType;
    @JsonProperty("fine_amount")
    private BigDecimal fineAmount;
    @JsonProperty("date_time")
    private Date dateTime;

    public Violation() {
    }

    public Violation(String firstName, String lastName, String violationType, BigDecimal fineAmount, Date dateTime) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.violationType = violationType;
        this.fineAmount = fineAmount;
        this.dateTime = dateTime;
    }

    public String getViolationType() {
        return violationType;
    }

    public BigDecimal getFineAmount() {
        return fineAmount;
    }*/
}

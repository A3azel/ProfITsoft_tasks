package profITsoft.lectures3_4.task2.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.math.BigDecimal;

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
}

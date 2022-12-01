package profITsoft.lectures3_4.task2.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ViolationJSON {
    @JsonProperty("type")
    private String violationType;
    @JsonProperty("fine_amount")
    private BigDecimal fineAmount;

    public ViolationJSON() {
    }

    public ViolationJSON(String violationType, BigDecimal fineAmount) {
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

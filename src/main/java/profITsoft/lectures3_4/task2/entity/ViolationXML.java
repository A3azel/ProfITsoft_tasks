package profITsoft.lectures3_4.task2.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.math.BigDecimal;

public class ViolationXML {
    @JacksonXmlProperty(localName = "type")
    private String violationType;
    @JacksonXmlProperty(localName = "total_amount_of_fines")
    private BigDecimal fineAmount;

    public ViolationXML() {
    }

    public ViolationXML(String violationType, BigDecimal fineAmount) {
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

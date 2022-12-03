package profITsoft.lectures3_4.task2.entity;

import java.math.BigDecimal;

public abstract class Violation {
    private String violationType;
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

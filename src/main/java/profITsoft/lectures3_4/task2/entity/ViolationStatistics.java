package profITsoft.lectures3_4.task2.entity;

import java.math.BigDecimal;

public class ViolationStatistics {
    private String violationType;
    private BigDecimal totalAmountOfFines;

    public ViolationStatistics(String violationType, BigDecimal totalAmountOfFines) {
        this.violationType = violationType;
        this.totalAmountOfFines = totalAmountOfFines;
    }

    public String getViolationType() {
        return violationType;
    }

    public BigDecimal getTotalAmountOfFines() {
        return totalAmountOfFines;
    }
}

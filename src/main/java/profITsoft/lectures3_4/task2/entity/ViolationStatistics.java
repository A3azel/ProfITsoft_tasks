package profITsoft.lectures3_4.task2.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.math.BigDecimal;
import java.util.List;

@JacksonXmlRootElement(localName = "statistic")
public class ViolationStatistics {
    @JacksonXmlElementWrapper(useWrapping = false)
    @JacksonXmlProperty(localName = "violation")
    private final List<Violation> violationList;

    public ViolationStatistics(List<Violation> violationList) {
        this.violationList = violationList;
    }

    /*private String violationType;
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
    }*/
}

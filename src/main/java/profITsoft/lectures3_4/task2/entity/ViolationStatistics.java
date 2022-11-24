package profITsoft.lectures3_4.task2.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "statistic")
public class ViolationStatistics {
    @JacksonXmlElementWrapper(localName = "violations")
    @JacksonXmlProperty(localName = "violation")
    private final List<Violation> violationList;

    public ViolationStatistics(List<Violation> violationList) {
        this.violationList = violationList;
    }
}

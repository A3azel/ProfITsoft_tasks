package profITsoft.lectures5_6.task1.entity;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "statistic")
public class ViolationStatistics {
    @JacksonXmlElementWrapper(localName = "violations")
    @JacksonXmlProperty(localName = "violation")
    private final List<ViolationXML> violationXMLList;

    public ViolationStatistics(List<ViolationXML> violationXMLList) {
        this.violationXMLList = violationXMLList;
    }
}

package profITsoft.lectures3_4.task2;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import profITsoft.lectures3_4.task2.entity.Violation;
import profITsoft.lectures3_4.task2.entity.ViolationStatistics;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ViolationConvertor {
    private static final String DEFAULT_OUTPUT_XML_PATH = "src/main/java/profITsoft/lectures3_4/task2/statisticsXMLs/";
    private static final String XML_SUFFIX = ".xml";

    private static final String ROOT_ELEMENT = "statistics";
    private static final String VIOLATION_ELEMENT = "violation";
    private static final String TYPE_ATTRIBUTE = "type";
    private static final String FINES_ATTRIBUTE = "total_amount_of_fines";

    public static void parseJSON(String dirPath, String outputXMLFileName){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create();
        List<Violation> violationList = new ArrayList<>();

        File jsonDirectory = new File(dirPath);
        File[] filesList = jsonDirectory.listFiles();
        if(filesList==null){
            System.out.println("Selected directory is empty");
            return;
        }
        for(File jsonFile: filesList){
            try(FileReader fileReader = new FileReader(jsonFile)){
                Violation violation = gson.fromJson(fileReader,Violation.class);
                if(violation!=null){
                    violationList.add(violation);
                }
            } catch (IOException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }
        List<ViolationStatistics> statisticsList = sortViolationStatistics(violationList);
        try {
            createXML(statisticsList,outputXMLFileName);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private static List<ViolationStatistics> sortViolationStatistics(List<Violation> violationList){
        /*Map<String,BigDecimal> map = violationList.stream()
                .map(a -> new ViolationStatistics(a.getViolationType(),a.getFineAmount()))
                .collect(Collectors.toList())
                .stream()
                .collect(Collectors.groupingBy(ViolationStatistics::getViolationType,Collectors.summingDouble(ViolationStatistics::getTotalAmountOfFines)));
*/
        return violationList.stream()
                .map(a -> new ViolationStatistics(a.getViolationType(),a.getFineAmount()))
                .collect(Collectors.toList());
    }

    private static void createXML(List<ViolationStatistics> statisticsList, String outputXMLFileName) throws ParserConfigurationException, TransformerException {
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();
        Element rootElement = doc.createElement(ROOT_ELEMENT);
        doc.appendChild(rootElement);

        for (ViolationStatistics statistics: statisticsList) {
            Element violation = doc.createElement(VIOLATION_ELEMENT);
            Attr violationType = doc.createAttribute(TYPE_ATTRIBUTE);
            violationType.setValue(statistics.getViolationType());
            Attr totalAmountOfFines = doc.createAttribute(FINES_ATTRIBUTE);
            totalAmountOfFines.setValue(String.valueOf(statistics.getTotalAmountOfFines()));
            violation.setAttributeNode(violationType);
            violation.setAttributeNode(totalAmountOfFines);
            rootElement.appendChild(violation);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(DEFAULT_OUTPUT_XML_PATH+outputXMLFileName+XML_SUFFIX));
        transformer.transform(source, result);
    }

}

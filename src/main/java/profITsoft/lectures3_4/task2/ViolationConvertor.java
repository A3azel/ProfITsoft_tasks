package profITsoft.lectures3_4.task2;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ViolationConvertor {
    private static final String DEFAULT_OUTPUT_XML_PATH = "src/main/resources/task2/outputFiles/";
    private static final String XML_SUFFIX = ".xml";

    private static final String DATE_FORMAT="yyyy-MM-dd HH:mm:ss";
    private static final String ROOT_ELEMENT = "statistics";
    private static final String VIOLATION_ELEMENT = "violation";
    private static final String TYPE_ATTRIBUTE = "type";
    private static final String FINES_ATTRIBUTE = "total_amount_of_fines";

    public static void parseJSON(String dirPath, String outputXMLFileName){
        Gson gson = new GsonBuilder()
                .setDateFormat(DATE_FORMAT)
                .create();
        List<Violation> allViolations = new ArrayList<>();

        File jsonDirectory = new File(dirPath);
        File[] filesList = jsonDirectory.listFiles();
        if(filesList==null){
            System.out.println("Selected directory is empty");
            return;
        }
        for(File jsonFile: filesList){
            try(BufferedReader bufferedReader = new BufferedReader(new FileReader(jsonFile))){
                Type founderListType = new TypeToken<ArrayList<Violation>>(){}.getType();
                List<Violation> violationList = gson.fromJson(bufferedReader, founderListType);
                if(violationList!=null){
                    allViolations.addAll(violationList);
                }
            } catch (IOException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }
        List<ViolationStatistics> statisticsList = creatingAndSortingStatistics(allViolations);
        try {
            createXML(statisticsList,outputXMLFileName);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private static List<ViolationStatistics> creatingAndSortingStatistics(List<Violation> violationList){
        return violationList.stream()
                .map(a -> new ViolationStatistics(a.getViolationType(),a.getFineAmount()))
                .collect(Collectors.toList())
                .stream()
                .collect(Collectors.groupingBy(ViolationStatistics::getViolationType,Collectors.reducing(BigDecimal.ZERO, ViolationStatistics::getTotalAmountOfFines, BigDecimal::add)))
                .entrySet()
                .stream()
                .sorted(Collections
                        .reverseOrder(Map.Entry.<String, BigDecimal>comparingByValue())
                        .thenComparing(Map.Entry.comparingByKey()))
                .map(x -> new ViolationStatistics(x.getKey(),x.getValue()))
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
            violation.setAttributeNode(totalAmountOfFines);
            violation.setAttributeNode(violationType);
            rootElement.appendChild(violation);
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(DEFAULT_OUTPUT_XML_PATH+outputXMLFileName+XML_SUFFIX));
        transformer.transform(source, result);
    }

}

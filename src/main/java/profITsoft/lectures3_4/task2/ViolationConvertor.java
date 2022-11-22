package profITsoft.lectures3_4.task2;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import profITsoft.lectures3_4.task2.entity.Violation;
import profITsoft.lectures3_4.task2.entity.ViolationStatistics;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
        ObjectMapper mapper = new ObjectMapper();
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        mapper.setDateFormat(dateFormat);
        List<Violation> allViolations = new ArrayList<>();

        File jsonDirectory = new File(dirPath);
        File[] filesList = jsonDirectory.listFiles();
        if(filesList==null){
            System.out.println("Selected directory is empty");
            return;
        }
        for(File jsonFile: filesList){
            try(BufferedReader bufferedReader = new BufferedReader(new FileReader(jsonFile))){
                List<Violation> violationList = mapper.readValue(bufferedReader, new TypeReference<>() {});
                if(violationList!=null){
                    allViolations.addAll(violationList);
                }
            } catch (IOException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }

        List<Violation> statisticsList = creatingAndSortingStatistics(allViolations);
        try {
            createXML(statisticsList,outputXMLFileName);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private static List<Violation> creatingAndSortingStatistics(List<Violation> violationList){
        return violationList.stream()
                .collect(Collectors.groupingBy(Violation::getViolationType,Collectors.reducing(BigDecimal.ZERO, Violation::getFineAmount, BigDecimal::add)))
                .entrySet()
                .stream()
                .sorted(Collections
                        .reverseOrder(Map.Entry.<String, BigDecimal>comparingByValue())
                        .thenComparing(Map.Entry.comparingByKey()))
                .map(x -> new Violation(x.getKey(),x.getValue()))
                .collect(Collectors.toList());
    }

    private static void createXML(List<Violation> statisticsList, String outputXMLFileName) throws ParserConfigurationException, TransformerException {
        XmlMapper mapper = new XmlMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        ViolationStatistics statistics = new ViolationStatistics(statisticsList);

        try {
            mapper.writeValue(new File(DEFAULT_OUTPUT_XML_PATH+outputXMLFileName+XML_SUFFIX), statistics);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Second solution with Jackson and DOM for creating XML file + added ViolationStatistics class

    /*public static void parseJSON(String dirPath, String outputXMLFileName){
        ObjectMapper mapper = new ObjectMapper();
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        mapper.setDateFormat(dateFormat);
        List<Violation> allViolations = new ArrayList<>();

        File jsonDirectory = new File(dirPath);
        File[] filesList = jsonDirectory.listFiles();
        if(filesList==null){
            System.out.println("Selected directory is empty");
            return;
        }
        for(File jsonFile: filesList){
            try(BufferedReader bufferedReader = new BufferedReader(new FileReader(jsonFile))){
                List<Violation> violationList = mapper.readValue(bufferedReader, new TypeReference<>() {});
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
        //prettier output
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(DEFAULT_OUTPUT_XML_PATH+outputXMLFileName+XML_SUFFIX));
        transformer.transform(source, result);
    }*/
}

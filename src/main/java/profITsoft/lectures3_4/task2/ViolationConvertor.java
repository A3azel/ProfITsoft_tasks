package profITsoft.lectures3_4.task2;


import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import profITsoft.lectures3_4.task2.entity.Violation;
import profITsoft.lectures3_4.task2.entity.ViolationStatistics;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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
}

package profITsoft.lectures3_4.task2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import profITsoft.lectures3_4.task2.entity.ViolationJSON;
import profITsoft.lectures3_4.task2.entity.ViolationStatistics;
import profITsoft.lectures3_4.task2.entity.ViolationXML;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class ViolationConvertor {
    private static final String DEFAULT_OUTPUT_XML_PATH = "src/main/resources/lectures3_4/task2/outputFiles/";
    private static final String XML_SUFFIX = ".xml";

    private static final String DATE_FORMAT="yyyy-MM-dd HH:mm:ss";
    private static final int CUSTOM_JSON_BUFFER = 50;

    // only 50 new json objects in memory
    public static void parseJSON(String dirPath, String outputXMLFileName){
        ObjectMapper mapper = new ObjectMapper();
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        mapper.setDateFormat(dateFormat);

        List<ViolationJSON> allViolationJSONS = new ArrayList<>();

        File jsonDirectory = new File(dirPath);
        File[] filesList = jsonDirectory.listFiles();
        if(filesList==null){
            System.out.println("Selected directory is empty");
            return;
        }
        for(File jsonFile: filesList){
            int jsonCount = 0;
            //faster solution
            try (InputStream is = new FileInputStream(jsonFile)){
                Scanner scanner = new Scanner(is, "UTF-8").useDelimiter("},");

                while (scanner.hasNext()) {
                    String row = scanner.next();
                    row = row+"}";
                    row = row.replaceAll("[\\[\\]]","");
                    ViolationJSON violationJSON = mapper.readValue(row, ViolationJSON.class);
                    allViolationJSONS.add(violationJSON);
                    jsonCount++;
                    if(jsonCount>CUSTOM_JSON_BUFFER){
                        jsonCount = 0;
                        allViolationJSONS = creatingAndSortingStatistics(allViolationJSONS);
                    }
                }
                if(jsonCount!=0){
                    allViolationJSONS = creatingAndSortingStatistics(allViolationJSONS);
                }
                scanner.close();
            } catch (IOException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }
        List<ViolationXML> allViolationXMLS = allViolationJSONS.stream()
                .map(x -> new ViolationXML(x.getViolationType(),x.getFineAmount()))
                .collect(Collectors.toList());
        try {
            createXML(allViolationXMLS,outputXMLFileName);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    private static List<ViolationJSON> creatingAndSortingStatistics(List<ViolationJSON> violationJSONList){
        return violationJSONList.stream()
                .collect(Collectors.groupingBy(ViolationJSON::getViolationType,Collectors.reducing(BigDecimal.ZERO, ViolationJSON::getFineAmount, BigDecimal::add)))
                .entrySet()
                .stream()
                .sorted(Collections
                        .reverseOrder(Map.Entry.<String, BigDecimal>comparingByValue())
                        .thenComparing(Map.Entry.comparingByKey()))
                .map(x -> new ViolationJSON(x.getKey(),x.getValue()))
                .collect(Collectors.toList());
    }

    private static void createXML(List<ViolationXML> statisticsList, String outputXMLFileName) throws ParserConfigurationException, TransformerException {
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

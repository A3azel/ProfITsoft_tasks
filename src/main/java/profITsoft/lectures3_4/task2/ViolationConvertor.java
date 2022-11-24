package profITsoft.lectures3_4.task2;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import profITsoft.lectures3_4.task2.entity.Violation;
import profITsoft.lectures3_4.task2.entity.ViolationStatistics;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ViolationConvertor {
    private static final String DEFAULT_OUTPUT_XML_PATH = "src/main/resources/task2/outputFiles/";
    private static final String XML_SUFFIX = ".xml";

    private static final String JSON_REGEX = "\\{\\s*[\\s\\S]+?}";

    private static final String DATE_FORMAT="yyyy-MM-dd HH:mm:ss";
    private static final int CUSTOM_JSON_BUFFER = 50;



    // only 50 new json objects in memory
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
                StringBuilder jsonString = new StringBuilder();
                String jsonLine;
                int jsonCount = 0;
                while((jsonLine=bufferedReader.readLine())!=null){
                    jsonString.append(jsonLine).append("\n");
                    Pattern pattern = Pattern.compile(JSON_REGEX);
                    Matcher matcher = pattern.matcher(jsonString.toString());
                    if (matcher.find()){
                        Violation violation = mapper.readValue(matcher.group(),Violation.class);
                        allViolations.add(violation);
                        jsonCount++;
                        if(jsonCount>CUSTOM_JSON_BUFFER){
                            jsonCount = 0;
                            List<Violation> statisticsList = creatingAndSortingStatistics(allViolations);
                            allViolations.clear();
                            allViolations.addAll(statisticsList);
                        }
                        jsonString.setLength(0);
                    }
                }
                if(jsonCount!=0){
                    List<Violation> statisticsList = creatingAndSortingStatistics(allViolations);
                    allViolations.clear();
                    allViolations.addAll(statisticsList);
                }
            } catch (IOException fileNotFoundException) {
                fileNotFoundException.printStackTrace();
            }
        }
        try {
            createXML(allViolations,outputXMLFileName);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }

    // entire file in memory
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
                    List<Violation> statisticsList = creatingAndSortingStatistics(allViolations);
                    allViolations.clear();
                    allViolations.addAll(statisticsList);
                } catch (IOException fileNotFoundException) {
                    fileNotFoundException.printStackTrace();
                }
            }
            //List<Violation> statisticsList = creatingAndSortingStatistics(allViolations);
            try {
                //createXML(statisticsList,outputXMLFileName);
                createXML(allViolations,outputXMLFileName);
            } catch (ParserConfigurationException | TransformerException e) {
                e.printStackTrace();
            }
        }*/

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

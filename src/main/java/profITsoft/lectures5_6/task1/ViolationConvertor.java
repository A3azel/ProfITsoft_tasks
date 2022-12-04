package profITsoft.lectures5_6.task1;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import profITsoft.lectures5_6.task1.entity.ViolationJSON;
import profITsoft.lectures5_6.task1.entity.ViolationStatistics;
import profITsoft.lectures5_6.task1.entity.ViolationXML;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ViolationConvertor {
    private static final String DEFAULT_OUTPUT_XML_PATH = "src/main/resources/lectures5_6/task1/outputFiles/";
    private static final String XML_SUFFIX = ".xml";
    private static final String JSON_REGEX = "\\{\\s*[\\s\\S]+?}";
    private static final String DATE_FORMAT="yyyy-MM-dd HH:mm:ss";
    private static final int CUSTOM_JSON_BUFFER = 50;
    private static final int DEFAULT_THREAD_COUNT = 8;

    public static void multithreadingParseJSON(String dirPath, String outputFileName){
        File[] filesList = checkFile(dirPath);

        ExecutorService service = Executors.newFixedThreadPool(DEFAULT_THREAD_COUNT);

        List<CompletableFuture<List<ViolationJSON>>> completableFutures = Arrays.stream(filesList)
                .map(x -> CompletableFuture.supplyAsync(() -> ViolationConvertor.parseJSON(x),service))
                .collect(Collectors.toList());
        service.shutdown();

        //CompletableFuture<Void> all = CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0]));

        CompletableFuture<List<ViolationJSON>> completableFuture = CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0]))
                .thenApply(v -> completableFutures.stream()
                .map(CompletableFuture::join)
                .flatMap(Collection::stream)
                .collect(Collectors.toList()));

        List<ViolationXML> violationXMLS = null;
        try {
            violationXMLS = ViolationConvertor.creatingAndSortingStatistics(completableFuture.get()).stream()
                    .map(x -> new ViolationXML(x.getViolationType(), x.getFineAmount()))
                    .collect(Collectors.toList());
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        try {
            ViolationConvertor.createXML(violationXMLS, outputFileName);
        } catch (ParserConfigurationException | TransformerException e) {
            e.printStackTrace();
        }
    }


    public static List<ViolationJSON> parseJSON(File selectedFile){
        ObjectMapper mapper = new ObjectMapper();
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        mapper.setDateFormat(dateFormat);
        System.out.println(Thread.currentThread().getName());
        List<ViolationJSON> allViolationJSONS = new ArrayList<>();
        int jsonCount = 0;

        try (InputStream is = new FileInputStream(selectedFile)){
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


        } catch (FileNotFoundException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //
        /*try(BufferedReader bufferedReader = new BufferedReader(new FileReader(selectedFile))){
            StringBuilder jsonString = new StringBuilder();
            String jsonLine;
            int jsonCount = 0;
            while((jsonLine=bufferedReader.readLine())!=null){
                jsonString.append(jsonLine).append("\n");
                Pattern pattern = Pattern.compile(JSON_REGEX);
                Matcher matcher = pattern.matcher(jsonString.toString());
                if (matcher.find()){
                    ViolationJSON violationJSON = mapper.readValue(matcher.group(), ViolationJSON.class);
                    allViolationJSONS.add(violationJSON);
                    jsonCount++;
                    if(jsonCount>CUSTOM_JSON_BUFFER){
                        jsonCount = 0;
                        allViolationJSONS = creatingAndSortingStatistics(allViolationJSONS);
                    }
                    jsonString.setLength(0);
                }
            }
            if(jsonCount!=0){
                allViolationJSONS = creatingAndSortingStatistics(allViolationJSONS);
            }
            //return mapper.readValue(bufferedReader, new TypeReference<>(){});
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }*/
        return allViolationJSONS;
        //return null;
    }

    public static List<ViolationJSON> creatingAndSortingStatistics(List<ViolationJSON> violationJSONList){
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

    public static void createXML(List<ViolationXML> statisticsList, String outputXMLFileName) throws ParserConfigurationException, TransformerException {
        XmlMapper mapper = new XmlMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        ViolationStatistics statistics = new ViolationStatistics(statisticsList);
        try {
            mapper.writeValue(new File(DEFAULT_OUTPUT_XML_PATH+outputXMLFileName+XML_SUFFIX), statistics);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static File[] checkFile(String dirPath){
        File jsonDirectory = new File(dirPath);
        File[] filesList = jsonDirectory.listFiles();
        if (filesList == null) {
            throw new IllegalArgumentException();
        }
        return filesList;
    }
}

package profITsoft.lectures5_6.task1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import profITsoft.lectures5_6.task1.entity.Violation;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class JsonGenerator {
    private static final String TEST_JSON_DIR_PATH = "src/main/resources/lectures5_6/task1/inputFiles/";
    private static final String DEFAULT_JSON_FILE_NAME = "TestJson";
    private static final String DATE_FORMAT="yyyy-MM-dd HH:mm:ss";
    private static final String JSON_SUFFIX = ".json";
    private static final int DEFAULT_COUNT_OF_FILES = 10;
    private static final int DEFAULT_COUNT_OF_JSON_OBJECTS = 10000;
    private static final List<String> DEFAULT_VIOLATIONS = List.of("DRIVING_UNDER_ALCOHOL","SPEEDING","TEASED","VANDALISM");

    public static void generateJsonFiles(){
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        mapper.setDateFormat(dateFormat);
        for (int i = 0; i < DEFAULT_COUNT_OF_FILES; i++) {
            File file = new File(TEST_JSON_DIR_PATH+DEFAULT_JSON_FILE_NAME+i+JSON_SUFFIX);
            try {
                mapper.writeValue(file,generateJsonObjects(i));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private static List<Violation> generateJsonObjects(int fileNumber){
        List<Violation> resultList = new ArrayList<>();
        for (int i = 0; i < DEFAULT_COUNT_OF_JSON_OBJECTS; i++) {
            Random rand = new Random();
            LocalDateTime localDateTime = LocalDateTime.now();
            Date date = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
            Violation violation = new Violation();
            violation.setFirstName("Test");
            violation.setLastName("Test");
            violation.setFineAmount(new BigDecimal(BigInteger.valueOf(new Random().nextInt(10001)), 2));
            violation.setType(DEFAULT_VIOLATIONS.get(rand.nextInt(DEFAULT_VIOLATIONS.size())));
            violation.setDateTime(date);
            resultList.add(violation);
        }
        return resultList;
    }

}

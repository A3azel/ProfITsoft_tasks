package profITsoft.lectures5_6.task2.validation;

import profITsoft.lectures5_6.task2.exeptions.CustomParseException;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;


public class ProcessingProperties {

    private static final String DEFAULT_DATA_FORMAT = "dd.MM.yyyy HH:mm";

    public static Map<String,String> parseProperties(Path propertiesPath){
        Map<String,String> fieldsMap = new HashMap<>();
        try(FileReader reader = new FileReader(String.valueOf(propertiesPath))){
            Properties properties = new Properties();
            properties.load(reader);
            fieldsMap = properties.entrySet()
                    .stream()
                    .collect(Collectors.toMap(
                                    e -> e.getKey().toString(),
                                    e -> e.getValue().toString()
                            )
                    );
            return fieldsMap;
        } catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        return fieldsMap;
    }

    public static Integer convertToInt(String possibleInt){
        try{
            return Integer.parseInt(possibleInt);
        }catch (NumberFormatException e){
            throw new CustomParseException(String.format("Can't format to Integer %1$s",possibleInt));
        }

    }

    public static Instant convertToInstant(String possibleInstant, String instantFormat){
        DateTimeFormatter formattedInstant = DateTimeFormatter.ofPattern(DEFAULT_DATA_FORMAT);
        if(!instantFormat.equals("")){
            formattedInstant = DateTimeFormatter.ofPattern(instantFormat);
        }
        try {
            return Instant.ofEpochSecond(Long.parseLong(possibleInstant));
        }catch (NumberFormatException e){
            try {
                LocalDateTime localDateTime = LocalDateTime.parse(possibleInstant,formattedInstant);
                return localDateTime.toInstant(ZoneOffset.UTC);
            }catch (DateTimeParseException e1){
                throw new CustomParseException(String.format("Can't format to Instant %1$s",possibleInstant));
            }
        }
    }

    public static Date convertToDate(String possibleData, String dateFormat) {
        DateFormat df = new SimpleDateFormat(DEFAULT_DATA_FORMAT);
        if(!dateFormat.equals("")){
            df = new SimpleDateFormat(dateFormat);
        }
        Date date;
        try {
            date = df.parse(possibleData);
        } catch (ParseException e) {
            throw new CustomParseException(String.format("Can't parse format to Date %1$s",dateFormat));
        }
        return date;
    }
}

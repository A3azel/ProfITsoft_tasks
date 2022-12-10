package profITsoft.lectures5_6.task2.validation;

import profITsoft.lectures5_6.task2.exeptions.CustomParseException;

import java.io.FileNotFoundException;
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
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;


public class ProcessingProperties {

    private static final String DEFAULT_DATA_FORMAT = "dd.MM.yyyy HH:mm";

    public static Map<String,String> parseProperties(Path propertiesPath) throws FileNotFoundException {
        Map<String,String> fieldsMap;
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
            throw new FileNotFoundException(String.format("File with path %1$s not found",propertiesPath));
        }
    }

    public static Integer convertToInt(String possibleInt) throws CustomParseException {
        try{
            return Integer.parseInt(possibleInt);
        }catch (NumberFormatException e){
            throw new CustomParseException(String.format("Can't format %1$s to Integer",possibleInt));
        }

    }

    public static Instant convertToInstant(String possibleInstant, String instantFormat) throws CustomParseException {
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
                throw new CustomParseException(String.format("Can't format %1$s to Instant with pattern %2$s",possibleInstant,formattedInstant));
            }
        }
    }

    public static Date convertToDate(String possibleData, String dateFormat) throws CustomParseException {
        DateFormat df = new SimpleDateFormat(DEFAULT_DATA_FORMAT);
        if(!dateFormat.equals("")){
            df = new SimpleDateFormat(dateFormat);
        }
        Date date;
        try {
            date = df.parse(possibleData);
        } catch (ParseException e) {
            throw new CustomParseException(String.format("Can't parse format %1$s to Date with pattern %2$s",dateFormat, df));
        }
        return date;
    }
}

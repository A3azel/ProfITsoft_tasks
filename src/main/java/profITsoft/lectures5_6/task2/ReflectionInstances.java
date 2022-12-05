package profITsoft.lectures5_6.task2;

import profITsoft.lectures5_6.task2.annotations.Property;
import profITsoft.lectures5_6.task2.exeptions.CustomFillInstantException;
import profITsoft.lectures5_6.task2.exeptions.CustomParseException;
import profITsoft.lectures5_6.task2.validation.ProcessingProperties;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;


public class ReflectionInstances {

    public static <T>T loadFromProperties(Class<T> cls, Path propertiesPath){
        Constructor<?> emptyConstructor;
        Object selectedClazz = null;
        try {
            emptyConstructor = cls.getConstructor();
            selectedClazz = emptyConstructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e ) {
            e.printStackTrace();
        }

        Map<String,String> fieldsMap = ProcessingProperties.parseProperties(propertiesPath);
        for(Field selectedField: cls.getDeclaredFields()){
            Property property = selectedField.getAnnotation(Property.class);
            String propertyValue = fieldsMap.get(selectedField.getName());
            String dataFormat = "";

            if(property!=null) {
                dataFormat = property.format();
                if (!property.name().equals("")) {
                    if (fieldsMap.get(property.name()) == null) {
                        throw new CustomFillInstantException(String.format("Can't find field annotated %1$s", property.name()));
                    }
                    propertyValue = fieldsMap.get(property.name());
                }
            }
            /*if(property!=null) {
                dataFormat = property.format();
                if (!property.name().equals("")) {
                    if(fieldsMap.get(selectedField.getName()) == null){
                        throw new CustomFillInstantException(String.format("Can't find field %1$s",selectedField.getName()));
                    }
                    propertyValue = fieldsMap.get(property.name());
                }else {
                    propertyValue = fieldsMap.get(selectedField.getName());
                }
            }else {
                propertyValue = fieldsMap.get(selectedField.getName());
            }*/

            Method setter;
            try {
                setter = cls.getDeclaredMethod(createSetterName(selectedField.getName()),selectedField.getType());
                if(selectedField.getType()==Integer.class || selectedField.getType()==int.class){
                    setter.invoke(selectedClazz, ProcessingProperties.convertToInt(propertyValue));
                }else if(selectedField.getType()==Instant.class){
                    setter.invoke(selectedClazz,ProcessingProperties.convertToInstant(propertyValue,dataFormat));
                }else if(selectedField.getType()==Date.class){
                    setter.invoke(selectedClazz,ProcessingProperties.convertToDate(propertyValue, dataFormat));
                }else {
                    setter.invoke(selectedClazz,propertyValue);
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new CustomFillInstantException(String.format("Can't fill field %1$s",selectedField.getName()));
            }
        }
        return (T) selectedClazz;
    }

    /*private static Map<String,String> parseProperties(Path propertiesPath){
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

    private static Integer convertToInt(String possibleInt){
        try{
            return Integer.parseInt(possibleInt);
        }catch (NumberFormatException e){
            throw new CustomParseException(String.format("Can't format to Integer %1$s",possibleInt));
        }

    }

    public static Instant convertToInstant(String possibleInstant){
        try {
            return Instant.parse(possibleInstant);
        }catch (DateTimeParseException e){
            throw new CustomParseException(String.format("Can't format to Instant %1$s",possibleInstant));
        }
    }

    private static Date convertToDate(String possibleData,String dateFormat) {
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
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
    }*/

    private static String createSetterName(String fieldName){
        StringBuilder stringBuilder = new StringBuilder("set");
        fieldName = fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
        return stringBuilder.append(fieldName).toString();
    }
}

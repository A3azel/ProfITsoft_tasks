package profITsoft.lectures5_6.task2;

import profITsoft.lectures5_6.task2.annotations.Property;
import profITsoft.lectures5_6.task2.exeptions.CustomFillInstantException;
import profITsoft.lectures5_6.task2.validation.ProcessingProperties;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Date;
import java.util.Map;


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

    private static String createSetterName(String fieldName){
        StringBuilder stringBuilder = new StringBuilder("set");
        fieldName = fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
        return stringBuilder.append(fieldName).toString();
    }
}

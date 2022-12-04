package profITsoft.lectures5_6.task2;

import profITsoft.lectures5_6.task2.exeptions.FieldNotFound;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class PropertiesClass {
    public static <T>T loadFromProperties(Class<T> cls, Path propertiesPath) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, FieldNotFound {
        Class<?> c = cls.getClass();
        System.out.println(Arrays.toString(cls.getDeclaredFields()));//yes
        Constructor<?> emptyConstructor = cls.getConstructor();
        TestClass objectToInvokeOn = (TestClass) emptyConstructor.newInstance();

        Map<String,String> fieldsMap = parseProperties(propertiesPath);
        for (Map.Entry<String, String> entry : fieldsMap.entrySet()){
            try {
                Field field = cls.getDeclaredField(entry.getKey());
            } catch (NoSuchFieldException e) {
                throw new FieldNotFound(String.format("Field %1$s not found",entry.getKey()));
            }
        }
        return null;
    }

    private static Map<String,String> parseProperties(Path propertiesPath){
        Map<String,String> fieldsMap = new HashMap<>();
        String fieldLine;
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(String.valueOf(propertiesPath)))){
            while((fieldLine=bufferedReader.readLine())!=null){
                String[] fieldKeyValue = fieldLine.split("=");
                fieldsMap.put(fieldKeyValue[0],fieldKeyValue[1]);
            }

        }catch (IOException fileNotFoundException) {
            fileNotFoundException.printStackTrace();
        }
        return fieldsMap;
    }


}

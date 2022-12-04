package profITsoft.lectures5_6.task2;

import profITsoft.lectures5_6.task2.exeptions.FieldNotFound;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        try {
            PropertiesClass.loadFromProperties(TestClass.class,Path.of(""));
        } catch (NoSuchMethodException | IllegalAccessException | FieldNotFound | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }
}

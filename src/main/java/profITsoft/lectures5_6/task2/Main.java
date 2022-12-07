package profITsoft.lectures5_6.task2;

import profITsoft.lectures5_6.task2.entities.TestEntity2;
import profITsoft.lectures5_6.task2.exeptions.CustomFillInstantException;
import profITsoft.lectures5_6.task2.exeptions.CustomParseException;

import java.io.FileNotFoundException;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        //System.out.println(ReflectionInstances.loadFromProperties(WrongTestEntity.class, Path.of("src/main/resources/lectures5_6/task2/test2.properties")));
        try {
            System.out.println(ReflectionInstances.loadFromProperties(TestEntity2.class, Path.of("src/main/resources/lectures5_6/task2/test.properties")));
        } catch (CustomFillInstantException | CustomParseException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}

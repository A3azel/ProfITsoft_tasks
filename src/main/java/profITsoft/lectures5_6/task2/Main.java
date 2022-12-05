package profITsoft.lectures5_6.task2;

import profITsoft.lectures5_6.task2.entities.TestEntity;
import profITsoft.lectures5_6.task2.entities.TestEntityWithAnnotations;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        System.out.println(ReflectionInstances.loadFromProperties(TestEntityWithAnnotations.class, Path.of("src/main/resources/lectures5_6/task2/inputFiles/test2.properties")));
    }
}

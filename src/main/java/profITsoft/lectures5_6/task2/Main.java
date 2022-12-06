package profITsoft.lectures5_6.task2;

import profITsoft.lectures5_6.task2.entities.TestEntity;
import profITsoft.lectures5_6.task2.entities.TestEntity2;
import profITsoft.lectures5_6.task2.entities.TestEntityWithAnnotations;
import profITsoft.lectures5_6.task2.entities.WrongTestEntity;
import profITsoft.lectures5_6.task2.validation.ProcessingProperties;

import java.nio.file.Path;
import java.time.Instant;

public class Main {
    public static void main(String[] args) {
        //System.out.println(ReflectionInstances.loadFromProperties(WrongTestEntity.class, Path.of("src/main/resources/lectures5_6/task2/inputFiles/test2.properties")));
        System.out.println(ReflectionInstances.loadFromProperties(TestEntity2.class, Path.of("src/main/resources/lectures5_6/task2/inputFiles/test.properties")));
    }
}

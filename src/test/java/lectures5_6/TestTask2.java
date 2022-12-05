package lectures5_6;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import profITsoft.lectures5_6.task2.ReflectionInstances;
import profITsoft.lectures5_6.task2.entities.TestEntity;
import profITsoft.lectures5_6.task2.entities.TestEntityWithAnnotations;
import profITsoft.lectures5_6.task2.entities.WrongTestEntity;
import profITsoft.lectures5_6.task2.exeptions.CustomFillInstantException;
import profITsoft.lectures5_6.task2.exeptions.CustomParseException;

import java.nio.file.Path;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestTask2 {
    private static final String TEST_1_PATH = "src/main/resources/lectures5_6/task2/inputFiles/test1.properties";
    private static final String TEST_2_PATH = "src/main/resources/lectures5_6/task2/inputFiles/test2.properties";
    private static final String TEST_3_PATH = "src/main/resources/lectures5_6/task2/inputFiles/test3.properties";

    @BeforeAll
    public static void init(){
        System.out.println("Starting testing");
    }

    @AfterAll
    public static void finish(){
        System.out.println("Finishing testing");
    }

    @Test
    public void testLoadFromPropertiesWithoutAnnotations(){
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        try {
            Date correctDate = df.parse("29.11.2022 18:30");
            TestEntity correctInstance = new TestEntity("value1",10,correctDate);
            TestEntity createdInstance = ReflectionInstances.loadFromProperties(TestEntity.class, Path.of(TEST_1_PATH));
            Assertions.assertEquals(correctInstance,createdInstance);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoadFromPropertiesWithAnnotations(){
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        try {
            Date correctDate = df.parse("01-01-2000 19:19");
            TestEntityWithAnnotations correctInstance = new TestEntityWithAnnotations("User","User",correctDate,22);
            TestEntityWithAnnotations createdInstance = ReflectionInstances.loadFromProperties(TestEntityWithAnnotations.class, Path.of(TEST_2_PATH));
            Assertions.assertEquals(correctInstance,createdInstance);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testLoadFromPropertiesWithExceptions(){
        Assertions.assertThrows(CustomFillInstantException.class,() -> ReflectionInstances.loadFromProperties(WrongTestEntity.class, Path.of(TEST_2_PATH)));
    }

    @Test
    public void testLoadFromPropertiesWithExceptions2(){
        Assertions.assertThrows(CustomParseException.class,() -> ReflectionInstances.loadFromProperties(TestEntityWithAnnotations.class, Path.of(TEST_3_PATH)));
    }

}

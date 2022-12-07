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
    private static final String TEST_1_PATH = "src/main/resources/lectures5_6/task2/test1.properties";
    private static final String TEST_2_PATH = "src/main/resources/lectures5_6/task2/test2.properties";
    private static final String TEST_3_PATH = "src/main/resources/lectures5_6/task2/test3.properties";

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
        TestEntity correctInstance = null;
        TestEntity createdInstance = null;
        try {
            Date correctDate = df.parse("29.11.2022 18:30");
            correctInstance = new TestEntity("value1",10,correctDate);
            createdInstance = ReflectionInstances.loadFromProperties(TestEntity.class, Path.of(TEST_1_PATH));
        } catch (ParseException | CustomFillInstantException | CustomParseException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(correctInstance,createdInstance);
    }

    @Test
    public void testLoadFromPropertiesWithAnnotations(){
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        TestEntityWithAnnotations correctInstance = null;
        TestEntityWithAnnotations createdInstance = null;
        try {
            Date correctDate = df.parse("01-01-2000 19:19");
            correctInstance = new TestEntityWithAnnotations("User","User",correctDate,22);
            createdInstance = ReflectionInstances.loadFromProperties(TestEntityWithAnnotations.class, Path.of(TEST_2_PATH));
        } catch (ParseException | CustomFillInstantException | CustomParseException e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(correctInstance,createdInstance);
    }

    @Test
    public void testLoadFromPropertiesWithExceptions(){
        Assertions.assertThrows(CustomFillInstantException.class,() -> ReflectionInstances.loadFromProperties(WrongTestEntity.class, Path.of(TEST_2_PATH)));
    }

    @Test
    public void testLoadFromPropertiesWithExceptions2(){
        Assertions.assertThrows(CustomParseException.class,() -> ReflectionInstances.loadFromProperties(TestEntityWithAnnotations.class, Path.of(TEST_3_PATH)));
    }

    @Test
    public void testNullAsClass(){
        Assertions.assertThrows(NullPointerException.class,() -> ReflectionInstances.loadFromProperties(null, Path.of(TEST_3_PATH)));
    }

    @Test
    public void testNullAsPath(){
        Assertions.assertThrows(CustomParseException.class,() -> ReflectionInstances.loadFromProperties(TestEntity.class, null));
    }

}

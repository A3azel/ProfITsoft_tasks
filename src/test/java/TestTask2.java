import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import profITsoft.Task2.SortingTagsTask;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TestTask2 {

    @BeforeAll
    public static void init(){
        System.out.println("Starting testing");
    }

    @AfterAll
    public static void finish(){
        System.out.println("Finishing testing");
    }

    @Test
    public void testWithoutDuplicatesInTheSentence(){
        List<String> text = List.of(
                "Some text #Tag1, #Tag2, #Tag3 and another text",
                "#Tag1 #Tag4 text test 13412 #Tag5",
                "#Tag7, #Tag3 12 test #Tag1, #Tag5 text",
                "#Tag5, #Tag2, #Tag1 text #Tag4",
                "#Tag7 #Tag5 #Tag2 #Tag1");
        Map<String,Integer> correctResult = new LinkedHashMap<>();
        correctResult.put("#Tag1",5);
        correctResult.put("#Tag5",4);
        correctResult.put("#Tag2",3);
        correctResult.put("#Tag3",2);
        correctResult.put("#Tag4",2);
        Map<String,Integer> result = SortingTagsTask.getMostPopularTags(text);
        System.out.println("testLessThenFiveTags result map");
        result
                .forEach((k,v)-> System.out.println("Tag: "+ k + " count: " + v));
        Assertions.assertEquals(result,correctResult);
    }

    @Test
    public void testDuplicatesInTheSentence(){
        List<String> text = List.of(
                "Some text #Tag1, #Tag2, #Tag2, #Tag3 and another #Tag1 text",
                "#Tag1 #Tag4 text test 13412 #Tag5",
                "#Tag7, #Tag3 12 #Tag5 test #Tag1, #Tag5 text",
                "#Tag5, #Tag2, #Tag1 text #Tag4,#Tag4 #Tag1",
                "#Tag7 #Tag5 #Tag2,#Tag1 #Tag2,#Tag5");
        Map<String,Integer> correctResult = new LinkedHashMap<>();
        correctResult.put("#Tag1",5);
        correctResult.put("#Tag5",4);
        correctResult.put("#Tag2",3);
        correctResult.put("#Tag3",2);
        correctResult.put("#Tag4",2);
        Map<String,Integer> result = SortingTagsTask.getMostPopularTags(text);
        System.out.println("testLessThenFiveTags result map");
        result
                .forEach((k,v)-> System.out.println("Tag: "+ k + " count: " + v));
        Assertions.assertEquals(result,correctResult);
    }

    @Test
    public void testLessThenFiveTags(){
        List<String> text = List.of(
                "Some text #Tag3, #Tag2, #Tag3 and another text",
                "#Tag1 #Tag3 text test 13412 #Tag3",
                "#Tag2, #Tag3 12 test #Tag1, #Tag2 text",
                "#Tag3, #Tag3, #Tag1 test");
        Map<String,Integer> correctResult = new LinkedHashMap<>();
        correctResult.put("#Tag3",4);
        correctResult.put("#Tag1",3);
        correctResult.put("#Tag2",2);
        Map<String,Integer> result = SortingTagsTask.getMostPopularTags(text);
        System.out.println("testLessThenFiveTags result map");
        result
                .forEach((k,v)-> System.out.println("Tag: "+ k + " count: " + v));
        Assertions.assertEquals(result,correctResult);
    }

    @Test
    public void testWithoutTags(){
        List<String> text1 = List.of("Some text...");
        List<String> text2 = List.of("Some text...", "Some text...", "Some text...", "Some text...");
        Map<String,Integer> correctResult = new LinkedHashMap<>();
        Map<String,Integer> result1 = SortingTagsTask.getMostPopularTags(text1);
        Map<String,Integer> result2 = SortingTagsTask.getMostPopularTags(text2);
        Assertions.assertEquals(result1,correctResult);
        Assertions.assertEquals(result2,correctResult);
    }

    @Test
    public void testNULL() throws NullPointerException{
        Assertions.assertThrows(NullPointerException.class, () -> SortingTagsTask.getMostPopularTags(null));
    }

}

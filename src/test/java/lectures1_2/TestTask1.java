package lectures1_2;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import profITsoft.lectures1_2.task1.SortingArrayTask;
;

public class TestTask1 {

    int[] mixArray = {3, 8, 3, -9, 0, 4, -7, 3, -7, 2, -7, 62, 46, -83, 14, 46};
    int[] negativeArray = {-5, -7, -23, -5, -3};
    int[] emptyArray = {};
    int[] testNull = null;

    @BeforeAll
    public static void init(){
        System.out.println("Starting testing");
    }

    @AfterAll
    public static void finish(){
        System.out.println("Finishing testing");
    }

    @Test
    public void testLambdaSorting(){
        int[] afterSorting = {62, 46, 46, 14, 8, 4, 3, 3, 3, 2, 0};
        Assertions.assertArrayEquals(SortingArrayTask.sortMassWithStream(mixArray),afterSorting);
    }

    @Test
    public void testVanillaSorting(){
        int[] afterSorting = {62, 46, 46, 14, 8, 4, 3, 3, 3, 2, 0};
        Assertions.assertArrayEquals(SortingArrayTask.vanillaSorting(mixArray),afterSorting);
    }

    @Test
    public void testLambdaSortingWithNegativeNumbers(){
        int[] afterSorting = {};
        Assertions.assertArrayEquals(SortingArrayTask.sortMassWithStream(negativeArray),afterSorting);
    }

    @Test
    public void testVanillaWithNegativeNumbers(){
        int[] afterSorting = {};
        Assertions.assertArrayEquals(SortingArrayTask.vanillaSorting(negativeArray),afterSorting);
    }

    @Test
    public void testLambdaSortingWithEmptyArray(){
        int[] afterSorting = {};
        Assertions.assertArrayEquals(SortingArrayTask.sortMassWithStream(emptyArray),afterSorting);
    }

    @Test
    public void testVanillaWithEmptyArray(){
        int[] afterSorting = {};
        Assertions.assertArrayEquals(SortingArrayTask.vanillaSorting(emptyArray),afterSorting);
    }

    @Test
    public void testLambdaSortingWithNULL(){
        Assertions.assertThrows(IllegalArgumentException.class,() -> SortingArrayTask.sortMassWithStream(testNull));
    }

    @Test
    public void testVanillaWithNULL(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> SortingArrayTask.vanillaSorting(testNull));
    }

}
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import profITsoft.Task1.SortingArrayTask;

public class TestTask1 {

    int[] testArray1 = {3, 8, 3, -9, 0, 4, -7, 3, -7, 2, -7, 62, 46, -83, 14, 46};
    int[] testArray2 = {-5, -7, -23, -5, -3};
    int[] testArray3 = null;

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
        Assertions.assertArrayEquals(SortingArrayTask.sortMassWithStream(testArray1),afterSorting);
    }

    @Test
    public void testVanillaSorting(){
        int[] afterSorting = {62, 46, 46, 14, 8, 4, 3, 3, 3, 2, 0};
        Assertions.assertArrayEquals(SortingArrayTask.vanillaSorting(testArray1),afterSorting);
    }

    @Test
    public void testLambdaSortingWithNegativeNumbers(){
        int[] afterSorting = {};
        Assertions.assertArrayEquals(SortingArrayTask.sortMassWithStream(testArray2),afterSorting);
    }

    @Test
    public void testVanillaWithNegativeNumbers(){
        int[] afterSorting = {};
        Assertions.assertArrayEquals(SortingArrayTask.vanillaSorting(testArray2),afterSorting);
    }

    @Test
    public void testLambdaSortingWithNULL() throws NullPointerException{
        Assertions.assertThrows(NullPointerException.class,() -> SortingArrayTask.sortMassWithStream(testArray3));
    }

    @Test
    public void testVanillaWithNULL() throws NullPointerException{
        Assertions.assertThrows(NullPointerException.class, () -> SortingArrayTask.vanillaSorting(testArray3));
    }

}
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import profITsoft.Task3.*;

import java.util.ArrayList;
import java.util.List;

public class TestTask3 {

    Cube cube1 = new Cube("Cube1",10.0,10.0,10.0);
    Cube cube2 = new Cube("Cube2",5.0,5.0,15.0);
    Cube cube3 = new Cube("Cube3",8.5,8.0,8.0);

    Cylinder cylinder1 = new Cylinder("Cylinder1",8.0,8.0);
    Cylinder cylinder2 = new Cylinder("Cylinder2",5.0,5.5);
    Cylinder cylinder3 = new Cylinder("Cylinder3",4.0,9.5);

    Sphere sphere1 = new Sphere("Sphere1", 5.5);
    Sphere sphere2 = new Sphere("Sphere2", 12);
    Sphere sphere3 = new Sphere("Sphere3", 7.5);

    List<Shape> beforeSorting = List.of(cube1, cube2, cube3, cylinder1, cylinder2, cylinder3, sphere1, sphere2, sphere3);

    @BeforeAll
    public static void init(){
        System.out.println("Starting testing");
    }

    @AfterAll
    public static void finish(){
        System.out.println("Finishing testing");
    }

    @Test
    public void testShapeSorting(){
        List<Shape> afterSorting = List.of(cube2, cylinder2, cylinder3, cube3, sphere1, cube1, cylinder1, sphere3, sphere2);
        System.out.println(afterSorting);
        Assertions.assertIterableEquals(SortingShapeTask.sortShape(beforeSorting),afterSorting);
    }

    @Test
    public void testShapeListSize(){
        List<Shape> afterSorting = List.of(cube2, cylinder2, cylinder3, cube3, sphere1, cube1, cylinder1, sphere3, sphere2);
        Assertions.assertEquals(SortingShapeTask.sortShape(beforeSorting).size(),afterSorting.size());
    }

    @Test
    public void testEmptyList(){
        List<Shape> emptyList = new ArrayList<>();
        List<Shape> afterSorting = new ArrayList<>();
        Assertions.assertEquals(SortingShapeTask.sortShape(emptyList),afterSorting);
    }

    @Test
    public void testShapeSortingWithNULL() throws NullPointerException{
        Assertions.assertThrows(NullPointerException.class, () -> SortingShapeTask.sortShape(null));
    }

}
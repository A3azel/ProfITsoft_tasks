package lectures1_2;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import profITsoft.lectures1_2.task3.*;

import java.util.ArrayList;
import java.util.List;

public class TestTask3 {

    Cube cube1 = new Cube("Cube1",10.0);
    Cube cube2 = new Cube("Cube2",5.0);
    Cube cube3 = new Cube("Cube3",8.5);

    Cube cube33 = new Cube("Cube33",8.5);
    Cube cube34 = new Cube("Cube34",8.5);

    Cylinder cylinder1 = new Cylinder("Cylinder1",8.0,8.0);
    Cylinder cylinder2 = new Cylinder("Cylinder2",5.0,5.5);
    Cylinder cylinder3 = new Cylinder("Cylinder3",4.0,9.5);

    Cylinder cylinder44 = new Cylinder("Cylinder44",4.0,9.5);
    Cylinder cylinder55 = new Cylinder("Cylinder55",4.0,9.5);

    Sphere sphere1 = new Sphere("Sphere1", 5.5);
    Sphere sphere2 = new Sphere("Sphere2", 12);
    Sphere sphere3 = new Sphere("Sphere3", 7.5);

    Sphere sphere512 = new Sphere("Sphere512", 7.5);

    List<Shape> beforeSorting = List.of(cube1, cube2, cube3, cylinder1, cylinder2, cylinder3, sphere1, sphere2, sphere3);
    List<Shape> beforeSortingWithSameArea = List.of(cube34, cube3, cylinder44, sphere3, cylinder55, cube33, cylinder3, sphere512);

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
        Assertions.assertIterableEquals(SortingShapeTask.sortShape(beforeSorting),afterSorting);
    }

    @Test
    public void testShapeSortingWithSameArea(){
        List<Shape> afterSorting = List.of(cylinder3, cylinder44, cylinder55, cube3, cube33, cube34, sphere3, sphere512);
        Assertions.assertIterableEquals(SortingShapeTask.sortShape(beforeSortingWithSameArea),afterSorting);
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
    public void testShapeSortingWithNULL(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> SortingShapeTask.sortShape(null));
    }

}
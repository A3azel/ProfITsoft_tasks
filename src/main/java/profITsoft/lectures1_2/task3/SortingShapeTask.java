package profITsoft.lectures1_2.task3;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SortingShapeTask {
    // very simple exercise for stream API
    public static List<Shape> sortShape(List<Shape> shapeList){
        return Optional.ofNullable(shapeList)
                .orElseThrow(()->new IllegalArgumentException("passed null instead List<Shape>"))
                .stream()
                .sorted(Comparator.comparingDouble(Shape::getVolume).thenComparing(Shape::getName))
                .collect(Collectors.toList());
    }
}


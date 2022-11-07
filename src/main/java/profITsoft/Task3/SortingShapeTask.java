package profITsoft.Task3;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SortingShapeTask {
    // very simple exercise for stream API
    public static List<Shape> sortShape(List<Shape> shapeList){
        return shapeList
                .stream()
                .sorted(Comparator.comparingDouble(Shape::area).thenComparing(o -> o.name))
                .collect(Collectors.toList());
    }
}


package profITsoft.Task3;

import java.util.List;
import java.util.stream.Collectors;

public class SortingShapeTask {
    // very simple exercise for stream API
    public static List<Shape> sortShape(List<Shape> shapeList){
        return shapeList
                .stream()
                .sorted((o1,o2) -> (int) (o1.area()-o2.area()))
                .collect(Collectors.toList());
    }
}


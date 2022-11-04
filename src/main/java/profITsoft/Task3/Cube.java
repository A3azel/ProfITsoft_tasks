package profITsoft.Task3;

import java.util.Objects;

public class Cube implements Shape{
    public String name;
    public double high;
    public double length;
    public double width;

    public Cube(String name, double high, double length, double width) {
        this.name = name;
        this.high = high;
        this.length = length;
        this.width = width;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cube)) return false;
        Cube cube = (Cube) o;
        return Double.compare(cube.high, high) == 0 && Double.compare(cube.length, length) == 0 && Double.compare(cube.width, width) == 0 && Objects.equals(name, cube.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, high, length, width);
    }

    @Override
    public double area() {
        return high*length*width;
    }

    @Override
    public String toString() {
        return "Cube{" +
                "name='" + name + '\'' +
                ", area=" + area() + '}';
    }
}

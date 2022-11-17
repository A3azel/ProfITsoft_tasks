package profITsoft.lectures1_2.task3;

import java.util.Objects;

public class Cube extends Shape{
    private double high;

    public Cube(String name, double high) {
        super(name);
        this.high = high;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cube)) return false;
        Cube cube = (Cube) o;
        return Double.compare(cube.high, high) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(high);
    }

    @Override
    public double getVolume() {
        return Math.pow(high,3);
    }

    @Override
    public String toString() {
        return "Cube{" +
                "name='" + super.getName() + '\'' +
                ", area=" + getVolume() + '}';
    }
}

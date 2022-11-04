package profITsoft.Task3;

import java.util.Objects;

public class Cylinder implements Shape{
    public String name;
    public double radius;
    public double high;

    public Cylinder(String name, double radius, double high) {
        this.name = name;
        this.radius = radius;
        this.high = high;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cylinder)) return false;
        Cylinder cylinder = (Cylinder) o;
        return Double.compare(cylinder.radius, radius) == 0 && Double.compare(cylinder.high, high) == 0 && Objects.equals(name, cylinder.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, radius, high);
    }

    @Override
    public double area() {
        return Math.PI*Math.pow(radius,2)*high;
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "name='" + name + '\'' +
                ", area=" + area() + '}';
    }
}


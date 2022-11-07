package profITsoft.Task3;

import java.util.Objects;

public class Sphere extends Shape{
    public double radius;

    public Sphere(String name, double radius) {
        this.name = name;
        this.radius = radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sphere)) return false;
        Sphere sphere = (Sphere) o;
        return Double.compare(sphere.radius, radius) == 0 && Objects.equals(name, sphere.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, radius);
    }

    @Override
    public double area() {
        return (4.0/3.0)*Math.PI*Math.pow(radius,3);
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "name='" + name + '\'' +
                ", area=" + area() + '}';
    }
}

package profITsoft.lectures1_2.task3;

import java.util.Objects;

public class Sphere extends Shape{
    private double radius;

    public Sphere(String name, double radius) {
        super(name);
        this.radius = radius;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sphere)) return false;
        Sphere sphere = (Sphere) o;
        return Double.compare(sphere.radius, radius) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(radius);
    }

    @Override
    public double getVolume() {
        return (4.0/3.0)*Math.PI*Math.pow(radius,3);
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "name='" + super.getName() + '\'' +
                ", area=" + getVolume() + '}';
    }
}

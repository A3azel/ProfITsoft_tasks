package profITsoft.lectures1_2.task3;

import java.util.Objects;

public class Cylinder extends Shape{
    private double radius;
    private double high;

    public Cylinder(String name, double radius, double high) {
        super(name);
        this.radius = radius;
        this.high = high;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cylinder)) return false;
        Cylinder cylinder = (Cylinder) o;
        return Double.compare(cylinder.radius, radius) == 0 && Double.compare(cylinder.high, high) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(radius, high);
    }

    @Override
    public double getVolume() {
        return Math.PI*Math.pow(radius,2)*high;
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "name='" + super.getName() + '\'' +
                ", area=" + getVolume() + '}';
    }
}


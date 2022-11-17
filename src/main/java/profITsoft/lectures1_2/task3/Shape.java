package profITsoft.lectures1_2.task3;

public abstract class Shape {
    private String name;
    abstract double getVolume();

    public Shape(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}



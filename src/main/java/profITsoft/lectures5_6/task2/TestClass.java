package profITsoft.lectures5_6.task2;

import profITsoft.lectures5_6.task2.annotations.Property;

import java.util.Date;

public class TestClass{
    private String name;
    private int number;
    private Date created;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void hi(){
        System.out.println("Hi");
    }
}

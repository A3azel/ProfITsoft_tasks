package profITsoft.lectures5_6.task2.entities;

import profITsoft.lectures5_6.task2.annotations.Property;

import java.util.Date;

public class TestEntityWithAnnotations {
    @Property(name = "firstname")
    private String firstName;
    @Property(name = "lastname")
    private String lastName;
    @Property(name = "birthday.date", format = "dd-MM-yyyy HH:mm")
    private Date birthday;
    private int age;

    public TestEntityWithAnnotations() {
    }

    public TestEntityWithAnnotations(String firstName, String lastName, Date birthday, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthday = birthday;
        this.age = age;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "TestEntityWithAnnotations{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthday=" + birthday +
                ", age=" + age +
                '}';
    }
}

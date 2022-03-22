package dkip.oop;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Players {
    //---------Declaring attributes----

    String firstName;
    String lastName;
    String position;
    String State;
    int age;


    public Players (String firstName, String lastName, String position, String State, int age)
    {
        // some minimal validation
        if (firstName == null && lastName == null )
            throw new IllegalArgumentException("null arguments encountered");
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.State = State;
        this.age = age;
    }
    //Getters & Setters
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    String getInfo()
    {
        return "Name: "+firstName+ " "+lastName+", "+"position: " +position+ ", State: " +State+ ", Age: " +age;
    }
}


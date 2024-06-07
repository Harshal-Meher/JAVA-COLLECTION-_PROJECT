package model;

public class Student {
    private int rollno;
    private String name;
    private String address;
    private long mark;

    // Getters and Setters
    public int getRollno() {
        return rollno;
    }

    public void setRollno(int rollno) {
        this.rollno = rollno;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getMark() {
        return mark;
    }

    public void setMark(long mark) {
        this.mark = mark;
    }
}

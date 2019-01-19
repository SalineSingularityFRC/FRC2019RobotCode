package frc.robot;

public abstract class Student{

    double gpa;
    String name;
    boolean inSchool;
    int totalDaysSuspended;

    //false means male, true means female
    boolean gender;

    String course;

    public Student(double gpa, String name, boolean gender) {


        this.gpa = gpa;
        this.name = name;
        this.gender = gender;
        
        this.inSchool = true;
        totalDaysSuspended = 0;

    }

    public void suspend(int days) {

    this.inSchool = false;
        this.totalDaysSuspended = this.totalDaysSuspended + days;



    }

    public abstract void assignClass();

}
package frc.robot;


//Student along with Ninth/TenthGrader and Runner are classes to deomonstrate abstract and super classes.
//Created by Carter on 1-16-19
public abstract class Student {
    double gpa;
    String name;
    boolean inSchool;
    int totalDaysSuspended;
    String course;

    //false male, true female
    boolean gender;

    public Student(double gpa, String name, boolean gender){
        this.gpa = gpa;
        this.name = name;
        this.gender = gender;

        inSchool = true;
        totalDaysSuspended = 0;
    }

    public void suspend(int days) {
        this.inSchool = false;
        this.totalDaysSuspended = this.totalDaysSuspended + days;
    }

    public abstract void assignClass();
    
}
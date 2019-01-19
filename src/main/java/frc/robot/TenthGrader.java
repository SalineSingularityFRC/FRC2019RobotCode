package frc.robot;

public class TenthGrader extends Student {

    public TenthGrader(double gpa, String name, boolean gender) {
        super(gpa, name, gender);
    }
public void assignClass() {

    }
    double randomNumber = Math.random();

    if (randomNumber < 0.33) {
    super.course = "US History";
    }
    else if (randomNumber >= 0.33 && randomNumber <= 0.67) {
    super.course = "Honors english 10";
    }
    else {
    super.course = "pre calc";
}
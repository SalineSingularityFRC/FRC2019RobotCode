package frc.robot;

public class TenthGrader extends Student {

    public TenthGrader(double gpa, String name, boolean gender) {
        super(gpa, name, gender);
    }

    public void assignClass() {
        double random1 = Math.random();

        if(random1 < 0.33) {
            super.course = "Honors English 10";
        }
        
        else if(random1 > 0.33 && random1 < 0.66) {
            super.course = "US History";
        }

        else {
            super.course = "Chemistry";
        }
    }
}
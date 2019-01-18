package frc.robot;

public class Runner {

    public static void main(String[] args) {
        
        Student travis;


        //NinthGrader travis = new NinthGrader(4.0, "Travis", false, true);
        travis = new TenthGrader(3.9, "Travis", false);

    }

    public static void suspend(Student s) {
        s.suspend(30);

        s.assignClass();
    }
}
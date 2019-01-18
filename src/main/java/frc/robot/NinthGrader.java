package frc.robot;

public class NinthGrader extends Student {
    
        boolean inNinthGradeBand;
    
    public NinthGrader(double gpa, String name, boolean gender, boolean inNinthGradeBand) {
             super(gpa, name, gender);

             this.inNinthGradeBand = inNinthGradeBand;
        }

    public boolean auditionForJazzBand(String instrument) {
        double randomNumber = Math.random();
        if(randomNumber > 0.5) {
            return true;
        }

        else {
            return false;
        }
    }

    public void assignClass() {
        double random = Math.random();

        if (random < 0.33) {
            super.course = "biology";
        }
        else if (random > 0.33 && random < 0.66) {
            super.course = "world history";
        }
        else {
            super.course = "health";
        }
    }

    public static void main(String[] args) {
        
    }


    }
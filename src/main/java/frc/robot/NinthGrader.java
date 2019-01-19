package frc.robot;

public class NinthGrader extends Student {

    boolean inNinthGradeBand;

    public NinthGrader(double gpa, String name, boolean gender, boolean inNinthGradeBand) {
            super(gpa, name, gender);

            this.inNinthGradeBand = inNinthGradeBand;
        }

        public boolean auditionForJazzBand(String instrument) {


            double randomNumber= Math.random();

            if (randomNumber > 0.5) {
                return true;
            }
             
                return false;
            
        
            public void assignClass() {


                double randomNumber = math.random();

                if (randomNumber < 0.33) {
                    super.course = "biology";
                }
                else if (randomNumber >= 0.33 && randomNumber <= 0.67) {
                    suoer.course = "Algebra II";
                }
                else {
                    super.course = "health";
                }
            }

    }

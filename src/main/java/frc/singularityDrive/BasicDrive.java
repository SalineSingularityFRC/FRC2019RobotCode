package frc.singularityDrive;


/**
 * The simplest subclass of SingDrive, meant to represent a drivetrain with two sets of in-line
 * wheels and motors (for examples, 3 motors on the left and 3 on the right).
 */
public class BasicDrive extends SingDrive {
	
	
	/**
	 * This is the essential constructor for BasicDrive. Its parameters are motor controller ports and the
	 * driving speed constants.
	 * 
	 * The number and position of motor controllers will likely change from year to year, and likely from season to season,
	 * the the first several parameters and corresponding code will need to be edited accordingly.
	 *  
	 * @param leftMotor1 motor controller port # for one motor controller on the left drive train
	 * @param leftMotor2 motor controller port # for one motor controller on the left drive train
	 * @param rightMotor1 motor controller port # for one motor controller on the right drive train
	 * @param rightMotor2 motor controller port # for one motor controller on the right drive train
	 * 
	 * @param slowSpeedConstant suggested values: 0.2 - 0.5
	 * @param normalSpeedConstant suggested values: 0.6 - 1.0
	 * @param fastSpeedConstant suggest value: 1.0
	 */
	public BasicDrive(int leftMotor1, int leftMotor2, int rightMotor1, int rightMotor2,
	double slowSpeedConstant, double normalSpeedConstant, double fastSpeedConstant) {
		super(leftMotor1, leftMotor2, rightMotor1, rightMotor2, slowSpeedConstant, normalSpeedConstant, fastSpeedConstant);
	}


	/**
	 * This is the more basic constructor for BasicDrive. Its parameters are only motor controller ports, and they must
	 * correspond to the ports in the above constructor.
	 */
	public BasicDrive(int leftMotor1, int leftMotor2, int rightMotor1, int rightMotor2) {
		super(leftMotor1, leftMotor2, rightMotor1, rightMotor2);
		
	}



	/**
	 * Standard method for driving based on arcade
	 * 
	 * @param vertical the forward/reverse constraint for robot movement
	 * @param rotation the turning constraint for robot movement
	 * @param horizontal the side-to-side constrain for robot movement: pass in 0.0, as BasicDrive cannot handle strafing movement
	 * @param squaredInputs pass true if inputs should be squared, thus improving sensitivity for slower driving
	 * @param speedMode controls the velocityMultiplier in order to scale motor velocity
	 */
	public void arcadeDrive(double vertical, double rotation, double horizontal, boolean squaredInputs, SpeedMode speedMode) {

		double translationVelocity = vertical, rotationVelocity = rotation;
		
		// Account for joystick drift.
		translationVelocity = threshold(translationVelocity);
		rotationVelocity = threshold(rotationVelocity);

		// Do squared inputs if necessary.
		if (squaredInputs) {
			translationVelocity *= Math.abs(translationVelocity);
			rotationVelocity *= Math.abs(rotationVelocity);
		}

		// Change veloctiyMultiplier.
		setVelocityMultiplierBasedOnSpeedMode(speedMode);

		// If translation + rotation > 1, we will divide by this value, maximum, in order to only set motors to power -1 to 1.
		double maximum = Math.max(1, Math.abs(translationVelocity) + Math.abs(rotationVelocity));

		// Drive the motors, and all subsequent motors through following.
		m_leftMotor1.set(super.velocityMultiplier * (translationVelocity + rotationVelocity) / maximum);
		m_rightMotor1.set(super.velocityMultiplier * (-translationVelocity + rotationVelocity) / maximum);
		
	}

	
	/**
	 * 
	 * 6 wheel tank drive
	 * @param right
	 * @param squaredInputs
	 * @param speedMode
	 */
	public void tankDrive(double left, double right, double horizontal, boolean squaredInputs, SpeedMode speedMode) {
		
		double leftVelocity = left, rightVelocity = right;
		
		// Account for joystick drift.
		leftVelocity = threshold(leftVelocity);
		rightVelocity = threshold(rightVelocity);

		// Do squared inputs if necessary.
		if (squaredInputs) {
			leftVelocity *= Math.abs(leftVelocity);
			rightVelocity *= Math.abs(rightVelocity);
		}
		
		// Change velocityMultiplier.
		setVelocityMultiplierBasedOnSpeedMode(speedMode);
		
		// If a velocity > 1, we will divide by this value, maximum, in order to only set motors to power -1 to 1.
		double leftMaximum = Math.max(1, Math.abs(leftVelocity));
		double rightMaximum = Math.max(1, Math.abs(rightVelocity));

		// Drive the motors, and all subsequent motors through following.
		m_leftMotor1.set(super.velocityMultiplier * (leftVelocity) / leftMaximum);
		m_rightMotor1.set(super.velocityMultiplier * (rightVelocity) / rightMaximum);

	}
}
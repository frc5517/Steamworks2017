package org.usfirst.frc.team5517.robot.subsystems;

import org.usfirst.frc.team5517.robot.RobotMap;
import org.usfirst.frc.team5517.robot.commands.Drive;
import org.usfirst.frc.team5517.robot.sensors.ADXRS453Gyro;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.PIDSubsystem;

/**
 * DriveTrain PID subsystem
 * Tried to use a PIDSubsystem here but could not get it working properly
 */
public class DriveTrain_PID extends PIDSubsystem {
	
	private final static double JOYSTICK_TOLERANCE = 0.2,
								kP = 0.4, 
								kI = 0,
								kD = 0.1; //0.4
	
    private Talon leftMotors;
    private Talon rightMotors;
    private Talon backMotors;
    private ADXRS453Gyro gyro;

    private double targetAngle = 0;
    private double compensateValue = 0;
    private double lastUpdatedAngleTime = 0;
    
    public DriveTrain_PID() {
    	
    	// Call PIDSubsystem constructor
    	// Pass in name and PID constants
    	super("DriveTrain", kP, kI, kD);
    	/*super("DriveTrain", 
    			SmartDashboard.getNumber("P",0), 
    			SmartDashboard.getNumber("I",0), 
    			SmartDashboard.getNumber("D",0)
    			);*/
    	
    	leftMotors = new Talon(RobotMap.leftDriveMotorPWMPort);
    	rightMotors = new Talon(RobotMap.rightDriveMotorPWMPort);
    	backMotors = new Talon(RobotMap.backDriveMotorPWMPort);
    	gyro = new ADXRS453Gyro();
    	gyro.startThread();
    	
    	// tolerance for the PID target (degrees)
    	//this.setAbsoluteTolerance(5);
    	this.setPercentTolerance(10);
    	this.setInputRange(-180, 180);
    	this.setOutputRange(-0.14, 0.14);
    	this.setSetpoint(0.0);
    	this.getPIDController().setContinuous(false);
    	this.enable();
    }

    @Override
	public void initDefaultCommand() {
    	setDefaultCommand(new Drive());
    }
    
    /**
     * Adds a "deadzone" to joystick inputs to remove any jitter
     * @param The joystick value
     * @return The joystick value with deadzone added
     */
    private double joystickDz(double value) {
    	if (value > -JOYSTICK_TOLERANCE && value < JOYSTICK_TOLERANCE) {
    		return 0;
    	}
    	return value;
    }
    
    /**
     * Move the drive train
     * @param x The x value
     * @param y The y value
     * @param r The rotation value
     * @param d 
     * @param d 
     */
    public void drive(double x, double y, double r) {
    	double left, right, back;
    	//final double sin11Pi6 = -0.5; // sine of 11pi/6
    	//final double cos11Pi6 = 1.732050807568877/2; // sqrt(3)/2 cosine of 11pi/6 (30 degrees)
    	double currentAngle = getHeading();
    	double diff = Math.abs(currentAngle-targetAngle);
    	//compensateValue = 0;
    	
    	// fix joystick values by adding a "deadzone"
    	x = joystickDz(x);
    	y = joystickDz(y);
    	r = joystickDz(r);
    	
    	//System.out.printf("x: %f, y: %f, r: %f", x, y, r);
    	
    	// scale down rotation
    	r = r/2;
    	
    	// if there is rotation input, update the target angle
    	if(r != 0) {
    		setTargetAngle(currentAngle);
    		System.out.println("last sys nanotime: "+System.nanoTime());
    		lastUpdatedAngleTime = System.nanoTime();
    	}
    	
    	// if the last time the angle was updated was less than
    	// a set amount of nanoseconds ago, don't compensate yet
    	// and update the target angle to the current angle again
    	if( 1000000000 > (System.nanoTime() - lastUpdatedAngleTime) ) {
    		compensateValue = 0;
    		setTargetAngle(currentAngle);
    		System.out.println("\n\nNOT updating...\n");
    	}
    	/*else {
	    	System.out.println("gyro angle: " + getHeading());
	    	System.out.println("setAngle: " + setAngle);
			System.out.print("diff: " + diff);
			
			// if diff is big enough, rotate to compensate
			if(diff > 3) {
				
				double multiplier = 0.015;
				
				if(setAngle > currentAngle) {
					System.out.println("compensate right");
					compensateValue = diff * multiplier;
					//compensateValue += 0.15;
				}
				else if(setAngle < currentAngle) {
					System.out.println("compensate left");
					compensateValue = diff * -multiplier;
					//compensateValue -= 0.15;
				}
				
				// minimum and maximum value
				// max
				if(compensateValue > 0 && compensateValue > 0.3) 
					compensateValue = 0.3;
				else if(compensateValue < 0 && compensateValue < -0.3) 
					compensateValue = -0.3;
				
				// min
				else if(compensateValue < 0 && compensateValue > -0.1)
					compensateValue = -0.1;
				else if(compensateValue > 0 && compensateValue < 0.1)
					compensateValue = 0.1;
				
			}
    	}*/
    	if(compensateValue != 0) {
    		System.out.println("compensateValue: "+compensateValue);
    	}
    	
    	// calculate wheel speeds from inputs
    	left = (-0.5 * x - Math.sqrt(3)/2 * y) + r + compensateValue;
    	right = (-0.5 * x + Math.sqrt(3)/2 * y) + r + compensateValue;
    	back = x + r + compensateValue;
    	
    	leftMotors.set(left);
    	rightMotors.set(right);
    	backMotors.set(back);
    	
    	//System.out.println("left: " + left + " right: " + right +" back: " + back);
    	//System.out.println("Diff (NOT being used by PID): " + diff);
    	//System.out.println("compensateValue: " + compensateValue);

    }
    
    public void setTargetAngle(double angle) {
    	System.out.println("updating setAngle: " + angle);
    	this.setSetpoint(angle); // update the PID setpoint
    	targetAngle = angle;
    }
    
    public void turnToAngle(double angle) {
    	setTargetAngle(angle);
    }
    
    private double getCorrectedAngle(double angle){
    	return angle + 360*Math.floor(0.5-angle/360);
    }
    
    public double getRawHeading() {
    	return gyro.getAngle();
    }
    
    public double getHeading() {
    	return getCorrectedAngle(gyro.getAngle());
    }
    
    public boolean isGyroCalibrating() {
    	return gyro.isCalibrating();
    }
    
    public void calibrateGyro() {
    	gyro.calibrate();
    }
    
    public void stopCalibrating() {
    	gyro.stopCalibrating();
    }
    
    /**
     * Stop robot drive train
     */
    public void stop() {
    	leftMotors.set(0);
    	rightMotors.set(0);
    	backMotors.set(0);
    }

    
    // PID subsystem methods
    
    /**
     * Set the PID input to the current gyro angle
     */
	@Override
	protected double returnPIDInput() {
		//System.out.println("Setting PID Input to: " + getHeading());
		return getHeading();
	}

	/**
	 * Set the compensate value to the PID output
	 * The compensate value is added to all of the wheel's speeds
	 * (in order to make the robot rotate)
	 */
	@Override
	protected void usePIDOutput(double output) {
		//System.out.println("PID Output: " +output);
		this.compensateValue = output;
	}
    
}


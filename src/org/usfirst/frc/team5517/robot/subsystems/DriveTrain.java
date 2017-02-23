package org.usfirst.frc.team5517.robot.subsystems;

import org.usfirst.frc.team5517.robot.RobotMap;
import org.usfirst.frc.team5517.robot.commands.Drive;
import org.usfirst.frc.team5517.robot.sensors.ADXRS453Gyro;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The DriveTrain subsystem
 * This is the robot's main subsystem; without it, the robot isn't going anywhere!
 * 
 * Contains a main drive method as well as the gyro and methods to access/change the gyro
 */
public class DriveTrain extends Subsystem {

	private final double JOYSTICK_TOLERANCE = 0.1;
	
    private Talon leftMotors;
    private Talon rightMotors;
    private Talon backMotors;
    private ADXRS453Gyro gyro;

    /**
     * The current gyro angle
     */
    private double currentAngle = 0;
    
    /**
     * The target angle of the robot
     * (we want the gyro angle close to this)
     */
    private double targetAngle = 0;
    
    /**
     * Value added to the motor speeds to compensate angle error
     */
    private double compensation = 0;
    
    /**
     * Last time the target angle was updated, in nanoseconds
     * (obtained from System.nanoTime())
     */
    private long lastUpdatedTargetAngleTime = 0;
    
    public DriveTrain() {
    	leftMotors = new Talon(RobotMap.leftDriveMotorPWMPort);
    	rightMotors = new Talon(RobotMap.rightDriveMotorPWMPort);
    	backMotors = new Talon(RobotMap.backDriveMotorPWMPort);
    	gyro = new ADXRS453Gyro();
    	gyro.startThread();
    }

    @Override
	public void initDefaultCommand() {
    	setDefaultCommand(new Drive());
    }
    
    /**
     * Move the drive train based on x, y, and rotation input
     * Called by the Drive command, which is constantly being ran by the scheduler 
     * 
     * @param x The x value from left joystick
     * @param y The y value from left joystick
     * @param r The rotation value, which is x from the right joystick
     */
    public void drive(double x, double y, double r) {
    	currentAngle = this.getRawHeading();
    	double leftWheel, rightWheel, backWheel;
    	//double diff = Math.abs(currentAngle-targetAngle); // aka error
    	double diff = Math.IEEEremainder(targetAngle-currentAngle, 360.0);
    	
    	compensation = 0;
    	
    	// remove joystick jitter by adding a "deadzone"
    	x = joystickDz(x);
    	y = joystickDz(y);
    	r = joystickDz(r);
    	
    	// scale down rotation
    	r = r/2;
    	
    	// if there is rotation input, update the target angle
    	if(r != 0) {
    		setTargetAngle(currentAngle);
        	lastUpdatedTargetAngleTime = System.nanoTime();
    	}
    	// if it has been some time since angle was updated by driver, 
    	// then we can compensate 
    	else if(1000 > nanoToMilli(System.nanoTime()-lastUpdatedTargetAngleTime)) {
	    	System.out.print("gyro angle: " + currentAngle);
	    	System.out.print(", target angle: " + targetAngle);
			System.out.println(", diff: " + diff);
			
			// if diff is big enough, compensate
			if(Math.abs(diff) > 3) {
				
				final double multiplier = 0.015;
				
				compensation = diff * multiplier;
				
				/*if(targetAngle > currentAngle) {
					System.out.println("compensate right");
					compensation = diff * multiplier;
				}
				else if(targetAngle < currentAngle) {
					System.out.println("compensate left");
					compensation = diff * -multiplier;
				}*/
				
				// min and max for compensation
				compensation = minAndMax(compensation, 0.1, 0.3);
				
				System.out.println("compensating " + compensation);
				
			}
    	}
    	
    	// calculate wheel speeds from inputs
    	leftWheel = (-0.5 * x - Math.sqrt(3)/2 * y) + r + compensation;
    	rightWheel = (-0.5 * x + Math.sqrt(3)/2 * y) + r + compensation;
    	backWheel = x + r + compensation;
    	
    	leftMotors.set(leftWheel);
    	rightMotors.set(rightWheel);
    	backMotors.set(backWheel);
    	
    	//System.out.println("MOTORS: left: " + left + " right: " + right +" back: " + back);
    	System.out.println("compensation: " + compensation);

    }
    
    public void setTargetAngle(double angle) {
    	System.out.println("updating targetAngle: " + targetAngle);
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
     * Min and max of a value (positive and negative)
     * @param value
     * @param max
     * @param min
     * @return limited value
     */
    private double minAndMax(double value, double min, double max) {
    	// positive max
		if(value > max) 
			value = max;
		// negative max
		else if(value < 0 && value < -max) 
			value = -max;
		// negative min
		else if(value < 0 && value > -min)
			value = -min;
		// positive min
		else if(value > 0 && value < min)
			value = min;
		
		return value;
    }
    
    /**
     * Converts nanoseconds to milliseconds
     * @param nano nanoseconds
     * @return milliseconds
     */
    private long nanoToMilli(long nano) {
    	return nano/1000000;
    }
    
}

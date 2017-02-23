package org.usfirst.frc.team5517.robot.subsystems;

import java.util.Timer;

import org.usfirst.frc.team5517.robot.RobotMap;
import org.usfirst.frc.team5517.robot.commands.Drive;
import org.usfirst.frc.team5517.robot.sensors.ADXRS453Gyro;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {

	private final double JOYSTICK_TOLERANCE = 0.1;
	
    private Talon leftMotors;
    private Talon rightMotors;
    private Talon backMotors;
    private ADXRS453Gyro gyro;
    private Timer timer;

    private double setAngle = 0;
    private double P;
    private double lastUpdatedAngleTime;
    
    public DriveTrain() {
    	leftMotors = new Talon(RobotMap.leftDriveMotorPWMPort);
    	rightMotors = new Talon(RobotMap.rightDriveMotorPWMPort);
    	backMotors = new Talon(RobotMap.backDriveMotorPWMPort);
    	gyro = new ADXRS453Gyro();
    	gyro.startThread();
    }

    @Override
	public void initDefaultCommand() {
        // Set the default command for a subsystem here.
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
    	double diff = Math.abs(setAngle-currentAngle);
    	P = 0;
    	
    	// fix joystick values by adding a "deadzone"
    	x = joystickDz(x);
    	y = joystickDz(y);
    	r = joystickDz(r);
    	
    	// scale down rotation
    	r = r/2;
    	
    	// if there is rotation input, update the target angle
    	if(r != 0) {
    		setAngle = currentAngle;
    		System.out.println("updating setAngle: " + setAngle);
    		lastUpdatedAngleTime = System.nanoTime();
    	}
    	else {
	    	System.out.println("gyro angle: " + getHeading());
	    	System.out.println("setAngle: " + setAngle);
			System.out.print("diff: " + diff);
			
			// if diff is big enough, rotate to compensate
			if(diff > 3) {
				
				double multiplier = 0.015;
				
				if(setAngle > currentAngle) {
					System.out.println("compensate right");
					P = diff * multiplier;
					//P += 0.15;
				}
				else if(setAngle < currentAngle) {
					System.out.println("compensate left");
					P = diff * -multiplier;
					//P -= 0.15;
				}
				
				// minimum and maximum value
				// max
				if(P > 0 && P > 0.3) 
					P = 0.3;
				else if(P < 0 && P < -0.3) 
					P = -0.3;
				
				// min
				else if(P < 0 && P > -0.1)
					P = -0.1;
				else if(P > 0 && P < 0.1)
					P = 0.1;
				
			}
    	}
    	
    	
    	if(setAngle > 175 && setAngle < -175) {
    		P = 0;
    	}
    	
    	if( 1000000000 > (System.nanoTime() - lastUpdatedAngleTime) ) {
    		P = 0;
    		setTargetAngle(currentAngle);
    		System.out.println("\n\nNOT updating...\n");
    	}
    	
    	// calculate wheel speeds from inputs
    	left = (-0.5 * x - Math.sqrt(3)/2 * y) + r + P;
    	right = (-0.5 * x + Math.sqrt(3)/2 * y) + r + P;
    	back = x + r + P;
    	
    	leftMotors.set(left);
    	rightMotors.set(right);
    	backMotors.set(back);
    	
    	//System.out.println("left: " + left + " right: " + right +" back: " + back + " diff: " + diff);
    	System.out.println("P " + P);

    }
    
    public void setTargetAngle(double angle) {
    	setAngle = angle;
    }
    
    public void turnToAngle(double angle) {
    	setAngle = angle;
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
    
}

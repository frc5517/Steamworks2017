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
    	double diff = Math.abs(currentAngle-setAngle);
    	P = 0;
    	
    	// scale down rotation
    	r = r/2;
    	
    	// check if rotation input is within the joystick tolerance
    	// if so, don't rotate
    	if ( (r > 0 && r < JOYSTICK_TOLERANCE) || (r < 0 && r > -JOYSTICK_TOLERANCE) ) {
    		r = 0;
    	}
    	
    	// if rotation input is not within joystick tolerance
    	// then update the target angle
    	if(r < -JOYSTICK_TOLERANCE || r > JOYSTICK_TOLERANCE) {
    		setAngle = currentAngle;
    		System.out.println("updating setAngle: " + setAngle);
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
    
    public void changeSetAngle(double angle) {
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


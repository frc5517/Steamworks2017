package org.usfirst.frc.team5517.robot.subsystems;

import org.usfirst.frc.team5517.robot.RobotMap;
import org.usfirst.frc.team5517.robot.commands.Drive;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {

    Talon leftMotors;
    Talon rightMotors;
    Talon backMotors;
    
    public DriveTrain() {
    	leftMotors = new Talon(RobotMap.leftDriveMotorPWMPort);
    	rightMotors = new Talon(RobotMap.rightDriveMotorPWMPort);
    	backMotors = new Talon(RobotMap.backDriveMotorPWMPort);
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
     */
    public void drive(double x, double y, double r) {
    	double left, right, back;
    	final double sinPi6 = 0.5; // sin of pi/6
    	final double cosPi6 = Math.sqrt(3)/2; // cosine of pi/6 (30 degrees)
    	
    	left = -sinPi6 * x - cosPi6 * y + r;
    	right = -sinPi6 * x + cosPi6 * y + r;
    	back = x + r;
    	
    	leftMotors.set(left);
    	rightMotors.set(right);
    	backMotors.set(back);
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


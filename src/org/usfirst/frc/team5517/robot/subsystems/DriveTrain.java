package org.usfirst.frc.team5517.robot.subsystems;

import org.usfirst.frc.team5517.robot.RobotMap;
import org.usfirst.frc.team5517.robot.commands.Drive;
import org.usfirst.frc.team5517.robot.sensors.ADXRS453Gyro;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class DriveTrain extends Subsystem {

    private Talon leftMotors;
    private Talon rightMotors;
    private Talon backMotors;
    private ADXRS453Gyro gyro;
    
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
    public void drive(double x, double y, double r, double s, double rightStick) {
    	double left, right, back;
    	final double sin11Pi6 = -0.5; // sine of 11pi/6
    	final double cos11Pi6 = 1.732050807568877/2; // sqrt(3)/2 cosine of 11pi/6 (30 degrees)
    	
    	left = sin11Pi6 * x - cos11Pi6 * y + r;
    	right = sin11Pi6 * x + cos11Pi6 * y + r;
    	back = -(x + r);
    	
    	leftMotors.set(left);
    	rightMotors.set(right);
    	backMotors.set(back);

    	if(rightStick > 0.1 || rightStick < -0.1)  {
    		leftMotors.set(left/1.6);
    		rightMotors.set(right/1.75);
    	}

    }
    
    public double getHeading() {
    	return gyro.getAngle();
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


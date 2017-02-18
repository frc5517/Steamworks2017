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
    	double diff = Math.abs(getHeading()-setAngle);
    	P = 0;
    	
    	
    	r = r/2;
    	
    	if(r < -0.1 || r > 0.1){
    		setAngle = getHeading();
    		System.out.println("updating setAngle: " + setAngle);
    	}
    	else {
	    	//System.out.println("gyro angle: " + getHeading());
	    	//System.out.println("setAngle: " + setAngle);
			System.out.print("diff: " + diff);
			
			if(diff > 3) {
				
				if(setAngle >= getHeading()) {
					System.out.println("compensate right");
					P += 0.15;
				}
				else if(setAngle <= getHeading()) {
					System.out.println("compensate left");
					P -= 0.15;
				}
				
			}
			else {
				P = 0;
			}
			/*if(P < 0.11) {
				P = 0.11;
			}*/
    	}
    	
    	/*if(setAngle > 0 && diff > 0.5 ) {
    		System.out.println("compensating right");
    		x += 0.2;
    	}
    	else if(setAngle < 0 && diff > 0.5) {
    		System.out.println("compensating left");
    		x -= 0.2;
    	}*/
    	
    	left = (-0.5 * x - Math.sqrt(3)/2 * y) + r + P;
    	right =( -0.5 * x + Math.sqrt(3)/2 * y) + r + P;
    	back = x + r + P;
    	
    	/*if(P < 0){
    		left = -left;
    		right = -right;
    		back = -back;
    	}*/
    	
    	leftMotors.set(left);
    	rightMotors.set(right);
    	backMotors.set(back);
    	
    	//System.out.println("left: " + left + " right: " + right +" back: " + back + " diff: " + diff);
    	System.out.println("P " + P);

    }
    
    private double correctedAngle(double angle){
    	return angle + 360*Math.floor(0.5-angle/360);
    }
    
    public double getRawHeading() {
    	return gyro.getAngle();
    }
    
    public double getHeading() {
    	return correctedAngle(gyro.getAngle());
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


package org.usfirst.frc.team5517.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {

	final public static int 
	
	// Controllers
	driverGamepadID = 0,
	operatorGamepadID = 1,
	
	// Motor Ports
	leftDriveMotorPWMPort  = 0,
	rightDriveMotorPWMPort = 1,
	backDriveMotorPWMPort  = 2,
	dumpMotorPWMPort = 3,
	intakeMotorPWMPort = 4,
	intakeLiftMotorPWMPort = 5,
	climberMotorPWMPort = 6;
	
}

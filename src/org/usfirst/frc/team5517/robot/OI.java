package org.usfirst.frc.team5517.robot;

import org.usfirst.frc.team5517.robot.utils.Gamepad;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	private Gamepad driverGamepad;
	//private Gamepad operatorGamepad;
	
	public OI() {
		driverGamepad = new Gamepad(RobotMap.driverGamepadID);
		//operatorGamepad = new Gamepad(RobotMap.operatorGamepadID);
		bindControls();
	}
	
	private void bindControls() {
		
	}
	
	public double getDriverLeftX() {
		return driverGamepad.getLeftX();
	}
	
	public double getDriverLeftY() {
		return driverGamepad.getLeftY();
	}
	
	public double getDriverRightX() {
		return driverGamepad.getRightX();
	}
	
	public double getDriverRightY() {
		return driverGamepad.getRightY();
	}
	
}

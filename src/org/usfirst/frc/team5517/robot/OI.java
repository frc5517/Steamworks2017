package org.usfirst.frc.team5517.robot;

import org.usfirst.frc.team5517.robot.commands.ClimbDown;
import org.usfirst.frc.team5517.robot.commands.ClimbUp;
import org.usfirst.frc.team5517.robot.commands.LiftIntake;
import org.usfirst.frc.team5517.robot.commands.LowerIntake;
import org.usfirst.frc.team5517.robot.commands.SpinIntakeRollerIn;
import org.usfirst.frc.team5517.robot.commands.SpinIntakeRollerOut;
import org.usfirst.frc.team5517.robot.commands.TurnBackward;
import org.usfirst.frc.team5517.robot.commands.TurnForward;
import org.usfirst.frc.team5517.robot.commands.TurnLeft135;
import org.usfirst.frc.team5517.robot.commands.TurnRight45;
import org.usfirst.frc.team5517.robot.utils.DPadButton;
import org.usfirst.frc.team5517.robot.utils.Gamepad;
import org.usfirst.frc.team5517.robot.utils.JoystickAnalogButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	private Gamepad driverGamepad;
	private Gamepad operatorGamepad;
	public JoystickAnalogButton operatorTriggerR, operatorTriggerL;
	public DPadButton operatorDPadUp, operatorDPadDown;
			  
	
	public OI() {
		driverGamepad = new Gamepad(RobotMap.driverGamepadID);
		operatorGamepad = new Gamepad(RobotMap.operatorGamepadID);
		operatorTriggerR = new JoystickAnalogButton(operatorGamepad, Gamepad.AXIS_RIGHT_TRIGGER, 0.5);
		operatorTriggerL = new JoystickAnalogButton(operatorGamepad, Gamepad.AXIS_LEFT_TRIGGER, 0.5);
		operatorDPadUp = new DPadButton(operatorGamepad, DPadButton.NORTH);
		operatorDPadDown = new DPadButton(operatorGamepad, DPadButton.SOUTH);
		bindControls();
	}
	
	private void bindControls() {
		operatorGamepad.getRightShoulder().whileHeld(new SpinIntakeRollerIn());
		operatorGamepad.getLeftShoulder().whileHeld(new SpinIntakeRollerOut());
		operatorTriggerR.whileHeld(new LiftIntake());
		operatorTriggerL.whileHeld(new LowerIntake());
		operatorDPadUp.whileHeld(new ClimbUp());
		operatorDPadDown.whileHeld(new ClimbDown());
		//operatorGamepad.getButtonX().whenPressed(new CloseDumpDoor());
		//operatorGamepad.getButtonY().whenPressed(new OpenDumpDoor());
		//operatorGamepad.getButtonA().whenPressed(new ());
		
		operatorGamepad.getButtonB().whileHeld(new LiftIntake());
		operatorGamepad.getButtonA().whileHeld(new LowerIntake());
		
		driverGamepad.getButtonY().whenPressed(new TurnForward());
		driverGamepad.getButtonB().whenPressed(new TurnRight45());
		driverGamepad.getButtonA().whenPressed(new TurnBackward());
		driverGamepad.getButtonX().whenPressed(new TurnLeft135());		
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

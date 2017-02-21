package org.usfirst.frc.team5517.robot;

import org.usfirst.frc.team5517.robot.commands.Climb;
import org.usfirst.frc.team5517.robot.commands.CloseDumpDoor;
import org.usfirst.frc.team5517.robot.commands.LiftIntake;
import org.usfirst.frc.team5517.robot.commands.LowerIntake;
import org.usfirst.frc.team5517.robot.commands.OpenDumpDoor;
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
	private JoystickAnalogButton operatorTriggerR, operatorTriggerL;
	private DPadButton operatorDPadUp, operatorDPadDown;
			  
	
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
		//operatorDPadUp.whileHeld(new ClimbUp()); // TODO figure if this works
		//operatorDPadDown.whileHeld(new ClimbDown()); // TODO figure if this works
		operatorGamepad.getButtonX().whileHeld(new CloseDumpDoor());
		operatorGamepad.getButtonY().whileHeld(new OpenDumpDoor());
		operatorGamepad.getButtonA().whileHeld(new Climb());
		
		driverGamepad.getButtonY().whileHeld(new TurnForward());
		driverGamepad.getButtonB().whileHeld(new TurnRight45());
		driverGamepad.getButtonA().whileHeld(new TurnBackward());
		driverGamepad.getButtonX().whileHeld(new TurnLeft135());
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
	
	public double getOperatorLeftY() {
		return operatorGamepad.getLeftY();
	}
	
}

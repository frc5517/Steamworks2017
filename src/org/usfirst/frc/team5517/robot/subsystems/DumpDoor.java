package org.usfirst.frc.team5517.robot.subsystems;

import org.usfirst.frc.team5517.robot.RobotMap;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.command.Subsystem;

public class DumpDoor extends Subsystem {

	private static final double OPEN_SPEED = 0.2;
	private static final double CLOSE_SPEED = 0.2;
	
	private Talon dumpMotor;
	
	public DumpDoor() {
		dumpMotor = new Talon(RobotMap.dumpMotorPWMPort);
	}
	@Override
	protected void initDefaultCommand() {
		//setDefaultCommand();
	}
	
	public void open() {
		dumpMotor.set(OPEN_SPEED);
	}
	
	public void close() {
		dumpMotor.set(CLOSE_SPEED);
	}
	
	public void stop() {
		dumpMotor.set(0);
	}

	
}
